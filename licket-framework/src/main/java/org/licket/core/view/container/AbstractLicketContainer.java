package org.licket.core.view.container;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.QUOTE_FIELD_NAMES;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static org.licket.core.model.LicketModel.emptyModel;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.function.Predicate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketModel;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.ComponentView;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.testing.annotation.Name;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.ObjectLiteral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author activey
 */
public abstract class AbstractLicketContainer<T> extends AbstractLicketComponent<T> implements LicketComponentContainer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketContainer.class);
    private List<LicketComponentContainer<?>> branches = newArrayList();
    private List<LicketComponent<?>> leaves = newArrayList();

    @Name("model")
    private ObjectLiteralBuilder modelProperty = ObjectLiteralBuilder.objectLiteral();

    public AbstractLicketContainer(String id, Class<T> modelClass, ComponentView view) {
        this(id, modelClass, emptyModel(), view);
    }

    public AbstractLicketContainer(String id, Class<T> modelClass,
                                   LicketModel<T> componentModel, ComponentView view) {
        super(id, modelClass, componentModel, view);
    }

    protected final void add(LicketComponent<?> licketComponent) {
        if (branches.contains(licketComponent)) {
            LOGGER.trace("Licket component [{}] already used as a branch!", licketComponent.getId());
            return;
        }
        licketComponent.setParent(this);
        leaves.add(licketComponent);
    }

    protected final void add(LicketComponentContainer<?> licketComponentContainer) {
        licketComponentContainer.setParent(this);
        branches.add(licketComponentContainer);
    }

    @Override
    protected final void onInitialize() {
        branches.forEach(LicketComponent::initialize);
        leaves.forEach(LicketComponent::initialize);

        onInitializeContainer();
        generateComponentModel();
    }

    private void generateComponentModel() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(QUOTE_FIELD_NAMES, false);

            // serialize component model to string json
            String modelStringValue = mapper.writeValueAsString(getComponentModel().get());

            // parse model declaration object literal
            AstRoot astRoot = new Parser().parse(modelObjectLiteralReader(modelStringValue), "test.js", 0);
            astRoot.visitAll(node -> {
                if (node instanceof ObjectLiteral) {
                    modelProperty.fromObjectLiteral((ObjectLiteral) node);
                    return false;
                }
                return true;
            });
        } catch (IOException e) {
            LOGGER.error("An error occurred while creating Angular class constructor.", e);
        }
    }

    private Reader modelObjectLiteralReader(String modelStringValue) {
        return new StringReader(format("model = %s", modelStringValue));
    }

    protected void onInitializeContainer() {}

    public final void traverseDown(Predicate<LicketComponent<?>> componentVisitor) {
        leaves.forEach(componentVisitor::test);
        branches.forEach(branch -> {
            if (componentVisitor.test(branch)) {
                branch.traverseDown(componentVisitor);
            }
        });
    }

    @Override
    public final void traverseDownContainers(Predicate<LicketComponentContainer<?>> containerVisitor) {
        branches.forEach(branch -> {
            if (containerVisitor.test(branch)) {
                branch.traverseDownContainers(containerVisitor);
            }
        });
    }

    @Override
    public final LicketComponent<?> findChild(CompositeId compositeId) {
        if (!compositeId.hasMore()) {
            if (compositeId.current().equals(getId())) {
                return this;
            }
            for (LicketComponent<?> leaf : leaves) {
                if (leaf.getId().equals(compositeId.current())) {
                    return leaf;
                }
            }
            for (LicketComponentContainer<?> branch : branches) {
                if (!branch.getId().equals(compositeId.current())) {
                    continue;
                }
                LicketComponent<?> childComponent = branch.findChild(compositeId);
                if (childComponent != null) {
                    return childComponent;
                }
            }
            return null;
        }

        compositeId.forward();

        for (LicketComponent<?> leaf : leaves) {
            if (leaf.getId().equals(compositeId.current())) {
                return leaf;
            }
        }
        for (LicketComponentContainer<?> branch : branches) {
            if (!branch.getId().equals(compositeId.current())) {
                continue;
            }
            LicketComponent<?> childComponent = branch.findChild(compositeId);
            if (childComponent != null) {
                return childComponent;
            }
        }
        return null;
    }
}

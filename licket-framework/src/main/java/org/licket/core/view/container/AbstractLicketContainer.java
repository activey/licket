package org.licket.core.view.container;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static org.licket.core.view.hippo.ComponentModelSerializer.serializeComponentModel;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.EqualCheckExpressionBuilder.equalCheckExpression;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.IfStatementBuilder.ifStatement;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;
import static org.licket.framework.hippo.VariableDeclarationBuilder.variableDeclaration;
import static org.licket.framework.hippo.VariableInitializerBuilder.variableInitializer;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.ComponentView;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.annotation.AngularClassConstructor;
import org.licket.core.view.hippo.annotation.AngularClassFunction;
import org.licket.core.view.hippo.annotation.Name;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
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
    private ObjectLiteralBuilder modelProperty;

    @Name("modelReloader")
    protected LicketComponentModelReloader modelReloader;

    public AbstractLicketContainer(String id, Class<T> modelClass, LicketComponentModelReloader modelReloader) {
        super(id, modelClass);
        this.modelReloader = checkNotNull(modelReloader, "Model reloader has to be not null!");
    }

    public AbstractLicketContainer(String id, Class<T> modelClass, LicketModel<T> componentModel,
                                   LicketComponentModelReloader modelReloader) {
        super(id, modelClass, componentModel);
        this.modelReloader = checkNotNull(modelReloader, "Model reloader has to be not null!");
    }

    public AbstractLicketContainer(String id, Class<T> modelClass, LicketModel<T> componentModel, ComponentView view,
                                   LicketComponentModelReloader modelReloader) {
        super(id, modelClass, componentModel, view);
        this.modelReloader = checkNotNull(modelReloader, "Model reloader has to be not null!");
    }

    @AngularClassFunction
    public void handleModelChanged(@Name("changedModelData") NameBuilder changedModelData, BlockBuilder functionBody) {
        functionBody.appendStatement(
          expressionStatement(
              ifStatement()
                  .condition(equalCheckExpression()
                          .left(property(changedModelData, name("compositeId")))
                          .right(stringLiteral(getCompositeId().getValue())))
                  .then(
                          assignment()
                            .left(property(name("this"), name("model")))
                            .right(property(changedModelData, name("model")))
                  ))
          );
    }

    @AngularClassConstructor
    public void constructor(BlockBuilder body) {
        body.appendStatement(expressionStatement(
                variableDeclaration().variable(variableInitializer().target(name("vm")).initializer(thisLiteral()))
        ));
        body.appendStatement(expressionStatement(
                functionCall()
                        .target(property(name("modelReloader"), name("listenForModelChange")))
                        .argument(functionNode()
                                    .param(name("changedModelData"))
                                    .body(block().appendStatement(expressionStatement(
                                            functionCall()
                                                    .target(property(name("vm"), name("handleModelChanged")))
                                                    .argument(name("changedModelData"))
                                    ))))
        ));
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
    }

    public ObjectLiteralBuilder getModelProperty() {
        try {
            return serializeComponentModel(getComponentModel());
        } catch (IOException e) {
            LOGGER.error("An error occurred while serializing component model.", e);
            return objectLiteral();
        }
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

package org.licket.core.view.container;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.EqualCheckExpressionBuilder.equalCheckExpression;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.IfStatementBuilder.ifStatement;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

import java.util.List;
import java.util.function.Predicate;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.LicketComponentView;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.annotation.OnVueCreated;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.render.ComponentRenderingContext;
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
    private List<LicketComponent<?>> leaves = newArrayList();

    @Name("model")
    private ObjectLiteralBuilder modelProperty;

    @Name("$licketModelReloader")
    protected LicketComponentModelReloader modelReloader;

    public AbstractLicketContainer(String id, Class<T> modelClass, LicketComponentModelReloader modelReloader) {
        super(id, modelClass);
        this.modelReloader = checkNotNull(modelReloader, "Model reloader has to be not null!");
    }

    public AbstractLicketContainer(String id, Class<T> modelClass, LicketComponentModel<T> componentModel,
                                   LicketComponentModelReloader modelReloader) {
        super(id, modelClass, componentModel);
        this.modelReloader = checkNotNull(modelReloader, "Model reloader has to be not null!");
    }

    public AbstractLicketContainer(String id, Class<T> modelClass, LicketComponentModel<T> componentModel, LicketComponentView view,
                                   LicketComponentModelReloader modelReloader) {
        super(id, modelClass, componentModel, view);
        this.modelReloader = checkNotNull(modelReloader, "Model reloader has to be not null!");
    }

    @VueComponentFunction
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

    @OnVueCreated
    public void onVueCreated(BlockBuilder body) {
        body.appendStatement(expressionStatement(
                functionCall()
                        .target(property(property(thisLiteral(), modelReloader.vueName()), name("listenForModelChange")))
                        .argument(property(thisLiteral(), name("handleModelChanged")))
        ));
    }

    @Override
    protected final void onBeforeRender(ComponentRenderingContext renderingContext) {
        onRenderContainer(renderingContext);
    }

    protected void onRenderContainer(ComponentRenderingContext renderingContext) {}

    public final void add(LicketComponent<?> licketComponent) {
        if (leaves.contains(licketComponent)) {
            LOGGER.trace("Licket component [{}] already used as a leaf!", licketComponent.getId());
            return;
        }
        licketComponent.setParent(this);
        leaves.add(licketComponent);
    }

    @Override
    protected final void onInitialize() {
        onInitializeContainer();
        leaves.forEach(LicketComponent::initialize);
    }

    protected void onInitializeContainer() {}

    public final void traverseDown(Predicate<LicketComponent<?>> componentVisitor) {
        leaves.forEach(leaf -> {
            if (componentVisitor.test(leaf)) {
                leaf.traverseDown(componentVisitor);
            }
        });
    }
}

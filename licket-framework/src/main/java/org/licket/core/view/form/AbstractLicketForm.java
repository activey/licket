package org.licket.core.view.form;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.ComponentActionCallback;
import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.LicketComponentView;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.annotation.VueComponent;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;

import static org.licket.core.module.application.LicketComponentModelReloader.callReloadComponent;
import static org.licket.framework.hippo.ArrayElementGetBuilder.arrayElementGet;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.ReturnStatementBuilder.returnStatement;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author activey
 */
@VueComponent
public abstract class AbstractLicketForm<T> extends AbstractLicketMultiContainer<T> {

  public AbstractLicketForm(String id, Class<T> modelClass, LicketComponentModel<T> model, LicketComponentView componentView) {
    super(id, modelClass, model, componentView);
  }

  @SuppressWarnings("unused")
  /**
   * Executed from within LicketFormController
   */
  public final void submitForm(T formModelObject, ComponentActionCallback actionCallback) {
    setComponentModelObject(formModelObject);
    onSubmit();
    onAfterSubmit(actionCallback);
  }

  protected void onSubmit() {
  }

  @Override
  protected void onBeforeRenderContainer(ComponentRenderingContext renderingContext) {
    renderingContext.onSurfaceElement(element -> {
      // TODO check if element is in fact a <form>, or not necessary?
      element.addAttribute("v-on:submit", "submitForm");
    });
  }

  @VueComponentFunction
  public final void afterSubmit(@Name("response") NameBuilder response, BlockBuilder functionBody) {
    // setting current form model directly without event emitter
    functionBody
            .appendStatement(
                    expressionStatement(assignment().left(property(thisLiteral(), name("model")))
                            .right(arrayElementGet()
                                    .target(
                                            property(property("response", "body"), "model"))
                                    .element(stringLiteral(getCompositeId().getValue())))));

    // gathering all others
    ComponentActionCallback componentActionCallback = new ComponentActionCallback();

    // invoking post action callback
    onAfterSubmit(componentActionCallback);

    // sending reload request for gathered components
    componentActionCallback.forEachToBeReloaded((component, patch) -> functionBody.appendStatement(expressionStatement(callReloadComponent(component, patch))));

    // invoking javascript calls
    componentActionCallback.forEachCall(call -> functionBody.appendStatement(
            expressionStatement(call)
    ));
  }

  protected void onAfterSubmit(ComponentActionCallback componentActionCallback) {
  }

  @VueComponentFunction
  public void submitForm(BlockBuilder functionBlock) {
    functionBlock
            .appendStatement(expressionStatement(
                    LicketRemote.callSubmitForm(
                            getCompositeId().getValue(), property(thisLiteral(), name("afterSubmit"))
                    )
            ))
            .appendStatement(returnStatement().returnValue(name("false")));
  }

  @Override
  public AbstractLicketFormAPI api(ComponentFunctionCallback functionCallback) {
    return new AbstractLicketFormAPI(this, functionCallback);
  }
}

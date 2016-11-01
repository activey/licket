package org.licket.semantic.component.modal;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.framework.hippo.BlockBuilder;

import static org.licket.core.model.LicketModel.ofModelObject;
import static org.licket.core.view.ComponentView.fromComponentContainerClass;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;

/**
 * @author grabslu
 */
public abstract class AbstractSemanticUIModal extends AbstractLicketContainer<ModalSettings> {

  public AbstractSemanticUIModal(String id, ModalSettings modalSettings,
      LicketComponentModelReloader modelReloader) {
    super(id, ModalSettings.class, ofModelObject(modalSettings),
        fromComponentContainerClass(AbstractSemanticUIModal.class), modelReloader);
  }

  @VueComponentFunction
  public final void show(BlockBuilder body) {
    body.appendStatement(expressionStatement(functionCall()));
  }

  @VueComponentFunction
  public final void hide(BlockBuilder body) {
    body.appendStatement(expressionStatement(functionCall()));
  }
}

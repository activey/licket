package org.licket.core.view.hippo.vue.extend;

import org.licket.core.LicketApplication;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.framework.hippo.FunctionNodeBuilder;
import org.licket.framework.hippo.KeywordLiteralBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.EqualCheckExpressionBuilder.equalCheckExpression;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.IfStatementBuilder.ifStatement;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.ReturnStatementBuilder.returnStatement;

/**
 * @author lukaszgrabski
 */
public class OnVueBeforeRouteEnterDecorator {

  @Autowired
  private LicketRemote licketRemote;

  @Autowired
  private LicketApplication application;

  public final void decorate(LicketComponent<?> licketComponent, ObjectLiteralBuilder objectLiteralBuilder) {
    LicketMountPoint mountPoint = licketComponent.getClass().getAnnotation(LicketMountPoint.class);
    if (mountPoint == null) {
      return;
    }
    objectLiteralBuilder.objectProperty(
            propertyBuilder().name("beforeRouteEnter").value(beforeRouteEnter(licketComponent))
    );
  }

  private FunctionNodeBuilder beforeRouteEnter(LicketComponent<?> licketComponent) {
    return functionNode()
            .param(name("to"))
            .param(name("from"))
            .param(name("next"))
            .body(block()
                    .appendStatement(ifStatement()
                            .condition(equalCheckExpression().left(property("from", "path")).right(property("to", "path")))
                            .then(block()
                                    .appendStatement(expressionStatement(functionCall().target(name("next"))))
                                    .appendStatement(returnStatement())))
                    .appendStatement(expressionStatement(
                            functionCall()
                                    .target(name("next"))
                                    .argument(nextFunction(licketComponent)))));

  }

  private FunctionNodeBuilder nextFunction(LicketComponent<?> licketComponent) {
    return functionNode()
            .param(name("vm"))
            .body(
                    block()
                      .appendStatement(expressionStatement(
                              functionCall().target(property(name("vm"), "beforeMount"))
                      ))
                      .appendStatement(licketRemote.callMountComponent(
                              licketComponent.getCompositeId().getValue(),
                              functionNode()
                                      .param(name("response"))
                                      .body(block().appendStatement(
                                              functionCall()
                                                      .target(property(name("vm"), name("afterMount")))
                                                      .argument(name("response"))))
                      )
                    )
            );
  }
}

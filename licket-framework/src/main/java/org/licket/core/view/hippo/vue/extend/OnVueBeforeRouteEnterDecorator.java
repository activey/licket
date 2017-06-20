package org.licket.core.view.hippo.vue.extend;

import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.framework.hippo.FunctionNodeBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;

import static java.util.Arrays.stream;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author lukaszgrabski
 */
public class OnVueBeforeRouteEnterDecorator {

  private final LicketComponent<?> licketComponent;
  private final LicketRemote licketRemote;

  public static OnVueBeforeRouteEnterDecorator fromLicketComponent(LicketComponent<?> licketComponent, LicketRemote licketRemote) {
    return new OnVueBeforeRouteEnterDecorator(licketComponent, licketRemote);
  }

  public final void decorate(ObjectLiteralBuilder objectLiteralBuilder) {
    LicketMountPoint mountPoint = licketComponent.getClass().getAnnotation(LicketMountPoint.class);
    if (mountPoint == null) {
      return;
    }
    objectLiteralBuilder.objectProperty(
            propertyBuilder().name("beforeRouteEnter").value(beforeRouteEnter())
    );
  }

  private FunctionNodeBuilder beforeRouteEnter() {

    return functionNode()
            .param(name("to"))
            .param(name("from"))
            .param(name("next"))
            .body(block()
                    .appendStatement(expressionStatement(
                            functionCall()
                                    .target(name("next"))
                                    .argument(nextFunction())))
            );
  }

  private FunctionNodeBuilder nextFunction() {
    return functionNode()
            .param(name("vm"))
            .body(

                    block().appendStatement(licketRemote.callMountComponent(licketComponent.getCompositeId().getValue(), property(name("vm"), name("afterMount")))
            ));
  }



  private OnVueBeforeRouteEnterDecorator(LicketComponent<?> licketComponent, LicketRemote licketRemote) {
    this.licketComponent = licketComponent;
    this.licketRemote = licketRemote;
  }
}

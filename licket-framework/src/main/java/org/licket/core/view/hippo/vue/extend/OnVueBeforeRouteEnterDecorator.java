package org.licket.core.view.hippo.vue.extend;

import org.licket.core.LicketApplication;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.core.view.mount.MountComponentInterceptor;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionNodeBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
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

  @Autowired(required = false)
  private List<MountComponentInterceptor> mountComponentInterceptors = newArrayList();

  public final void decorate(LicketComponent<?> licketComponent, ObjectLiteralBuilder objectLiteralBuilder) {
    LicketMountPoint mountPoint = licketComponent.getClass().getAnnotation(LicketMountPoint.class);
    if (mountPoint == null) {
      return;
    }
    objectLiteralBuilder.objectProperty(
            propertyBuilder().name("beforeRouteEnter").value(beforeRouteEnter(licketComponent, mountPoint))
    );
  }

  private FunctionNodeBuilder beforeRouteEnter(LicketComponent<?> licketComponent, LicketMountPoint mountPoint) {
    return functionNode()
            .param(name("to"))
            .param(name("from"))
            .param(name("next"))
            .body(block()
                    .appendStatement(expressionStatement(
                            functionCall()
                                    .target(name("next"))
                                    .argument(nextFunction(licketComponent, mountPoint)))));

  }

  private FunctionNodeBuilder nextFunction(LicketComponent<?> licketComponent, LicketMountPoint mountPoint) {
    return functionNode()
            .param(name("vm"))
            .body(
                    decorateSamePathRouteEnter(decorateWithInterceptors(block(), mountPoint), mountPoint.samePathRouteEnter())
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

  private BlockBuilder decorateWithInterceptors(BlockBuilder blockBuilder, LicketMountPoint mountPoint) {
    mountComponentInterceptors.forEach(interceptor -> interceptor.intercept(blockBuilder, mountPoint));
    return blockBuilder;
  }

  private BlockBuilder decorateSamePathRouteEnter(BlockBuilder block, boolean samePathRouteEnter) {
    if (samePathRouteEnter) {
      return block;
    }
    block.appendStatement(ifStatement()
            .condition(equalCheckExpression().left(property("from", "path")).right(property("to", "path")))
            .then(block()
                    .appendStatement(returnStatement())));
    return block;
  }
}

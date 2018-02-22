package org.licket.core.module.application.security;

import org.licket.core.resource.FootParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.view.hippo.ComponentCallTargetOrigin.fromAppInstance;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.EqualCheckExpressionBuilder.equalCheckExpression;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.IfStatementBuilder.ifStatement;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author lukaszgrabski
 */
public class LicketComponentSecurityInterceptorResource extends AbstractJavascriptDynamicResource implements FootParticipatingResource {

  @Autowired
  private LicketComponentSecurity componentSecurity;

  @Override
  public String getName() {
    return "Licket.security.interceptor.js";
  }

  @Override
  protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
    scriptBlockBuilder.appendStatement(expressionStatement(
            functionCall()
                    .target(property(property(property("Vue", "http"), name("interceptors")), name("push")))
                    .argument(
                            functionNode()
                                    .param(name("request"))
                                    .param(name("next"))
                                    .body(writeNextFunction(writeTokenHeader(block())))
                    )
    ));
  }

  private BlockBuilder writeTokenHeader(BlockBuilder block) {
    block.appendStatement(expressionStatement(
            ifStatement()
                    .condition(equalCheckExpression().left(componentSecurity.callCheckAuthenticated(fromAppInstance())).right(NameBuilder.name("true")))
                    .then(
                            block().appendStatement(expressionStatement(componentSecurity.callWriteTokenToRequest(fromAppInstance(), name("request"))))
                    )
    ));
    return block;
  }

  private BlockBuilder writeNextFunction(BlockBuilder block) {
    block.appendStatement(functionCall().target(name("next")).argument(
            functionNode()
                    .param(name("res"))
                    .body(block()))
    );
    return block;
  }
}

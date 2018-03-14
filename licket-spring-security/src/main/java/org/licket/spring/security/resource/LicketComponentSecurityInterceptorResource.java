package org.licket.spring.security.resource;

import org.licket.core.resource.FootParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.spring.security.vue.LicketComponentSecurity;
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
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;
import static org.licket.framework.hippo.VariableDeclarationBuilder.variableDeclaration;
import static org.licket.framework.hippo.VariableInitializerBuilder.variableInitializer;

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
                                    .body(handleResponseFunction(writeTokenHeader(block())))
                    )
    ));
  }

  private BlockBuilder writeTokenHeader(BlockBuilder block) {
    block.appendStatement(expressionStatement(
            ifStatement()
                    .condition(equalCheckExpression().left(componentSecurity.callCheckAuthenticated(fromAppInstance())).right(name("true")))
                    .then(
                            block()
                                    .appendStatement(expressionStatement(componentSecurity.callWriteTokenToRequest(fromAppInstance(), name("request"))))
                    )
    ));
    return block;
  }

  private BlockBuilder handleResponseFunction(BlockBuilder block) {
    block.appendStatement(functionCall().target(name("next")).argument(
            functionNode()
                    .param(name("res"))
                    .body(block().appendStatement(expressionStatement(
                            ifStatement()
                                    .condition(
                                            equalCheckExpression()
                                                    .left(getHeaderCall())
                                                    .right(name("null"))
                                                    .negative())
                                    .then(
                                            block()
                                                    .appendStatement(expressionStatement(variableDeclaration().variable(variableInitializer().target(name("token")).initializer(getHeaderCall()))))
                                                    .appendStatement(expressionStatement(componentSecurity.callSetAuthenticationToken(fromAppInstance(), name("token"))))
                                    )
                    ))))
    );

    return block;
  }

  private FunctionCallBuilder getHeaderCall() {
    return functionCall().target(property(property("res", "headers"), "get")).argument(stringLiteral("X-AUTH-TOKEN"));
  }
}

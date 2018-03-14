package org.licket.spring.security.vue;

import org.licket.core.module.application.ApplicationModuleService;
import org.licket.core.view.hippo.ComponentCallTargetOrigin;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.hippo.vue.extend.AbstractCallableVueClass;
import org.licket.core.view.redirect.BrowserRedirect;
import org.licket.core.view.security.LicketComponentSecuritySettings;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.licket.framework.hippo.ConcatenateExpression.concatenateExpression;
import static org.licket.framework.hippo.EqualCheckExpressionBuilder.equalCheckExpression;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.IfStatementBuilder.ifStatement;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.ReturnStatementBuilder.returnStatement;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;
import static org.licket.framework.hippo.VariableDeclarationBuilder.variableDeclaration;
import static org.licket.framework.hippo.VariableInitializerBuilder.variableInitializer;

/**
 * @author lukaszgrabski
 */
public class LicketComponentSecurity extends AbstractCallableVueClass implements ApplicationModuleService {

  private static final String AUTHENTICATION_TOKEN_VARIABLE = "authToken";
  private static final String AUTHENTICATION_TOKEN_COOKIE = "X-AUTH-TOKEN";

  @Autowired
  private Optional<LicketComponentSecuritySettings> securitySettings;

  private BrowserRedirect browserRedirect = new BrowserRedirect();

  public static String serviceName() {
    return "$licketComponentSecurity";
  }

  @VueComponentFunction
  public void checkAuthenticated(BlockBuilder body) {
    NameBuilder authenticationToken = name(AUTHENTICATION_TOKEN_VARIABLE);
    body.appendStatement(expressionStatement(variableDeclaration().variable(
            variableInitializer()
                    .target(authenticationToken)
                    .initializer(functionCall()
                            .target(property("Cookie", "get")).argument(stringLiteral(AUTHENTICATION_TOKEN_COOKIE)))
    )));
    body.appendStatement(expressionStatement(
            ifStatement()
                    .condition(equalCheckExpression().negative().left(authenticationToken).right(name("null")))
                    .then(returnStatement().returnValue(name("true")))
    ));
    body.appendStatement(expressionStatement(returnStatement().returnValue(name("false"))));
  }

  @VueComponentFunction
  public void performAuthentication(@Name("router") NameBuilder router, @Name("redirectTo") NameBuilder redirectTo, BlockBuilder body) {
    securitySettings.ifPresent(securitySettings -> body.appendStatement(expressionStatement(browserRedirect.redirectToInternalUri("/oauth2/gitlab/login"))));

//  TODO implement it as a different authentication strategy?
//    securitySettings.ifPresent(securitySettings -> {
//      FunctionCallBuilder routeCall = mountedComponentNavigation.navigateToMounted(
//              securitySettings.loginPanelComponentClass(),
//              router,
//              paramsAggregator -> paramsAggregator
//                      .name("redirectTo")
//                      .value(property(propertyBuilder -> propertyBuilder.value(redirectTo)))
//      );
//      body.appendStatement(expressionStatement(routeCall));
//    });
  }

  @VueComponentFunction
  public void setAuthenticationToken(@Name("token") NameBuilder token, BlockBuilder body) {
    body.appendStatement(expressionStatement(
            functionCall()
                    .target(property("Cookie", "set"))
                    .argument(stringLiteral(AUTHENTICATION_TOKEN_COOKIE))
                    .argument(token)
    ));
  }

  @VueComponentFunction
  public void writeTokenToRequest(@Name("request") NameBuilder request, BlockBuilder body) {
    body.appendStatement(expressionStatement(
            functionCall()
                    .target(property(property("request", "headers"), name("set")))
                    .argument(stringLiteral("Authorization"))
                    .argument(concatenateExpression().left(stringLiteral("Bearer ")).right(
                            functionCall()
                                    .target(property("localStorage", "getItem"))
                                    .argument(stringLiteral(AUTHENTICATION_TOKEN_COOKIE))
                    ))

    ));
  }

  @Override
  public NameBuilder vueName() {
    return name(LicketComponentSecurity.serviceName());
  }

  public FunctionCallBuilder callCheckAuthenticated(ComponentCallTargetOrigin targetOrigin) {
    return functionCall()
            .target(property(property(targetOrigin.buildTargetOrigin(), LicketComponentSecurity.serviceName()), name("checkAuthenticated")));
  }

  public FunctionCallBuilder callPerformAuthentication(ComponentCallTargetOrigin targetOrigin, PropertyNameBuilder routerReference) {
    return functionCall()
            .target(property(property(targetOrigin.buildTargetOrigin(), LicketComponentSecurity.serviceName()), name("performAuthentication")))
            .argument(routerReference)
            .argument(property("to", "fullPath"));
  }

  public FunctionCallBuilder callWriteTokenToRequest(ComponentCallTargetOrigin targetOrigin, NameBuilder requestReference) {
    return functionCall()
            .target(property(property(targetOrigin.buildTargetOrigin(), LicketComponentSecurity.serviceName()), name("writeTokenToRequest")))
            .argument(requestReference);
  }

  public FunctionCallBuilder callSetAuthenticationToken(ComponentCallTargetOrigin targetOrigin, NameBuilder tokenReference) {
    return functionCall()
            .target(property(property(targetOrigin.buildTargetOrigin(), LicketComponentSecurity.serviceName()), "setAuthenticationToken"))
            .argument(tokenReference);
  }
}

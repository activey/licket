package org.licket.core.module.application.security;

import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.hippo.vue.extend.AbstractCallableVueClass;
import org.licket.core.view.hippo.vue.extend.AbstractVueClassCallableAPI;
import org.licket.core.view.hippo.vue.extend.VueClass;
import org.licket.core.view.mount.MountedComponent;
import org.licket.core.view.mount.MountedComponents;
import org.licket.core.view.security.LicketComponentSecuritySettings;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.EqualCheckExpressionBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.IfStatementBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.licket.framework.hippo.VariableDeclarationBuilder;
import org.licket.framework.hippo.VariableInitializerBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.licket.framework.hippo.EqualCheckExpressionBuilder.equalCheckExpression;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.IfStatementBuilder.ifStatement;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.ReturnStatementBuilder.returnStatement;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;
import static org.licket.framework.hippo.VariableDeclarationBuilder.variableDeclaration;
import static org.licket.framework.hippo.VariableInitializerBuilder.variableInitializer;

/**
 * @author lukaszgrabski
 */
public class LicketComponentSecurity extends AbstractCallableVueClass {

  private static final String AUTHENTICATION_TOKEN_VARIABLE = "authenticationToken";

  @Autowired
  private Optional<LicketComponentSecuritySettings> securitySettings;

  @Autowired
  private MountedComponents mountedComponents;

  @VueComponentFunction
  public void checkAuthenticated(BlockBuilder body) {
    NameBuilder authenticationToken = name(AUTHENTICATION_TOKEN_VARIABLE);
    body.appendStatement(expressionStatement(variableDeclaration().variable(
            variableInitializer()
                    .target(authenticationToken)
                    .initializer(functionCall()
                            .target(property("localStorage", "getItem")).argument(stringLiteral(AUTHENTICATION_TOKEN_VARIABLE)))
    )));
    body.appendStatement(expressionStatement(
            ifStatement()
                    .condition(equalCheckExpression().negative().left(authenticationToken).right(name("null")))
                    .then(returnStatement().returnValue(name("true")))
    ));
    body.appendStatement(expressionStatement(returnStatement().returnValue(name("false"))));
  }

  @VueComponentFunction
  public void displayLoginPanel(@Name("router") NameBuilder router, BlockBuilder body) {
    securitySettings.ifPresent(securitySettings -> {
      MountedComponent mountedComponent = mountedComponents.mountedComponent(securitySettings.loginPanelComponentClass());
      body.appendStatement(expressionStatement(functionCall()
              .target(property(router, "push"))
              .argument(objectLiteral()
                      .objectProperty(propertyBuilder().name("name").value(stringLiteral(securitySettings.loginPanelComponentClass().getName())))
                      .objectProperty(propertyBuilder().name("params").value(mountedComponent.params())))));
    });
  }

  @VueComponentFunction
  public void getAuthenticationToken(BlockBuilder body) {
  }

  @VueComponentFunction
  public void setAuthenticationToken(@Name("token") NameBuilder token, BlockBuilder body) {
    body.appendStatement(expressionStatement(
            functionCall()
                    .target(property("localStorage", "setItem"))
                    .argument(stringLiteral(AUTHENTICATION_TOKEN_VARIABLE))
                    .argument(token)
    ));
  }

  public static String serviceName() {
    return "$licketComponentSecurity";
  }

  @Override
  public NameBuilder vueName() {
    return name(LicketComponentSecurity.serviceName());
  }

  @Override
  public LicketComponentSecurityCallableAPI call(ComponentFunctionCallback functionCallback) {
    return new LicketComponentSecurityCallableAPI(functionCallback);
  }

  public FunctionCallBuilder callCheckAuthenticated() {
    return functionCall()
            .target(property(property(name("vm"), LicketComponentSecurity.serviceName()), name("checkAuthenticated")));
  }

  public FunctionCallBuilder callDisplayLoginPanel(PropertyNameBuilder routerReference) {
    return functionCall()
            .target(property(property(name("vm"), LicketComponentSecurity.serviceName()), name("displayLoginPanel")))
            .argument(routerReference);
  }
}

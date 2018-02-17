package org.licket.core.module.application.security;

import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.hippo.vue.extend.VueClass;
import org.licket.core.view.mount.MountedComponent;
import org.licket.core.view.mount.MountedComponents;
import org.licket.core.view.security.LicketComponentSecuritySettings;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.ReturnStatementBuilder.returnStatement;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author lukaszgrabski
 */
public class LicketComponentSecurity implements VueClass {

  @Autowired
  private LicketComponentSecuritySettings securitySettings;

  @Autowired
  private MountedComponents mountedComponents;

  @VueComponentFunction
  public void checkAuthenticated(BlockBuilder body) {
    body.appendStatement(expressionStatement(returnStatement().returnValue(name("false"))));
  }

  @VueComponentFunction
  public void displayLoginPanel(@Name("router") NameBuilder router, BlockBuilder body) {
    MountedComponent mountedComponent = mountedComponents.mountedComponent(securitySettings.loginPanelComponentClass());
    body.appendStatement(expressionStatement(functionCall()
            .target(property(router, "push"))
            .argument(objectLiteral()
                    .objectProperty(propertyBuilder().name("name").value(stringLiteral(securitySettings.loginPanelComponentClass().getName())))
                    .objectProperty(propertyBuilder().name("params").value(mountedComponent.params())))));
  }

  public static String serviceName() {
    return "$licketComponentSecurity";
  }

  @Override
  public NameBuilder vueName() {
    return name(LicketComponentSecurity.serviceName());
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

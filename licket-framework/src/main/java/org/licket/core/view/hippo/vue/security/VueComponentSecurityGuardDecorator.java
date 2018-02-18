package org.licket.core.view.hippo.vue.security;

import org.licket.core.module.application.security.LicketComponentSecurity;
import org.licket.core.view.security.LicketComponentSecuritySettings;
import org.licket.core.view.security.LicketMountPointAccess;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.EqualCheckExpressionBuilder.equalCheckExpression;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.IfStatementBuilder.ifStatement;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author lukaszgrabski
 */
public class VueComponentSecurityGuardDecorator {

  @Autowired
  private Optional<LicketComponentSecuritySettings> componentSecuritySettings;

  @Autowired
  private LicketComponentSecurity componentSecurity;

  public BlockBuilder decorate(BlockBuilder functionBlock, LicketMountPointAccess access) {
    if (access.isPublic() || !componentSecuritySettings.isPresent()) {
      return functionBlock;
    }
    functionBlock.appendStatement(expressionStatement(
            ifStatement()
                    .condition(equalCheckExpression().left(componentSecurity.callCheckAuthenticated()).right(NameBuilder.name("false")))
                    .then(
                            block().appendStatement(expressionStatement(componentSecurity.callDisplayLoginPanel(property("vm", "$router"))))
                    )
    ));


    return functionBlock.appendStatement(expressionStatement(componentSecurity.callCheckAuthenticated()));
  }
}

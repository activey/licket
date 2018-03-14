package org.licket.spring.security.vue;

import org.licket.core.view.security.LicketComponentSecuritySettings;
import org.licket.core.view.security.LicketMountPointAccess;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.licket.core.view.hippo.ComponentCallTargetOrigin.fromVm;
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
    return functionBlock.appendStatement(expressionStatement(
            ifStatement()
                    .condition(equalCheckExpression().left(componentSecurity.callCheckAuthenticated(fromVm())).right(NameBuilder.name("false")))
                    .then(
                            block().appendStatement(expressionStatement(componentSecurity.callPerformAuthentication(fromVm(), property("vm", "$router"))))
                    )
    ));
  }
}

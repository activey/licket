package org.licket.core.view.mount;

import org.licket.core.resource.FootParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author lukaszgrabski
 */
public class MountedContainersResource extends AbstractJavascriptDynamicResource implements FootParticipatingResource {

  @Autowired
  private MountedComponentsService mountedComponentsService;

  @Override
  protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {

    PropertyNameBuilder appMounts = property("app", "mounts");
    scriptBlockBuilder.appendStatement(
            expressionStatement(assignment()
                    .left(appMounts)
                    .right(mountedContainersMappings())
            ));
  }

  private ObjectLiteralBuilder mountedContainersMappings() {
    ObjectLiteralBuilder objectLiteralBuilder = objectLiteral();
    mountedComponentsService.forEachMountPoint((componentClass, mountPoint) -> {
      objectLiteralBuilder.objectProperty(
              propertyBuilder()
                      .name(stringLiteral(componentClass.getName()))
                      .value(stringLiteral(mountPoint)));
    });
    return objectLiteralBuilder;
  }

  @Override
  public String getName() {
    return "Licket.application.mountpoints.js";
  }
}

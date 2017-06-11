package org.licket.core.resource.vue.boot;

import org.licket.core.LicketApplication;
import org.licket.core.resource.FootParticipatingResource;
import org.licket.core.resource.ResourceStorage;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.core.view.hippo.vue.component.VueComponentPropertiesDecorator;
import org.licket.core.view.mount.MountedComponentsService;
import org.licket.framework.hippo.ArrayLiteralBuilder;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.licket.framework.hippo.StringLiteralBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.String.format;
import static org.licket.framework.hippo.ArrayLiteralBuilder.arrayLiteral;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.NewExpressionBuilder.newExpression;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author activey
 */
public class VueInstanceInitializerResource extends AbstractJavascriptDynamicResource implements FootParticipatingResource {

    @Autowired
    private LicketApplication application;

    @Autowired
    private ResourceStorage resourceStorage;

    @Autowired
    private MountedComponentsService mountedComponentsService;

    @Override
    public String getName() {
        return "Licket.application.js";
    }

    @Override
    protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
        // initializing VueRouter instance
        PropertyNameBuilder appRouter = property("app", "router");
        scriptBlockBuilder.appendStatement(
                expressionStatement(assignment()
                        .left(appRouter)
                        .right(newExpression()
                                .target(name("VueRouter"))
                                .argument(vueRoutesDefinitions())))
        );

        // initializing Vue instance
        scriptBlockBuilder.appendStatement(
            expressionStatement(assignment()
                    .left(property(name("app"), name("instance")))
                    .right(newExpression()
                            .target(name("Vue"))
                            .argument(objectLiteral().objectProperty(
                                    propertyBuilder().name("router").value(appRouter)
                            ))))
        );
        // mounting Vue instance
        scriptBlockBuilder.appendStatement(
                expressionStatement(
                    functionCall()
                        .target(property(property("app", "instance"), "$mount"))
                        .argument(applicationRootId()))
        );
    }

    private ObjectLiteralBuilder vueRoutesDefinitions() {
        return objectLiteral().objectProperty(
                propertyBuilder()
                        .name("routes")
                        .arrayValue(componentsTree())
        );
    }

    private ArrayLiteralBuilder componentsTree() {
        ArrayLiteralBuilder components = arrayLiteral();

        // will mount root container to "/"
        application.traverseDown(mountedContainer -> {
            LicketMountPoint mountPoint = mountedContainer.getClass().getAnnotation(LicketMountPoint.class);
            if (mountPoint == null) {
                if (mountedContainer.isRoot(application)) {
                    addComponentMountPoint(components, mountedContainer, "/");
                    return false;
                }
                return false;
            }
            // iterating trough components annotated with @LicketMountPoint
            addComponentMountPoint(components, mountedContainer, mountPoint.value());
            return false;
        });
        return components;
    }

    private void addComponentMountPoint(ArrayLiteralBuilder components, LicketComponent<?> licketComponent, String mountPoint) {
        components.element(objectLiteral()
                .objectProperty(propertyBuilder().name("path").value(stringLiteral(mountPoint)))
                .objectProperty(propertyBuilder().name("component").value(
                        new VueComponentPropertiesDecorator(licketComponent, resourceStorage).decorate(objectLiteral()))
                )
        );

        // registering mounted component
        mountedComponentsService.setMountedLink(licketComponent.getClass(), mountPoint);
    }

    private StringLiteralBuilder applicationRootId() {
        return stringLiteral(format("#%s", application.rootComponentContainer().getId()));
    }
}

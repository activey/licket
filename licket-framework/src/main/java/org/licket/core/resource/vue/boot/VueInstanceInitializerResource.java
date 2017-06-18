package org.licket.core.resource.vue.boot;

import org.licket.core.LicketApplication;
import org.licket.core.resource.FootParticipatingResource;
import org.licket.core.resource.ResourceStorage;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.core.view.hippo.vue.component.VueComponentPropertiesDecorator;
import org.licket.core.view.mount.MountedComponents;
import org.licket.framework.hippo.ArrayLiteralBuilder;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionNodeBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.licket.framework.hippo.StringLiteralBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Modifier;

import static java.lang.String.format;
import static org.licket.framework.hippo.ArrayLiteralBuilder.arrayLiteral;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(VueInstanceInitializerResource.class);

    @Autowired
    private LicketApplication application;

    @Autowired
    private ResourceStorage resourceStorage;

    @Autowired
    private MountedComponents mountedComponents;

    @Override
    public String getName() {
        return "Licket.application.js";
    }

    @Override
    protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
        registerMountPoints();

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
                    addComponentMountPoint(components, mountedContainer);
                    return false;
                }
                return false;
            }
            addComponentMountPoint(components, mountedContainer);
            return false;
        });
        return components;
    }

    private void addComponentMountPoint(ArrayLiteralBuilder components, LicketComponent<?> licketComponent) {
        components.element(objectLiteral()
                .objectProperty(propertyBuilder().name("path").value(stringLiteral(mountedComponents.mountedComponent(licketComponent.getClass()).path())))
                .objectProperty(propertyBuilder().name("name").value(stringLiteral(licketComponent.getClass().getName())))
                .objectProperty(propertyBuilder().name("component").value(
                    new VueComponentPropertiesDecorator(licketComponent, resourceStorage).decorate(objectLiteral()))
                )
                .objectProperty(propertyBuilder().name("beforeEnter").value(beforeEnterFunction(licketComponent)))
        );
    }

    // TODO temporary
    private FunctionNodeBuilder beforeEnterFunction(LicketComponent<?> licketComponent) {
        return functionNode()
            .param(name("to"))
            .param(name("from"))
            .param(name("next"))
            .body(block()
                .appendStatement(expressionStatement(
                    functionCall()
                        .target(property("console", "log"))
                        .argument(stringLiteral(mountedComponents.mountedComponent(licketComponent.getClass()).path()))))
                .appendStatement(expressionStatement(
                    functionCall()
                        .target(name("next"))))
                );
    }

    private StringLiteralBuilder applicationRootId() {
        return stringLiteral(format("#%s", application.rootComponentContainer().getId()));
    }

    private void registerMountPoints() {
        application.traverseDown(mountedContainer -> {
            LicketMountPoint mountPoint = mountedContainer.getClass().getAnnotation(LicketMountPoint.class);
            if (mountPoint == null) {
                if (mountedContainer.isRoot(application)) {
                    registerComponentMountPoint(mountedContainer, "/");
                    return false;
                }
                return false;
            }
            // iterating trough components annotated with @LicketMountPoint
            registerComponentMountPoint(mountedContainer, mountPoint.value());
            return false;
        });
    }

    private void registerComponentMountPoint(LicketComponent<?> licketComponent, String mountPoint) {
        if (isComponentAbstract(licketComponent)) {
            LOGGER.warn("Currently not supported to mount abstract component like this one: {}.", licketComponent.getCompositeId().getValue());
            return;
        }
        // registering mounted component
        mountedComponents.setMountedLink(licketComponent.getClass(), mountPoint);
    }

    private boolean isComponentAbstract(LicketComponent<?> licketComponent) {
        return Modifier.isAbstract(licketComponent.getClass().getModifiers());
    }
}
package org.licket.core.resource.boot;

import org.licket.core.LicketApplication;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.core.view.angular.ComponentBuilder;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.framework.hippo.BlockBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.view.LicketUrls.componentContainerViewUrl;
import static org.licket.core.view.angular.ClassConstructorBuilder.constructorBuilder;
import static org.licket.core.view.angular.ComponentBuilder.component;
import static org.licket.core.view.angular.ComponentClassBuilder.classBuilder;
import static org.licket.core.view.angular.ComponentCommunicationServiceBuilder.componentCommunicationService;

/**
 * @author activey
 */
public class AngularComponentsJavascriptResource extends AbstractJavascriptDynamicResource implements HeadParticipatingResource {

    @Autowired
    private LicketApplication licketApplication;

    @Override
    public String getName() {
        return "Licket.components.js";
    }

    @Override
    protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
        licketApplication.traverseDownContainers(container -> {
            generateComponentContainerCode(scriptBlockBuilder, container);
            return true;
        });

        // generating component services
        scriptBlockBuilder.prependStatement(componentCommunicationService()
                .serviceName("ComponentCommunicationService"));

    }

    private void generateComponentContainerCode(BlockBuilder scriptBlockBuilder, LicketComponentContainer<?> container) {
        if (!container.getComponentContainerView().isExternalized()) {
            return;
        }
        ComponentBuilder componentBuilder = component();
        appendContainerChildren(container, componentBuilder);

        scriptBlockBuilder.prependStatement(componentBuilder
                .selector(componentSelector(container))
                .templateUrl(componentContainerViewUrl(container))
                .componentName(componentName(container))
                .clazz(classBuilder()
                        .constructor(constructorBuilder(container))));
    }

    private void appendContainerChildren(LicketComponentContainer<?> container, final ComponentBuilder componentBuilder) {
        container.traverseDownContainers(childContainer -> {
            if (!childContainer.getComponentContainerView().isExternalized()) {
                return false;
            }
            componentBuilder.componentDependency(childContainer);
            return true;
        });
    }

    private String componentSelector(LicketComponentContainer<?> container) {
        return container.getId();
    }

    private String componentName(LicketComponentContainer<?> container) {
        return container.getCompositeId().getNormalizedValue();
    }
}

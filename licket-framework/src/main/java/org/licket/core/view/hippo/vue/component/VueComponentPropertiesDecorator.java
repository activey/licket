package org.licket.core.view.hippo.vue.component;

import com.google.common.io.CharStreams;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.resource.Resource;
import org.licket.core.resource.ResourceStorage;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.extend.OnVueBeforeRouteEnterDecorator;
import org.licket.core.view.hippo.vue.extend.OnVueCreatedDecorator;
import org.licket.core.view.hippo.vue.extend.OnVueMountedDecorator;
import org.licket.core.view.hippo.vue.extend.VueExtendMethodsDecorator;
import org.licket.framework.hippo.ArrayLiteralBuilder;
import org.licket.framework.hippo.FunctionNodeBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.StringLiteralBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import static org.licket.core.view.hippo.ComponentModelDecorator.fromComponentModel;
import static org.licket.framework.hippo.ArrayLiteralBuilder.arrayLiteral;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.ReturnStatementBuilder.returnStatement;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author activey
 */
public class VueComponentPropertiesDecorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(VueComponentPropertiesDecorator.class);

    @Autowired
    private ResourceStorage resourceStorage;

    @Autowired
    private LicketRemote licketRemote;

    @Autowired
    private OnVueBeforeRouteEnterDecorator routeEnterDecorator;

    public ObjectLiteralBuilder decorate(LicketComponent<?> component, ObjectLiteralBuilder componentObjectBuilder) {
        if (component.isStateful()) {
            componentObjectBuilder.objectProperty(propertyBuilder().name("data").value(data(component)));
        } else {
            componentObjectBuilder.objectProperty(propertyBuilder().name("props").value(props()));
        }
        componentObjectBuilder
                .objectProperty(propertyBuilder().name("template").value(template(component)))
                .objectProperty(propertyBuilder().name("methods").value(methods(component)))
                .objectProperty(propertyBuilder().name("components").value(nestedComponents(component)))
                .objectProperty(propertyBuilder().name("created").value(created(component)))
                .objectProperty(propertyBuilder().name("mounted").value(mounted(component)));

        // if it is mounted component or root
        routeEnterDecorator.decorate(component, componentObjectBuilder);
//        afterMountDecorator.decorate(component, componentObjectBuilder);

        return componentObjectBuilder;
    }

    private ObjectLiteralBuilder methods(LicketComponent<?> component) {
        return VueExtendMethodsDecorator.fromClass(component).decorate(objectLiteral());
    }

    private FunctionNodeBuilder data(LicketComponent<?> component) {
        ObjectLiteralBuilder modelData = objectLiteral();
        try {
            fromComponentModel(component.getComponentModel()).decorate(modelData);
        } catch (IOException e) {
            LOGGER.error("An error occurred while generating component model data. Returning empty model.", e);
        }
        functionNode().body(
                block()
        );

        return functionNode().body(block().appendStatement(returnStatement()
            .returnValue(objectLiteral().objectProperty(propertyBuilder().name("model").value(modelData)))));
    }

    private ArrayLiteralBuilder props() {
        return arrayLiteral().element(StringLiteralBuilder.stringLiteral("model"));
    }

    private ObjectLiteralBuilder nestedComponents(LicketComponent<?> component) {
        ObjectLiteralBuilder nestedComponents = objectLiteral();
        component.traverseDown(nestedComponent -> {
            if (nestedComponent.isCustom() || !nestedComponent.getView().hasTemplate() || !nestedComponent.isStateful()) {
                return false;
            }
            ObjectLiteralBuilder nestedComponentObject = objectLiteral();
            decorate(nestedComponent, nestedComponentObject);
            nestedComponents.objectProperty(
                    propertyBuilder()
                            .name(stringLiteral(nestedComponent.getId()))
                            .value(nestedComponentObject));

            return false;
        });
        return nestedComponents;
    }

    private StringLiteralBuilder template(LicketComponent<?> component) {
        Optional<Resource> componentViewResourceOptional = resourceStorage
            .getResource(component.getCompositeId().getValue());
        if (componentViewResourceOptional.isPresent()) {
            try {
                return stringLiteral(
                    CharStreams.toString(new InputStreamReader(componentViewResourceOptional.get().getStream())));
            } catch (IOException e) {
                LOGGER.error("An error occurred while serializing component view.", e);
            }
        }
        LOGGER.error("Unable to find template resource for component: {}.", component.getCompositeId().getValue());

        return stringLiteral("<!-- Unable to find template resource -->");
    }

    private FunctionNodeBuilder created(LicketComponent<?> component) {
        return functionNode().body(OnVueCreatedDecorator.fromVueClass(component).decorate(block()));
    }

    private FunctionNodeBuilder mounted(LicketComponent<?> component) {
        return functionNode().body(OnVueMountedDecorator.fromVueClass(component).decorate(block()));
    }
}

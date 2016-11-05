package org.licket.core.view.hippo.vue.component;

import static org.licket.core.view.hippo.ComponentModelDecorator.fromComponentModel;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.ReturnStatementBuilder.returnStatement;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import org.licket.core.resource.Resource;
import org.licket.core.resource.ResourceStorage;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.extend.OnVueCreatedDecorator;
import org.licket.core.view.hippo.vue.extend.OnVueMountedDecorator;
import org.licket.core.view.hippo.vue.extend.VueExtendMethodsDecorator;
import org.licket.framework.hippo.FunctionNodeBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.StringLiteralBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.io.CharStreams;

/**
 * @author activey
 */
public class VueComponentPropertiesDecorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(VueComponentPropertiesDecorator.class);

    private LicketComponent<?> component;
    private ResourceStorage resourceStorage;

    public VueComponentPropertiesDecorator(LicketComponent<?> component, ResourceStorage resourceStorage) {
        this.component = component;
        this.resourceStorage = resourceStorage;
    }

    public void decorate(ObjectLiteralBuilder componentObjectBuilder) {
        componentObjectBuilder
            .objectProperty(propertyBuilder().name(stringLiteral(component.getId())).value(componentProperties()));
    }

    private ObjectLiteralBuilder componentProperties() {
        return objectLiteral()
                .objectProperty(propertyBuilder().name("template").value(template()))
                .objectProperty(propertyBuilder().name("data").value(data()))
                .objectProperty(propertyBuilder().name("methods").value(methods()))
                .objectProperty(propertyBuilder().name("components").value(nestedComponents()))
                .objectProperty(propertyBuilder().name("created").value(created()))
                .objectProperty(propertyBuilder().name("mounted").value(mounted()));
    }

    private ObjectLiteralBuilder methods() {
        return VueExtendMethodsDecorator.fromClass(component).decorate(objectLiteral());
    }

    private FunctionNodeBuilder data() {
        ObjectLiteralBuilder modelData = objectLiteral();
        try {
            fromComponentModel(component.getComponentModel()).decorate(modelData);
        } catch (IOException e) {
            LOGGER.error("An error occurred while generating component model data. Returning empty model.", e);
        }
        return functionNode()
                .body(block().appendStatement(
                        returnStatement().returnValue(
                                objectLiteral().objectProperty(propertyBuilder().name("model").value(modelData)))
                    ));
    }

    private ObjectLiteralBuilder nestedComponents() {
        ObjectLiteralBuilder nestedComponents = objectLiteral();
        component.traverseDown(nestedComponent -> {
            if (!nestedComponent.getView().hasTemplate()) {
                return false;
            }
            new VueComponentPropertiesDecorator(nestedComponent, resourceStorage).decorate(nestedComponents);
            return false;
        });
        return nestedComponents;
    }

    private StringLiteralBuilder template() {
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
        // TODO use xml parser stuff
        return stringLiteral("<!-- Unable to render template, check logs for details. -->");
    }

    private FunctionNodeBuilder created() {
        return functionNode().body(OnVueCreatedDecorator.fromVueClass(component).decorate(block()));
    }

    private FunctionNodeBuilder mounted() {
        return functionNode().body(OnVueMountedDecorator.fromVueClass(component).decorate(block()));
    }
}

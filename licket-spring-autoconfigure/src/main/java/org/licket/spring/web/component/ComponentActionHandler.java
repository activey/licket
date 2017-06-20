package org.licket.spring.web.component;

import static org.joor.Reflect.on;
import org.joor.ReflectException;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.core.view.link.ComponentFunctionCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author activey
 */
public class ComponentActionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentActionHandler.class);

    private LicketComponent<?> component;

    private ComponentActionHandler(LicketComponent<?> component) {
        this.component = component;
    }

    public static ComponentActionHandler onComponent(LicketComponent<?> component) {
        return new ComponentActionHandler(component);
    }

    public final void trySubmitForm(JsonNode formData, ComponentActionCallback componentActionCallback) {
        try {
            LOGGER.trace("Trying to submit form [{}].", component.getId());

            on(component).call("submitForm", componentModelFromActionRequest(formData), componentActionCallback);
        } catch (JsonProcessingException e) {
            LOGGER.error("An error occurred while deserializing component model for: {}.", component.getId(), e);
        } catch (ReflectException reflectException) {
            LOGGER.error("An error occurred while setting component model for: {}.", component.getId(),
                reflectException);
        }
    }

    public final void tryMountComponent(JsonNode componentMountingParams, ComponentFunctionCallback componentFunctionCallback) {
        try {
            LOGGER.trace("Trying to mount component [{}].", component.getCompositeId().getValue());

            on(component).call("mountComponent", componentModelFromActionRequest(componentMountingParams), componentFunctionCallback);
        } catch (JsonProcessingException e) {
            LOGGER.error("An error occurred while deserializing component model for: {}.", component.getId(), e);
        } catch (ReflectException reflectException) {
            LOGGER.error("An error occurred while setting component model for: {}.", component.getId(),
                    reflectException);
        }
    }

    public final void tryLinkClick(ComponentActionCallback componentActionCallback) {
        try {
            LOGGER.trace("Trying to handle link click [{}].", component.getId());
            // possible only on
            on(component).call("invokeAction", componentActionCallback);
        } catch (ReflectException reflectException) {
            LOGGER.error("An error occurred while setting component model for: [%s]", component.getId(),
                reflectException);
        }
    }

    private Object componentModelFromActionRequest(JsonNode formData) throws JsonProcessingException {
        return new ObjectMapper().treeToValue(formData, component.getComponentModelClass());
    }
}

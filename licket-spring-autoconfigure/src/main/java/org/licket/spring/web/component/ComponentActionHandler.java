package org.licket.spring.web.component;

import static org.joor.Reflect.on;
import org.joor.ReflectException;
import org.licket.core.view.LicketComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author activey
 */
public class ComponentActionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentActionHandler.class);

    private LicketComponent<?> actionComponent;

    public ComponentActionHandler(LicketComponent<?> actionComponent) {
        this.actionComponent = actionComponent;
    }

    public void callAction(String method, ComponentActionRequest actionRequest) {
        if (actionRequest.getData() == null) {
            on(actionComponent).call(method);
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object value = mapper.treeToValue(actionRequest.getData(), actionComponent.getComponentModelClass());
            on(actionComponent).call(method, value);
        } catch (JsonProcessingException e) {
            LOGGER.error("An error occurred while deserializing component model for: [%s]", actionComponent.getId(), e);
        } catch (ReflectException reflectException) {
            LOGGER.error("An error occurred while setting component model for: [%s]", actionComponent.getId(),
                reflectException);
        }
    }
}

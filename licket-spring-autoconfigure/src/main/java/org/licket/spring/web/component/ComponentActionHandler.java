package org.licket.spring.web.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joor.ReflectException;
import org.licket.core.view.ComponentActionCallback;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.mount.params.MountingParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.joor.Reflect.on;

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
      LOGGER.trace("Trying to submit form [{}].", component.getCompositeId().getValue());

      on(component).call("submitForm", componentModelFromActionRequest(formData), componentActionCallback);
    } catch (JsonProcessingException e) {
      LOGGER.error("An error occurred while deserializing component model for: {}.", component.getCompositeId().getValue(), e);
    } catch (ReflectException reflectException) {
      LOGGER.error("An error occurred while setting component model for: {}.", component.getCompositeId().getValue(),
              reflectException);
    }
  }

  public final void tryLinkClick(JsonNode modelData, ComponentActionCallback componentActionCallback) {
    try {
      LOGGER.trace("Trying to handle link click [{}].", component.getCompositeId().getValue());
      // possible only on
      on(component).call("invokeAction", componentModelFromActionRequest(modelData), componentActionCallback);
    } catch (JsonProcessingException e) {
      LOGGER.error("An error occurred while deserializing component model for: {}.", component.getCompositeId().getValue(), e);
    } catch (ReflectException reflectException) {
      LOGGER.error("An error occurred while setting component model for: [%s]", component.getCompositeId().getValue(),
              reflectException);
    }
  }

  public final void tryMountComponent(JsonNode componentMountingParams, ComponentActionCallback componentActionCallback) {
    try {
      LOGGER.trace("Trying to mount component [{}].", component.getCompositeId().getValue());

      on(component).call("mountComponent", mountingParamsFromActionRequest(componentMountingParams), componentActionCallback);
    } catch (JsonProcessingException e) {
      LOGGER.error("An error occurred while deserializing component model for: {}.", component.getCompositeId().getValue(), e);
    } catch (ReflectException reflectException) {
      LOGGER.error("An error occurred while setting component model for: {}.", component.getCompositeId().getValue(),
              reflectException);
    }
  }

  private Object componentModelFromActionRequest(JsonNode formData) throws JsonProcessingException {
    return new ObjectMapper().treeToValue(formData, component.getComponentModelClass());
  }

  private MountingParams mountingParamsFromActionRequest(JsonNode mountingParams) throws JsonProcessingException {
    MountingParams params = new MountingParams();
    mountingParams.fieldNames().forEachRemaining(fieldName -> params.newParam(fieldName, mountingParams.get(fieldName).asText()));
    return params;
  }
}

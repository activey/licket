package org.licket.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.licket.core.view.ComponentActionCallback;
import org.licket.core.view.LicketComponent;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author activey
 */
public class LicketComponentModelGroup {

  private final Map<String, Object> modelGroup = newHashMap();
  private final Map<String, JsonNode> patchGroup = newHashMap();

  @JsonProperty("model")
  public Map<String, Object> getModelGroup() {
    return modelGroup;
  }

  @JsonProperty("patch")
  public Map<String, JsonNode> getPatchGroup() {
    return patchGroup;
  }

  public void addModel(String componentId, Object componentModelObject) {
    modelGroup.put(componentId, componentModelObject);
  }

  public LicketComponentModelGroup collectModels(ComponentActionCallback componentActionCallback) {
    componentActionCallback.forEachToBeReloaded(this::addComponentModel);
    return this;
  }

  private void addComponentModel(LicketComponent<?> component, boolean patch) {
    if (patch) {
      addPatch(component.getCompositeId().getValue(), component.getComponentModel().getPatch().getJsonPatch());
      return;
    }
    component.getComponentModel().get().ifPresent(componentModel -> addModel(component.getCompositeId().getValue(), componentModel));

  }

  private void addPatch(String componentId, JsonNode componentModelPatch) {
    patchGroup.put(componentId, componentModelPatch);
  }
}

package org.licket.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author activey
 */
public class LicketComponentModelGroup {

    private final Map<String, Object> modelGroup = newHashMap();

    @JsonProperty("model")
    public Map<String, Object> getModelGroup() {
        return modelGroup;
    }

    public void addModel(String componentId, Object componentModel) {
        modelGroup.put(componentId, componentModel);
    }

}

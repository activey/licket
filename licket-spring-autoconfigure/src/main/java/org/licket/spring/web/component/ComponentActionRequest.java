package org.licket.spring.web.component;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

/**
 * @author activey
 */
public class ComponentActionRequest implements Serializable {

    private String compositeId;
    private JsonNode data;

    public String getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(String compositeId) {
        this.compositeId = compositeId;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }
}

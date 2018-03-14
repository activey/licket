package org.licket.spring.websocket.reload;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author lukaszgrabski
 */
public class LicketComponentPatch {

  private String compositeId;
  private JsonNode patch;

  public LicketComponentPatch() {

  }

  public LicketComponentPatch(String compositeId, JsonNode patch) {
    this.compositeId = compositeId;
    this.patch = patch;
  }

  public String getCompositeId() {
    return compositeId;
  }

  public void setCompositeId(String compositeId) {
    this.compositeId = compositeId;
  }

  public JsonNode getPatch() {
    return patch;
  }

  public void setPatch(JsonNode patch) {
    this.patch = patch;
  }
}

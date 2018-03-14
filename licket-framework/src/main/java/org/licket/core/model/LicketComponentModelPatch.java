package org.licket.core.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.flipkart.zjsonpatch.JsonDiff.asJson;

/**
 * @author lukaszgrabski
 */
public final class LicketComponentModelPatch<T> {

  private final JsonNode jsonPatch;
  private ObjectMapper objectMapper = new ObjectMapper();

  public LicketComponentModelPatch(T oldValue, T newValue) {
    jsonPatch = asJson(objectMapper.valueToTree(oldValue), objectMapper.valueToTree(newValue));
  }

  public JsonNode getJsonPatch() {
    return jsonPatch;
  }
}

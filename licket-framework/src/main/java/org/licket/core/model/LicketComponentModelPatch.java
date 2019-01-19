package org.licket.core.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.flipkart.zjsonpatch.JsonDiff.asJson;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author lukaszgrabski
 */
public final class LicketComponentModelPatch<T> {

  private ObjectMapper objectMapper = new ObjectMapper();
  private final JsonNode jsonPatch;


  public LicketComponentModelPatch(T oldValue, T newValue) {
    checkNotNull(oldValue);
    checkNotNull(newValue);

    jsonPatch = asJson(objectMapper.valueToTree(oldValue), objectMapper.valueToTree(newValue));
  }

  public LicketComponentModelPatch(T newValue) {
    checkNotNull(newValue);

    jsonPatch = asJson(objectMapper.createObjectNode(), objectMapper.valueToTree(newValue));
  }

  public JsonNode getJsonPatch() {
    return jsonPatch;
  }
}

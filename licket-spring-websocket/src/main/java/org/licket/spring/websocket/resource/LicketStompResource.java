package org.licket.spring.websocket.resource;

import org.licket.core.resource.FootParticipatingResource;
import org.licket.core.resource.javascript.JavascriptTemplateResource;
import org.licket.spring.websocket.config.WebSocketProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author lukaszgrabski
 */
public class LicketStompResource extends JavascriptTemplateResource implements FootParticipatingResource {

  @Autowired
  private WebSocketProperties webSocketProperties;

  public LicketStompResource() {
    super("Licket.stomp.js", "js/Licket.stomp.template.js");
  }

  @Override
  protected void collectTemplateVariables(Map<String, Object> templateVariables) {
    templateVariables.put("stomp_uri", webSocketProperties.getEndpoint());
  }
}

package org.licket.spring.websocket.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author activey
 */
public class StompResource extends JavascriptStaticResource implements HeadParticipatingResource {

  public StompResource() {
    super("stomp.js", "stompjs/lib/stomp.js");
  }
}

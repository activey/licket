package org.licket.spring.websocket.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author activey
 */
public class SockJSResource extends JavascriptStaticResource implements HeadParticipatingResource {

  public SockJSResource() {
    super("sockjs.js", "sockjs-client/dist/sockjs.js");
  }
}

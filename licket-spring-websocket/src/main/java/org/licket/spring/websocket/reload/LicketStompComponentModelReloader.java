package org.licket.spring.websocket.reload;

import org.licket.core.view.LicketComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static java.util.Arrays.stream;

/**
 * @author lukaszgrabski
 */
public class LicketStompComponentModelReloader {

  private static final String TOPIC_COMPONENT_PATCH = "/topic/component/patch";
  private static final String TOPIC_COMPONENT_MODEL = "/topic/component/model";

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  public final void reloadForUser(String userName, LicketComponent<?>... components) {
    stream(components).forEach(component -> messagingTemplate.convertAndSendToUser(userName,
            TOPIC_COMPONENT_MODEL,
            new LicketComponentModel(component.getCompositeId().getValue(), component.getComponentModel().get())
    ));
  }

  public final void patchForUser(String userName, LicketComponent<?>... components) {
    stream(components).forEach(component -> messagingTemplate.convertAndSendToUser(userName,
            TOPIC_COMPONENT_PATCH,
            new LicketComponentPatch(component.getCompositeId().getValue(), component.getComponentModel().getPatch().getJsonPatch())
    ));
  }
}

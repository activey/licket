package org.licket.spring.websocket;

import org.licket.core.LicketApplication;
import org.licket.spring.websocket.config.WebSocketConfig;
import org.licket.spring.websocket.config.WebSocketProperties;
import org.licket.spring.websocket.reload.LicketStompComponentModelReloader;
import org.licket.spring.websocket.resource.LicketStompResource;
import org.licket.spring.websocket.resource.SockJSResource;
import org.licket.spring.websocket.resource.StompResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(LicketApplication.class)
@Import({WebSocketConfig.class})
@EnableConfigurationProperties(WebSocketProperties.class)
public class LicketWebsocketAutoconfigure {

  @Bean
  public LicketStompResource licketStompResource() {
    return new LicketStompResource();
  }

  @Bean
  public SockJSResource sockJsResource() {
    return new SockJSResource();
  }

  @Bean
  public StompResource stompResource() {
    return new StompResource();
  }

  @Bean
  public LicketStompComponentModelReloader licketStompComponentReloader() {
    return new LicketStompComponentModelReloader();
  }
}

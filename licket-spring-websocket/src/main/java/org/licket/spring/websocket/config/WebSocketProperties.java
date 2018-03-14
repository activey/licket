package org.licket.spring.websocket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lukaszgrabski
 */
@Configuration
@ConfigurationProperties(prefix = "stomp")
public class WebSocketProperties {

  private String applicationPrefix = "/app";
  private String brokerPrefix = "/topic";
  private String endpoint = "/stomp";

  public String getApplicationPrefix() {
    return applicationPrefix;
  }

  public void setApplicationPrefix(String applicationPrefix) {
    this.applicationPrefix = applicationPrefix;
  }

  public String getBrokerPrefix() {
    return brokerPrefix;
  }

  public void setBrokerPrefix(String brokerPrefix) {
    this.brokerPrefix = brokerPrefix;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }
}

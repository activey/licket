package org.licket.spring.decorator;

import org.licket.core.view.hippo.vue.component.VueComponentPropertiesDecorator;
import org.licket.core.view.hippo.vue.extend.OnVueBeforeRouteEnterDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lukaszgrabski
 */
@Configuration
public class LicketComponentDecoratorsConfiguration {

  @Bean
  public VueComponentPropertiesDecorator propertiesDecorator() {
    return new VueComponentPropertiesDecorator();
  }

  @Bean
  public OnVueBeforeRouteEnterDecorator beforeRouteEnterDecorator() {
    return new OnVueBeforeRouteEnterDecorator();
  }
}

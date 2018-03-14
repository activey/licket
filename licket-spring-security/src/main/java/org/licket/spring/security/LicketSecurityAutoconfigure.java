package org.licket.spring.security;

import org.licket.core.LicketApplication;
import org.licket.core.resource.FootParticipatingResource;
import org.licket.core.view.mount.MountComponentInterceptor;
import org.licket.spring.security.interceptor.SecurityComponentInterceptor;
import org.licket.spring.security.resource.LicketComponentSecurityInterceptorResource;
import org.licket.spring.security.resource.TinyCookieResource;
import org.licket.spring.security.vue.LicketComponentSecurity;
import org.licket.spring.security.vue.VueComponentSecurityGuardDecorator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(LicketApplication.class)
@Import({WebSecurityConfiguration.class})
public class LicketSecurityAutoconfigure {

  @Bean
  public WebUrls webUrls() {
    return new WebUrls();
  }

  @Bean
  public LicketComponentSecurity componentSecurity() {
    return new LicketComponentSecurity();
  }

  @Bean
  public FootParticipatingResource licketComponentSecurityInterceptorResource() {
    return new LicketComponentSecurityInterceptorResource();
  }

  @Bean
  public VueComponentSecurityGuardDecorator componentSecurityGuardDecorator() {
    return new VueComponentSecurityGuardDecorator();
  }

  @Bean
  public MountComponentInterceptor securityMountPointInterceptor() {
    return new SecurityComponentInterceptor();
  }

  @Bean
  public TinyCookieResource vueCookieResource() {
    return new TinyCookieResource();
  }
}

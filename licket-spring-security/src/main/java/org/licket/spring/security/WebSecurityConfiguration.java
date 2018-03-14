package org.licket.spring.security;

import org.licket.spring.security.token.TokenAuthenticationService;
import org.licket.spring.security.token.TokenPassFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.annotation.SessionScope;

/**
 * @author lukaszgrabski
 */
@EnableWebSecurity
@Configurable
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private OAuth2ClientContext oauth2ClientContext;

  @Autowired
  private AuthorizationCodeResourceDetails authorizationCodeResourceDetails;

  @Autowired
  private ResourceServerProperties resourceServerProperties;

  @Autowired
  private WebUrls webUrls;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers(webUrls.getPublicAntMatchers()).permitAll();
    http
            .authorizeRequests()
            .anyRequest().fullyAuthenticated()
            .and()
            .logout()
            .logoutSuccessUrl("/")
            .permitAll()
            .and()
            .addFilterAt(oauth2Filter(), BasicAuthenticationFilter.class)
            .addFilterBefore(tokenPassFilter(), BasicAuthenticationFilter.class)
            .csrf()
            .disable();
  }

  @Bean
  public TokenAuthenticationService tokenAuthenticationService() {
    return new TokenAuthenticationService();
  }

  @Bean
  public TokenPassFilter tokenPassFilter() {
    return new TokenPassFilter();
  }

  @Bean
  @SessionScope
  public OAuth2RestTemplate restTemplate() {
    return new OAuth2RestTemplate(authorizationCodeResourceDetails, oauth2ClientContext);
  }

  private OAuth2ClientAuthenticationProcessingFilter oauth2Filter() {
    OAuth2ClientAuthenticationProcessingFilter oAuth2Filter = new OAuth2ClientAuthenticationProcessingFilter(
            "/oauth2/gitlab/login");
    oAuth2Filter.setRestTemplate(restTemplate());
    oAuth2Filter.setTokenServices(new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(),
            resourceServerProperties.getClientId()));
    return oAuth2Filter;
  }
}
package org.licket.spring.web.security;

import org.licket.spring.web.security.token.TokenAuthenticationFilter;
import org.licket.spring.web.security.token.TokenAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author lukaszgrabski
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .csrf().disable()
            .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("test").password("test").roles("AUTHENTICATED");
  }

  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() throws Exception {
    return new TokenAuthenticationFilter(authenticationManager());
  }

  @Bean
  public TokenAuthenticationService tokenAuthenticationService() {
    return new TokenAuthenticationService();
  }
}

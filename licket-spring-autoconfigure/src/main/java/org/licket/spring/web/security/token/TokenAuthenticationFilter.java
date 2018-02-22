package org.licket.spring.web.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lukaszgrabski
 */
public class TokenAuthenticationFilter extends GenericFilterBean {

  private final AuthenticationManager authenticationManager;

  @Autowired
  private TokenAuthenticationService tokenAuthenticationService;

  public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    Authentication authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) servletRequest);
    if (authentication != null) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }
    authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }
    tokenAuthenticationService.addAuthentication((HttpServletResponse) servletResponse, authentication.getName());
    filterChain.doFilter(servletRequest, servletResponse);
  }
}

package org.licket.spring.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lukaszgrabski
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationManager authenticationManager;

  @Autowired
  private TokenAuthenticationService tokenAuthenticationService;

  public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    if (authentication == null) {
//      authentication = tokenAuthenticationService.getAuthentication(servletRequest);
//      if (authentication != null) {
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        filterChain.doFilter(servletRequest, servletResponse);
//        return;
//      }
//      filterChain.doFilter(servletRequest, servletResponse);
//      return;
//    }
//    tokenAuthenticationService.addAuthentication(servletResponse, authentication.getName());
//    filterChain.doFilter(servletRequest, servletResponse);
  }
}

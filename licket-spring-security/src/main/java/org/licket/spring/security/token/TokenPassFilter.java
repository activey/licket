package org.licket.spring.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

/**
 * @author lukaszgrabski
 */
public class TokenPassFilter extends OncePerRequestFilter {

  private static final String HEADER_AUTHORIZATION = "Authorization";
  private static final String HEADER_TOKEN = "X-AUTH-TOKEN";

  @Autowired
  private OAuth2ClientContext clientContext;

  @Autowired
  private ResourceServerTokenServices tokenServices;

  public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && (authentication instanceof OAuth2Authentication)) {
      OAuth2AccessToken accessToken = clientContext.getAccessToken();
      createTokenCookie(servletResponse, accessToken);
    } else {
      // checking header
      getAuthHeader(servletRequest).ifPresent(tokenHeader -> getBearerValue(tokenHeader).ifPresent(bearer -> {
        OAuth2Authentication oAuth2Authentication = tokenServices.loadAuthentication(bearer);
        SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);
      }));
      // checking cookie
      getAuthCookie(servletRequest).ifPresent(tokenCookie -> {
        OAuth2Authentication oAuth2Authentication = tokenServices.loadAuthentication(tokenCookie.getValue());
        SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);
      });
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  private Optional<Cookie> getAuthCookie(HttpServletRequest request) {
    if (request.getCookies() == null) {
      return empty();
    }
    return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(HEADER_TOKEN)).findFirst();
  }

  private void createTokenCookie(HttpServletResponse response, OAuth2AccessToken accessToken) {
    response.addCookie(new Cookie(HEADER_TOKEN, accessToken.getValue()));
  }

  private Optional<String> getBearerValue(String tokenHeaderValue) {
    String[] headerParts = tokenHeaderValue.split(" ");
    if (headerParts.length != 2) {
      return Optional.empty();
    }
    return Optional.of(headerParts[1]);
  }

  private Optional<String> getAuthHeader(HttpServletRequest request) {
    return ofNullable(request.getHeader(HEADER_AUTHORIZATION));
  }
}
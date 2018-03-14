package org.licket.spring.security.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author lukaszgrabski
 */
public class TokenAuthenticationService {

  static final long EXPIRATIONTIME = 864_000_000; // 10 days
  static final String SECRET = "ThisIsASecret";
  static final String TOKEN_PREFIX = "Bearer";
  static final String HEADER_STRING = "Authorization";

  public String authenticate(Authentication authentication) {
    // setting auth context
    SecurityContextHolder
            .getContext()
            .setAuthentication(authentication);
    return Jwts.builder()
            .setSubject(authentication.getName())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
  }

  public void addAuthentication(HttpServletResponse httpServletResponse, String subject) {
    String JWT = Jwts.builder()
            .setSubject(subject)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    httpServletResponse.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
  }

  public void addAuthentication(HttpServletResponse httpServletResponse, OAuth2Authentication subject) {
//    OAuth2AccessToken accessToken = clientContext.getAccessToken();
//
//    String JWT = Jwts.builder()
//            .setSubject(subject.getName())
//            .setExpiration(accessToken.getExpiration())
//            .signWith(SignatureAlgorithm.HS512, SECRET)
//            .set(subject.getAuthorities())
//            .compact();
//    httpServletResponse.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
  }

  public Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token == null) {
      return null;
    }
    try {
      String user = Jwts.parser()
              .setSigningKey(SECRET)
              .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
              .getBody()
              .getSubject();
      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, newArrayList());
      }
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    return null;
  }
}

package org.licket.spring.web.security.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
}

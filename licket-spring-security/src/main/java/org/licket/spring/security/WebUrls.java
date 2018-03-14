package org.licket.spring.security;

import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.stream;
import static org.licket.core.LicketUrls.CONTEXT_RESOURCES;

/**
 * @author lukaszgrabski
 */
@Component
public class WebUrls {

  private List<String> publicAntMatchers = newArrayList(CONTEXT_RESOURCES + "/**");

  public void addPublicAntMatchers(String... antMatchers) {
    stream(antMatchers).forEach(publicAntMatchers::add);
  }

  public String[] getPublicAntMatchers() {
    return publicAntMatchers.toArray(new String[0]);
  }
}

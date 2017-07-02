package org.licket.core.view.mount.params;

import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Optional.ofNullable;

/**
 * @author lukaszgrabski
 */
public class MountingParams {
  private Map<String, String> mountingParams = newHashMap();

  public void newParam(String paramName, String paramValue) {
    mountingParams.put(paramName, paramValue);
  }

  public Optional<String> getString(String paramName) {
    return ofNullable(mountingParams.get(paramName));
  }
}

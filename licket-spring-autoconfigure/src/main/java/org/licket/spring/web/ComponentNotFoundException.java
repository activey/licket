package org.licket.spring.web;

import static java.lang.String.format;

/**
 * @author activey
 */
public class ComponentNotFoundException extends RuntimeException {

  public ComponentNotFoundException() {
  }

  public ComponentNotFoundException(String message) {
    super(message);
  }

  public ComponentNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ComponentNotFoundException(Throwable cause) {
    super(cause);
  }

  public ComponentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public static ComponentNotFoundException componentNotFound(String compositeId) {
    return new ComponentNotFoundException(format("Component not found: [%s]", compositeId));
  }
}

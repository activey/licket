package org.licket.core.view.hippo.vue.extend;

import static java.lang.String.format;

/**
 * @author activey
 */
public class VueExtendConstructionException extends RuntimeException {

  public VueExtendConstructionException(String messageTemplate, Object... params) {
    super(format(messageTemplate, params));
  }

  public VueExtendConstructionException(String message, Throwable cause) {
    super(message, cause);
  }
}

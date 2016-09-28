package org.licket.core.view.hippo.testing.ngclass;

import static java.lang.String.format;

/**
 * @author activey
 */
public class AngularClassConstructionException extends RuntimeException {

    public AngularClassConstructionException(String messageTemplate, Object... params) {
        super(format(messageTemplate, params));
    }

    public AngularClassConstructionException(String message, Throwable cause) {
        super(message, cause);
    }
}

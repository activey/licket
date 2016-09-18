package org.licket.core;

/**
 * @author activey
 */
public class LicketApplicationLoadingException extends RuntimeException {

    public LicketApplicationLoadingException(String message) {
        super(message);
    }

    public LicketApplicationLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public LicketApplicationLoadingException(Throwable cause) {
        super(cause);
    }

    public static LicketApplicationLoadingException missingConstructorError() {
        return new LicketApplicationLoadingException("Unable to find page clazz with two parameters!");
    }
}

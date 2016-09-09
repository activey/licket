package org.licket.surface;

import nu.xom.ParsingException;

import java.io.IOException;

/**
 * @author activey
 */
public class SurfaceProcessingException extends RuntimeException {

    private SurfaceProcessingException(Throwable cause) {
        super(cause);
    }

    public static SurfaceProcessingException contentParsingException(ParsingException parsingException) {
        return new SurfaceProcessingException(parsingException);
    }

    public static SurfaceProcessingException ioException(IOException ioException) {
        return new SurfaceProcessingException(ioException);
    }
}

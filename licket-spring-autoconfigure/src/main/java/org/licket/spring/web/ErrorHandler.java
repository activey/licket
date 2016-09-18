package org.licket.spring.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author activey
 */
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Throwable.class)
    public void handleException(Throwable t) {
        t.printStackTrace();
    }
}

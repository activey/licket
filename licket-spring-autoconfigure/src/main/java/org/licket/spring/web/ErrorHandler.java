package org.licket.spring.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.google.common.base.Throwables.getRootCause;

/**
 * @author activey
 */
@ControllerAdvice
public class ErrorHandler {

  private final static Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

  @ExceptionHandler(ComponentNotFoundException.class)
  public void handleComponentNotFound(ComponentNotFoundException notFound) {
    LOGGER.error(getRootCause(notFound).getMessage(), notFound);
  }

  @ExceptionHandler(Throwable.class)
  public void handleException(Throwable throwable) {
    LOGGER.error(getRootCause(throwable).getMessage(), throwable);
  }
}

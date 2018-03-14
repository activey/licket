package org.licket.core.optional;

/**
 * @author lukaszgrabski
 */
@FunctionalInterface
public interface ElseConsumer {

  void doElse() throws Throwable;
}

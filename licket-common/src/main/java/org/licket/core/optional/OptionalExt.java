package org.licket.core.optional;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author lukaszgrabski
 */
public class OptionalExt<T> {

  private Consumer<Void> elseConsumer;

  public static <T> OptionalExt<T> forOptional(Optional<T> optional) {
    return new OptionalExt<T>(optional);
  }

  private final Optional<T> optional;

  public OptionalExt(Optional<T> optional) {
    this.optional = optional;
  }

  public OptionalExt<T> ifPresent(Consumer<T> valueConsumer) {
      optional.ifPresent(valueConsumer);
      return this;
  }

  public void orElse(ElseConsumer elseConsumer) throws Throwable {
    if (optional.isPresent()) {
      return;
    }
    elseConsumer.doElse();
  }
}

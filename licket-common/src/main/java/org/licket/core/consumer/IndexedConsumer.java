package org.licket.core.consumer;

import java.util.function.Consumer;

/**
 * @author activey
 */
public abstract class IndexedConsumer<T> implements Consumer<T> {

    private int index;

    protected abstract void accept(T consumable, int index);

    @Override
    public final void accept(T t) {
        accept(t, index++);
    }
}

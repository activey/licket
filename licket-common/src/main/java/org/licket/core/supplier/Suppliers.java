package org.licket.core.supplier;

import java.util.function.Supplier;

public class Suppliers {

    public static <T> Supplier<T> of(T givenValue) {
        return () -> givenValue;
    }
}

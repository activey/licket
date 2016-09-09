package org.licket.core.common;

import java.util.function.Supplier;

public class Suppliers {

    public static <T> Supplier<T> of(T givenValue) {
        return () -> givenValue;
    }
}

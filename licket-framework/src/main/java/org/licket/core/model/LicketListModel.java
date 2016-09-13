package org.licket.core.model;

import java.util.function.Supplier;

/**
 * @author activey
 */
public class LicketListModel<T> extends LicketModel<Iterable<T>> {

    public LicketListModel(Iterable<T> modelObject) {
        super(modelObject);
    }

    public LicketListModel(Supplier<Iterable<T>> modelObjectSupplier) {
        super(modelObjectSupplier);
    }

    public static <T> LicketListModel<T> fromSupplier(Supplier<Iterable<T>> listModelSupplier) {
        return new LicketListModel<T>(listModelSupplier);
    }
}

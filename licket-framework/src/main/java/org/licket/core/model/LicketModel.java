package org.licket.core.model;

import java.util.function.Supplier;

import static org.licket.core.common.Suppliers.of;

/**
 * @author activey
 */
public class LicketModel<T> {

    public static LicketModel<String> ofString(String stringValue) {
        return new LicketModel<String>(stringValue);
    }

    public static <T> LicketModel<T> empty() {
        return new LicketModel<>(() -> null);
    }

    public static <T> LicketModel<T> ofModelObject(T modelObject) {
        return new LicketModel<T>(modelObject);
    }

    private T modelObject;

    private Supplier<T> modelObjectSupplier;

    public LicketModel(T modelObject) {
        this(of(modelObject));
    }

    public LicketModel(Supplier<T> modelObjectSupplier) {
        this.modelObjectSupplier = modelObjectSupplier;
    }

    public T get() {
        if (modelObject == null) {
            modelObject = modelObjectSupplier.get();
        }
        return modelObject;
    }

    public void set(T modelObject) {
        this.modelObject = modelObject;
    }

    public static <T> LicketModel<T> fromParent(LicketModel<T> parentModel) {
        return new LicketModel<T>(parentModel.modelObjectSupplier);
    }
}

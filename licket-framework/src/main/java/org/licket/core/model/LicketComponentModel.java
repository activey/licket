package org.licket.core.model;

import static org.licket.core.supplier.Suppliers.of;
import java.util.function.Supplier;

/**
 * @author activey
 */
public class LicketComponentModel<T> {

    private T modelObject;
    private Supplier<T> modelObjectSupplier;

    public LicketComponentModel(T modelObject) {
        this(of(modelObject));
    }

    public LicketComponentModel(Supplier<T> modelObjectSupplier) {
        this.modelObjectSupplier = modelObjectSupplier;
    }

    public static LicketComponentModel<String> ofString(String stringValue) {
        return new LicketComponentModel<>(stringValue);
    }

    public static <T> LicketComponentModel<T> emptyComponentModel() {
        return new LicketComponentModel<>(() -> null);
    }

    public static <T> LicketComponentModel<T> ofModelObject(T modelObject) {
        return new LicketComponentModel<>(modelObject);
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
}

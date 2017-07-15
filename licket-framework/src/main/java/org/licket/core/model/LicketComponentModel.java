package org.licket.core.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.licket.core.supplier.Suppliers.of;

/**
 * @author activey
 */
public class LicketComponentModel<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicketComponentModel.class);

    private T modelObject;
    private Supplier<T> modelObjectSupplier;
    private LicketComponentModelPatch modelPatch;

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

    public void patch(Consumer<T> contactConsumer) {
        T contact = get();

        ObjectMapper mapper = new ObjectMapper();
        try {
            TreeNode node = mapper.valueToTree(contact);
            T copy = mapper.treeToValue(node, (Class<T>) contact.getClass());
            contactConsumer.accept(copy);

            set(copy);
        } catch (JsonProcessingException e) {
            LOGGER.error("An error occurred while patching model of component.", e);
        }
    }

    public LicketComponentModelPatch<T> getPatch() {
        return modelPatch;
    }

    public void set(T modelObject) {
        // computing model object patch
        if (this.modelObject != null && modelObject != null) {
            this.modelPatch = new LicketComponentModelPatch<>(this.modelObject, modelObject);
        }

        this.modelObject = modelObject;
    }
}

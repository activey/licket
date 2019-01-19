package org.licket.core.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author activey
 */
public class LicketComponentModel<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicketComponentModel.class);

    private T modelObject;
    private LicketComponentModelPatch<T> modelPatch;

    public LicketComponentModel(T modelObject) {
        this(Optional.ofNullable(modelObject));
    }

    public LicketComponentModel(Optional<T> modelObjectOptional) {
        modelObjectOptional.ifPresent(this::set);
    }

    public static LicketComponentModel<String> ofString(String stringValue) {
        return new LicketComponentModel<>(stringValue);
    }

    public static <T> LicketComponentModel<T> emptyComponentModel() {
        return new LicketComponentModel<>(Optional.empty());
    }

    public static <T> LicketComponentModel<T> ofModelObject(T modelObject) {
        return new LicketComponentModel<>(modelObject);
    }

    public Optional<T> get() {
        return Optional.ofNullable(modelObject);
    }

    public void patch(Consumer<T> modelObjectConsumer) {
        get().ifPresent(modelObject -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                TreeNode node = mapper.valueToTree(modelObject);
                T copy = mapper.treeToValue(node, (Class<T>) modelObject.getClass());
                modelObjectConsumer.accept(copy);

                set(copy);
            } catch (JsonProcessingException e) {
                LOGGER.error("An error occurred while patching model of component.", e);
            }
        });
    }

    public LicketComponentModelPatch<T> getPatch() {
        return modelPatch;
    }

    public void set(T modelObject) {
        if (this.modelObject != null) {
            if (modelObject != null) {
              this.modelPatch = new LicketComponentModelPatch<>(this.modelObject, modelObject);
            }
        } else {
          if (modelObject != null) {
            this.modelPatch = new LicketComponentModelPatch<>(modelObject);
          }
        }
        this.modelObject = modelObject;
    }
}

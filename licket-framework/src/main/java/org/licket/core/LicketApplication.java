package org.licket.core;

import static java.util.Optional.ofNullable;
import static org.licket.core.id.CompositeId.fromStringValue;

import java.io.Serializable;
import java.util.Optional;
import org.licket.core.id.CompositeId;
import org.licket.core.view.LicketComponent;
import org.springframework.beans.factory.annotation.Autowired;

public class LicketApplication implements Serializable {

    private String name;

    @Autowired
    private LicketComponent<?> rootComponent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LicketComponent<?> getRootComponent() {
        return rootComponent;
    }

    public Optional<LicketComponent<?>> findComponent(String compositeIdValue) {
        return ofNullable(rootComponent.findChild(fromStringValue(compositeIdValue)));
    }
}

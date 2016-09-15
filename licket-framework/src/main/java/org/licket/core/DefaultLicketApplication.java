package org.licket.core;

import static java.util.Optional.ofNullable;
import static org.licket.core.id.CompositeId.fromStringValue;

import java.io.Serializable;
import java.util.Optional;

import org.licket.core.id.CompositeId;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.LicketComponentContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DefaultLicketApplication implements LicketApplication, Serializable  {

    private final String name;

    @Autowired
    @Qualifier("root")
    private LicketComponentContainer<?> rootContainer;

    public DefaultLicketApplication(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LicketComponentContainer<?> getRootComponentContainer() {
        return rootContainer;
    }

    @Override
    public Optional<LicketComponent<?>> findComponent(CompositeId compositeId) {
        return ofNullable(rootContainer.findChild(compositeId));
    }

    @Override
    public Optional<LicketComponent<?>> findComponent(String compositeIdValue) {
        return findComponent(fromStringValue(compositeIdValue));
    }
}

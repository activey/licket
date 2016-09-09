package org.licket.core;

import static java.util.Optional.ofNullable;

import java.io.Serializable;
import java.util.Optional;
import org.licket.core.id.CompositeId;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.AbstractLicketPage;
import org.springframework.beans.factory.annotation.Autowired;

public class LicketApplication implements Serializable {

    private String name;

    @Autowired
    private AbstractLicketPage<?> mainLicketPage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbstractLicketComponent<?> getRootComponent() {
        return mainLicketPage;
    }

    public Optional<AbstractLicketComponent<?>> findComponent(String compositeIdValue) {
        return ofNullable(mainLicketPage.findChild(new CompositeId(compositeIdValue)));
    }
}

package org.licket.core.view.container;

import org.licket.core.module.application.LicketComponentModelReloader;

/**
 * @author activey
 */
public class TestContainer extends AbstractLicketMultiContainer<Void> {

    private final LicketComponentModelReloader modelReloader;

    public TestContainer(String id, LicketComponentModelReloader modelReloader) {
        super(id, Void.class);
        this.modelReloader = modelReloader;
    }

    @Override
    protected LicketComponentModelReloader getModelReloader() {
        return modelReloader;
    }
}

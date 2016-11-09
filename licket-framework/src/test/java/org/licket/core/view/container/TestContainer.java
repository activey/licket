package org.licket.core.view.container;

import org.licket.core.module.application.LicketComponentModelReloader;

/**
 * @author activey
 */
public class TestContainer extends AbstractLicketMultiContainer<Void> {

    public TestContainer(String id, LicketComponentModelReloader modelReloader) {
        super(id, Void.class, modelReloader);
    }
}

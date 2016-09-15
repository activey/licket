package org.licket.core.view.container;

import org.licket.core.resource.Resource;
import org.licket.core.view.ComponentContainerView;

import java.io.InputStream;

public class ExternalizedComponentContainerView implements ComponentContainerView {

    private Resource componentViewResource;

    public ExternalizedComponentContainerView(Resource componentViewResource) {
        this.componentViewResource = componentViewResource;
    }

    public final InputStream readViewContent() {
        return componentViewResource.getStream();
    }

    public boolean isExternalized() {
        return true;
    }
}

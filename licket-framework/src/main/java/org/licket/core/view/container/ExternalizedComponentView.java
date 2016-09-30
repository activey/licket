package org.licket.core.view.container;

import org.licket.core.resource.Resource;
import org.licket.core.view.ComponentView;

import java.io.InputStream;

public class ExternalizedComponentView implements ComponentView {

    private Resource componentViewResource;

    public ExternalizedComponentView(Resource componentViewResource) {
        this.componentViewResource = componentViewResource;
    }

    public final InputStream readViewContent() {
        return componentViewResource.getStream();
    }

    public boolean isExternalized() {
        return true;
    }
}

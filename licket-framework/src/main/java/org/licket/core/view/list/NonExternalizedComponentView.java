package org.licket.core.view.list;

import org.licket.core.view.ComponentView;

import java.io.InputStream;

/**
 * @author grabslu
 */
public class NonExternalizedComponentView implements ComponentView {

    @Override
    public InputStream readViewContent() {
        return null;
    }

    @Override
    public boolean isExternalized() {
        return false;
    }
}

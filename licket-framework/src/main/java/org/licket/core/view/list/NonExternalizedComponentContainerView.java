package org.licket.core.view.list;

import org.licket.core.view.ComponentContainerView;

import java.io.InputStream;

/**
 * @author grabslu
 */
public class NonExternalizedComponentContainerView implements ComponentContainerView {

    @Override
    public InputStream readViewContent() {
        return null;
    }

    @Override
    public boolean isExternalized() {
        return false;
    }
}

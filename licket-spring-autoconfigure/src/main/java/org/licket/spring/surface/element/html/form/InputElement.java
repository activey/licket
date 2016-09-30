package org.licket.spring.surface.element.html.form;

import org.licket.spring.surface.element.html.DefaultHtmlElement;

/**
 * @author activey
 */
public class InputElement extends DefaultHtmlElement {

    public InputElement(String name) {
        super(name);
    }

    @Override
    protected boolean writeEmpty() {
        return false;
    }

    @Override
    protected void onStart() {
    }
}

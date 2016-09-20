package org.licket.spring.surface.element.html;

import org.licket.surface.element.SurfaceElement;

import static org.licket.spring.surface.element.html.HtmlElementFactory.HTML_NAMESPACE;

/**
 * @author activey
 */
public class ScriptElement extends SurfaceElement {

    public ScriptElement() {
        super("script", HTML_NAMESPACE);
    }

    public void setSrc(String src) {
        setAttribute("src", src);
    }
}

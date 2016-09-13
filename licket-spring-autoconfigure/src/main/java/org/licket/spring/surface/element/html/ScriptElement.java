package org.licket.spring.surface.element.html;

import nu.xom.Attribute;
import org.licket.surface.element.SurfaceElement;

import static org.licket.spring.surface.element.html.HtmlElementFactory.NAMESPACE;

/**
 * @author activey
 */
public class ScriptElement extends SurfaceElement {

    public ScriptElement() {
        super("script", NAMESPACE);
    }

    public void setSrc(String src) {
        addAttribute(new Attribute("src", src));
    }
}

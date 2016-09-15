package org.licket.spring.surface.element.html;

import org.licket.surface.element.SurfaceElement;
import org.licket.xml.dom.Attribute;

import static org.licket.spring.surface.element.html.HtmlElementFactory.NAMESPACE;

/**
 * @author activey
 */
public class ScriptElement extends SurfaceElement {

    public ScriptElement() {
        super("script", NAMESPACE);
    }

    public void setSrc(String src) {
        Attribute attribute = new Attribute("src", getNamespace());
        attribute.setValue(src);
        addAttribute(attribute);
    }
}

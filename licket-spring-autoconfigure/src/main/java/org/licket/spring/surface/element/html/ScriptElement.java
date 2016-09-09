package org.licket.spring.surface.element.html;

import nu.xom.Attribute;
import org.licket.surface.element.BaseElement;

import static org.licket.spring.surface.element.html.HtmlElementFactory.NAMESPACE;

/**
 * @author activey
 */
public class ScriptElement extends BaseElement {

    public ScriptElement() {
        super("script", NAMESPACE);
    }

    public void setSrc(String src) {
        addAttribute(new Attribute("src", src));
    }
}

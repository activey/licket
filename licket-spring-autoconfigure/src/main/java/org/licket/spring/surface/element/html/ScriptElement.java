package org.licket.spring.surface.element.html;

import org.licket.xml.dom.Attribute;
import org.licket.xml.dom.Element;

import static org.licket.spring.surface.element.html.HtmlElementFactory.HTML_NAMESPACE;

/**
 * @author activey
 */
public class ScriptElement extends Element {

    public ScriptElement() {
        super("script", HTML_NAMESPACE);
    }

    public void setSrc(String src) {
        addAttribute(new Attribute("src", getNamespace(), src));
    }

    @Override
    protected boolean writeEmpty() {
        return true;
    }
}

package org.licket.spring.surface.element.html;

import org.licket.surface.element.SurfaceElement;
import org.licket.xml.dom.Element;
import org.licket.xml.dom.Text;

import static org.licket.spring.surface.element.html.HtmlElementFactory.HTML_NAMESPACE;

/**
 * @author activey
 */
public class ScriptElement extends Element {

    public ScriptElement() {
        super("script", HTML_NAMESPACE);
    }

    public void setSrc(String src) {
        setAttribute("src", src);
    }

    @Override
    protected boolean writeEmpty() {
        return true;
    }
}

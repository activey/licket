package org.licket.spring.surface.element.html;

import org.licket.surface.element.SurfaceElement;

import static org.licket.spring.surface.element.html.HtmlElementFactory.HTML_NAMESPACE;

/**
 * @author grabslu
 */
public class LinkElement extends SurfaceElement {

    public LinkElement() {
        super("link", HTML_NAMESPACE);
    }

    public void setRelType(LinkRelType relType) {
        setAttribute("rel", relType.getTypeValue());
    }

    public void setType(String mimetype) {
        setAttribute("type", mimetype);
    }

    public void setHref(String href) {
        setAttribute("href", href);
    }

    public enum LinkRelType {
        STYLESHEET("stylesheet");

        private final String typeValue;

        LinkRelType(String typeValue) {
            this.typeValue = typeValue;
        }

        public String getTypeValue() {
            return typeValue;
        }
    }
}

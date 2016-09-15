package org.licket.surface.attribute;

import org.licket.surface.element.SurfaceElement;
import org.licket.xml.dom.Attribute;

/**
 * @author activey
 */
public class BaseAttribute extends Attribute {

    public BaseAttribute(String localName, String namespace) {
        super(localName, namespace);
    }

    public BaseAttribute(String prefix, String localName, String namespace, Type type) {
        super(prefix, localName, namespace, type);
    }

    public BaseAttribute(String prefix, String localName, String namespace) {
        super(prefix, localName, namespace);
    }

    public final void start(SurfaceElement relatedElement) {
        onStart(relatedElement);
    }

    protected void onStart(SurfaceElement relatedElement) {}
}

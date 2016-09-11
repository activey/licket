package org.licket.surface.attribute;

import nu.xom.Attribute;
import org.licket.surface.element.BaseElement;

/**
 * @author activey
 */
public class BaseAttribute extends Attribute {

    public BaseAttribute(String localName, String namespace, Type type) {
        super(localName, namespace, "", type);
    }

    public final void start(BaseElement relatedElement) {
        onStart(relatedElement);
    }

    protected void onStart(BaseElement relatedElement) {}
}

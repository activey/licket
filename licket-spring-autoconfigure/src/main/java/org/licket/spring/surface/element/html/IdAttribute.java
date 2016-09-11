package org.licket.spring.surface.element.html;

import org.licket.surface.attribute.BaseAttribute;
import org.licket.surface.element.BaseElement;

import static nu.xom.Attribute.Type.ID;

/**
 * @author activey
 */
public class IdAttribute extends BaseAttribute {

    public IdAttribute() {
        super("id", "", ID);
    }

    @Override
    protected void onStart(BaseElement relatedElement) {
        relatedElement.setLocalName(getValue());
    }
}

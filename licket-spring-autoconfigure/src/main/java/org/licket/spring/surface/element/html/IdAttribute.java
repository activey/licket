package org.licket.spring.surface.element.html;

import org.licket.core.LicketApplication;
import org.licket.surface.attribute.BaseAttribute;
import org.licket.surface.element.SurfaceElement;
import org.springframework.beans.factory.annotation.Autowired;

import static nu.xom.Attribute.Type.ID;

/**
 * @author activey
 */
public class IdAttribute extends BaseAttribute {

    @Autowired
    private LicketApplication licketApplication;

    public IdAttribute(String name) {
        super(name, "", ID);
    }

    @Override
    protected void onStart(SurfaceElement relatedElement) {
        relatedElement.setComponentId(getValue());
    }
}

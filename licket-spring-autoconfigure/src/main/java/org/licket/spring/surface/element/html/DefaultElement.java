package org.licket.spring.surface.element.html;

import static org.licket.spring.surface.element.html.HtmlElementFactory.NAMESPACE;

import org.licket.core.LicketApplication;
import org.licket.core.view.LicketComponent;
import org.licket.surface.element.SurfaceElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author activey
 */
public class DefaultElement extends SurfaceElement {

    @Autowired
    private LicketApplication licketApplication;

    public DefaultElement(String name) {
        super(name, NAMESPACE);
    }

    @Override
    protected final void onFinish() {
        if (!isComponentIdSet()) {
            return;
        }
        Optional<LicketComponent<?>> componentOptional =
                licketApplication.findComponent(getComponentCompositeId());
        if (!componentOptional.isPresent()) {
            return;
        }


    }
}

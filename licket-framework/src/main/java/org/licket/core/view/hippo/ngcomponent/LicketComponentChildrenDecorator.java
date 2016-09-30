package org.licket.core.view.hippo.ngcomponent;

import org.licket.core.view.LicketComponent;
import org.licket.framework.hippo.ArrayLiteralBuilder;

/**
 * @author activey
 */
public class LicketComponentChildrenDecorator {

    private LicketComponent<?> licketComponent;

    public static LicketComponentChildrenDecorator forLicketComponentChildren(LicketComponent<?> licketComponent) {
        return new LicketComponentChildrenDecorator(licketComponent);
    }

    private LicketComponentChildrenDecorator(LicketComponent<?> licketComponent) {
        this.licketComponent = licketComponent;
    }

    public ArrayLiteralBuilder decorate(ArrayLiteralBuilder arrayLiteralBuilder) {
        licketComponent.traverseDown(child -> {
            if (!child.getView().isExternalized()) {
                return false;
            }
            arrayLiteralBuilder.element(child.angularName());
            return false;
        });
        return arrayLiteralBuilder;
    }
}
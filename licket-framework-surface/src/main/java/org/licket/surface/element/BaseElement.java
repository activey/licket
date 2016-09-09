package org.licket.surface.element;

import nu.xom.Element;
import nu.xom.ParentNode;

/**
 * @author activey
 */
public class BaseElement extends Element implements ElementProvider {

    public BaseElement(String name, String namespace) {
        super(name, namespace);
    }

    public final void finish() {
        onFinish();
    }

    protected void onFinish() {}

    public final void start() {
        onStart();
    }

    protected void onStart() {}

    protected final BaseElement getParentElement() {
        ParentNode parentNode = getParent();
        return new BaseElement(parentNode.getValue(), parentNode.getBaseURI());
    }

    protected final void addChildElement(BaseElement baseElement) {
        super.appendChild(baseElement);
    }

    @Override
    public BaseElement provideElement() {
        return this;
    }
}

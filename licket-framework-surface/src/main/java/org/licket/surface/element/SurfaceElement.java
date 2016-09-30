package org.licket.surface.element;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.licket.core.id.CompositeId.fromStringValue;
import static org.licket.core.id.CompositeId.fromStringValueWithAdditionalParts;
import static org.licket.surface.element.ElementTraverser.withComponentIdSet;
import java.util.Optional;
import org.licket.core.id.CompositeId;
import org.licket.surface.attribute.BaseAttribute;
import org.licket.xml.dom.Element;
import org.licket.xml.dom.Node;

/**
 * @author activey
 */
public class SurfaceElement extends Element {

    private String componentId;

    public SurfaceElement(String name, String namespace) {
        super(name, namespace);
    }

    public final void finish() {
        onFinish();
    }

    protected void onFinish() {}

    public final BaseAttribute addAttribute(String attributeName, String attributeValue) {
        BaseAttribute newAttribute = new BaseAttribute(attributeName, getNamespace());
        newAttribute.setValue(attributeValue);
        addAttribute(newAttribute);
        return newAttribute;
    }

    public final void start() {
        onStart();
    }

    protected void onStart() {}

    public final void addChildElement(Element surfaceElement) {
        super.appendChildElement(surfaceElement);
    }

    protected Optional<SurfaceElement> traverseUp(ElementTraverser elementTraverser) {
        SurfaceElement parent = getParentElement();
        if (parent == null) {
            return empty();
        }
        if (elementTraverser.elementMatch(parent)) {
            return of(parent);
        }
        return parent.traverseUp(elementTraverser);
    }

    public final boolean isComponentIdSet() {
        return componentId != null;
    }

    public String getComponentId() {
        return componentId;
    }

    public final void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public final CompositeId getComponentCompositeId() {
        if (!isComponentIdSet()) {
            return null;
        }
        Optional<SurfaceElement> parentOptional = traverseUp(withComponentIdSet());
        if (!parentOptional.isPresent()) {
            return fromStringValue(componentId);
        }
        return fromStringValueWithAdditionalParts(parentOptional.get().getComponentCompositeId().getValue(),
            componentId);
    }

    protected SurfaceElement getParentElement() {
        Node parentNode = getParent();
        if (parentNode instanceof SurfaceElement) {
            return (SurfaceElement) parentNode;
        }
        return null;
    }

    @Override
    protected boolean writeEmpty() {
        return true;
    }
}

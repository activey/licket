package org.licket.surface.element;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.licket.core.id.CompositeId.fromStringValue;
import static org.licket.core.id.CompositeId.fromStringValueWithAdditionalParts;
import static org.licket.surface.element.ElementTraverser.withComponentIdSet;
import java.util.Optional;

import nu.xom.ParentNode;
import org.licket.core.id.CompositeId;
import nu.xom.Element;

/**
 * @author activey
 */
public class BaseElement extends Element {

    private String componentId;

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
        if (parentNode instanceof BaseElement) {
            return (BaseElement) parentNode;
        }
        return null;
    }

    protected final void addChildElement(BaseElement baseElement) {
        super.appendChild(baseElement);
    }

    public final void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    protected Optional<BaseElement> traverseUp(ElementTraverser elementTraverser) {
        BaseElement parent = getParentElement();
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

    public final CompositeId getComponentCompositeId() {
        if (!isComponentIdSet()) {
            return null;
        }
        Optional<BaseElement> parentOptional = traverseUp(withComponentIdSet());
        if (!parentOptional.isPresent()) {
            return fromStringValue(componentId);
        }
        return fromStringValueWithAdditionalParts(parentOptional.get().getComponentCompositeId().getValue(),
                componentId);
    }

    public final void replaceWith(BaseElement replacement) {
        BaseElement parentElement = getParentElement();
        if (parentElement == null) {
            return;
        }
        parentElement.replaceChild(this, replacement);
    }
}

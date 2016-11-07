package org.licket.core.view.slot;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.LicketComponentView;
import org.licket.core.view.LicketComponent;
import org.licket.surface.element.SurfaceElement;
import org.licket.xml.dom.Text;

import java.util.Map;
import java.util.function.Predicate;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author activey
 */
public class AbstractSlottedLicketComponent<T> extends AbstractLicketComponent<T> {

    private LicketComponent<?> mainSlotComponent;
    private Map<String, LicketComponent<?>> slotsComponents = newHashMap();

    public AbstractSlottedLicketComponent(String id, Class<T> modelClass) {
        super(id, modelClass);
    }

    public AbstractSlottedLicketComponent(String id, Class<T> modelClass, LicketComponentModel<T> componentModel) {
        super(id, modelClass, componentModel);
    }

    public AbstractSlottedLicketComponent(String id, Class<T> modelClass, LicketComponentModel<T> componentModel, LicketComponentView view) {
        super(id, modelClass, componentModel, view);
    }

    public final void slot(LicketComponent<?> component) {
        this.mainSlotComponent = component;
    }

    public final void slot(String slotName, LicketComponent<?> component) {
        slotsComponents.put(slotName, component);
    }

    @Override
    public final void traverseDown(Predicate<LicketComponent<?>> componentConsumer) {
        traverseMainSlotComponent(componentConsumer);
        traverseSlotsComponents(componentConsumer);
    }

    private void traverseMainSlotComponent(Predicate<LicketComponent<?>> componentConsumer) {
        if (mainSlotComponent == null) {
            return;
        }
        if (componentConsumer.test(mainSlotComponent)) {
            mainSlotComponent.traverseDown(componentConsumer);
        }
    }

    private void traverseSlotsComponents(Predicate<LicketComponent<?>> componentConsumer) {
        slotsComponents.values().forEach(slotComponent -> {
            if (componentConsumer.test(slotComponent)) {
                slotComponent.traverseDown(componentConsumer);
            }
        });
    }

    @Override
    protected final void onElementReplaced(SurfaceElement element) {
        doRenderMainSlot(element);
        doRenderNamedSlots(element);
    }

    private void doRenderMainSlot(SurfaceElement element) {
        if (mainSlotComponent == null) {
            return;
        }
        SurfaceElement componentElement = new SurfaceElement(mainSlotComponent.getId(), element.getNamespace());
        componentElement.addChildElement(new Text("..."));
        element.addChildElement(componentElement);
    }

    private void doRenderNamedSlots(SurfaceElement element) {
        if (slotsComponents.size() == 0) {
            return;
        }
        slotsComponents.forEach((slotName, component) -> {
            SurfaceElement componentElement = new SurfaceElement(mainSlotComponent.getId(), element.getNamespace());
            componentElement.addAttribute("slot", slotName);
        });
    }
}

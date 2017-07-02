package org.licket.core;

import org.licket.core.id.CompositeId;
import org.licket.core.view.ComponentChildLocator;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.core.view.hippo.vue.VuePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static org.licket.core.id.CompositeId.fromStringValue;

public class DefaultLicketApplication implements LicketApplication, Serializable  {

    private final String name;

    @Autowired
    @Qualifier("root")
    private LicketComponentContainer<?> rootContainer;

    @Autowired
    private List<LicketComponent<?>> allDeclaredComponents = newArrayList();

    @Autowired
    private List<VuePlugin> plugins = newArrayList();

    public DefaultLicketApplication(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LicketComponentContainer<?> rootComponentContainer() {
        return rootContainer;
    }

    @Override
    public Optional<LicketComponent<?>> findComponent(CompositeId compositeId) {
        checkNotNull(rootContainer, "Can not find component definition annotated with @LicketRootContainer");
        Optional<LicketComponent<?>> licketComponentOptional = findComponent(rootContainer, compositeId);
        if (licketComponentOptional.isPresent()) {
            return licketComponentOptional;
        }

        // looking in declared components, @TODO include only those with @LicketMountPoint?
        for (LicketComponent<?> rootElement : allDeclaredComponents) {
            if (rootElement.getCompositeId().equals(compositeId)) {
                return Optional.of(rootElement);
            }
            licketComponentOptional = findComponent(rootElement, compositeId);
            if (licketComponentOptional.isPresent()) {
                return licketComponentOptional;
            }
        }
        return Optional.empty();
    }

    private Optional<LicketComponent<?>> findComponent(LicketComponent<?> rootElement, CompositeId compositeId) {
        if (compositeId.current().equals(rootElement.getId())) {
            compositeId.forward();
            ComponentChildLocator locator = new ComponentChildLocator(rootElement);
            return locator.findByCompositeId(compositeId);
        }
        return Optional.empty();
    }

    @Override
    public Optional<LicketComponent<?>> findComponent(String compositeIdValue) {
        return findComponent(fromStringValue(compositeIdValue));
    }

    @Override
    public void traverseDown(Predicate<LicketComponent<?>> componentVisitor) {
        allDeclaredComponents.forEach(mountedContainer -> {
            if (componentVisitor.test(mountedContainer)) {
                mountedContainer.traverseDown(componentVisitor);
            }
        });
    }

    @Override
    public final Iterable<VuePlugin> plugins() {
        return plugins;
    }
}

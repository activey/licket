package org.licket.core;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Optional.ofNullable;
import static org.licket.core.id.CompositeId.fromStringValue;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.licket.core.id.CompositeId;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.core.view.hippo.testing.ngmodule.AngularModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;

public class DefaultLicketApplication implements LicketApplication, Serializable  {

    private final String name;

    @Autowired
    @Qualifier("root")
    private LicketComponentContainer<?> rootContainer;

    @Autowired
    private List<AngularModule> modules = newArrayList();

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
        return ofNullable(rootContainer.findChild(compositeId));
    }

    @Override
    public Optional<LicketComponent<?>> findComponent(String compositeIdValue) {
        return findComponent(fromStringValue(compositeIdValue));
    }

    @Override
    public void traverseDown(Predicate<LicketComponent<?>> componentVisitor) {
        componentVisitor.test(rootContainer);
        rootContainer.traverseDown(componentVisitor);
    }

    @Override
    public void traverseDownContainers(Predicate<LicketComponentContainer<?>> containerVisitor) {
        containerVisitor.test(rootContainer);
        rootContainer.traverseDownContainers(containerVisitor);
    }

    @Override
    public final Iterable<AngularModule> modules() {
        return modules;
    }
}

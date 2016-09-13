package org.licket.spring.surface.element.html;

import org.licket.surface.element.SurfaceElement;
import org.licket.surface.element.ElementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * @author activey
 */
public class SpringAutowiredElementProvider implements ElementProvider {

    public static ElementProvider provideElement(String localName, NodeSupplier<SurfaceElement> elementSupplier) {
        return new SpringAutowiredElementProvider(localName, elementSupplier);
    }

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private String localName;
    private NodeSupplier<SurfaceElement> nodeSupplier;

    private SpringAutowiredElementProvider(String localName, NodeSupplier<SurfaceElement> nodeSupplier) {
        this.localName = localName;
        this.nodeSupplier = nodeSupplier;
    }

    @Override
    public final String getLocalName() {
        return localName;
    }

    @Override
    public final SurfaceElement provideElement() {
        SurfaceElement element = nodeSupplier.get(localName);
        beanFactory.autowireBean(element);
        return element;
    }
}

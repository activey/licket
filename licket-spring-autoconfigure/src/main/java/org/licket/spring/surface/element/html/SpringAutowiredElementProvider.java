package org.licket.spring.surface.element.html;

import org.licket.surface.element.BaseElement;
import org.licket.surface.element.ElementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * @author activey
 */
public class SpringAutowiredElementProvider implements ElementProvider {

    public static ElementProvider provideElement(String localName, NodeSupplier<BaseElement> elementSupplier) {
        return new SpringAutowiredElementProvider(localName, elementSupplier);
    }

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private String localName;
    private NodeSupplier<BaseElement> nodeSupplier;

    private SpringAutowiredElementProvider(String localName, NodeSupplier<BaseElement> nodeSupplier) {
        this.localName = localName;
        this.nodeSupplier = nodeSupplier;
    }

    @Override
    public final String getLocalName() {
        return localName;
    }

    @Override
    public final BaseElement provideElement() {
        BaseElement element = nodeSupplier.get(localName);
        beanFactory.autowireBean(element);
        return element;
    }
}

package org.licket.spring.surface.element.html;

import org.licket.surface.element.BaseElement;
import org.licket.surface.element.ElementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.util.function.Supplier;

/**
 * @author activey
 */
public class SpringAutowiredElementProvider implements ElementProvider {

    public static ElementProvider provideElement(String localName, Supplier<BaseElement> elementSupplier) {
        return new SpringAutowiredElementProvider(localName, elementSupplier);
    }

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private String localName;
    private Supplier<BaseElement> elementSupplier;

    private SpringAutowiredElementProvider(String localName, Supplier<BaseElement> elementSupplier) {
        this.localName = localName;
        this.elementSupplier = elementSupplier;
    }

    @Override
    public final String getLocalName() {
        return localName;
    }

    @Override
    public final BaseElement provideElement() {
        BaseElement element = elementSupplier.get();
        beanFactory.autowireBean(element);
        return element;
    }
}

package org.licket.spring.surface.element.html;

import java.util.function.Supplier;

import org.licket.surface.attribute.AttributeProvider;
import org.licket.surface.attribute.BaseAttribute;
import org.licket.surface.element.BaseElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * @author activey
 */
public class SpringAutowiredAttributeProvider implements AttributeProvider {

    public static AttributeProvider provideAttribute(String localName, Supplier<BaseAttribute> attributeSupplier) {
        return new SpringAutowiredAttributeProvider(localName, attributeSupplier);
    }

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private String localName;
    private Supplier<BaseAttribute> attributeSupplier;

    private SpringAutowiredAttributeProvider(String localName, Supplier<BaseAttribute> attributeSupplier) {
        this.localName = localName;
        this.attributeSupplier = attributeSupplier;
    }

    @Override
    public final String getLocalName() {
        return localName;
    }

    @Override
    public final BaseAttribute provideAttribute() {
        BaseAttribute element = attributeSupplier.get();
        beanFactory.autowireBean(element);
        return element;
    }
}

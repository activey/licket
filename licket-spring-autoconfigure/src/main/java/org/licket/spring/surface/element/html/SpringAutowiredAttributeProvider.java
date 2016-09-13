package org.licket.spring.surface.element.html;

import org.licket.surface.attribute.AttributeProvider;
import org.licket.surface.attribute.BaseAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * @author activey
 */
public class SpringAutowiredAttributeProvider implements AttributeProvider {

    public static AttributeProvider provideAttribute(String localName, NodeSupplier<BaseAttribute> attributeSupplier) {
        return new SpringAutowiredAttributeProvider(localName, attributeSupplier);
    }

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private String localName;
    private NodeSupplier<BaseAttribute> attributeSupplier;

    private SpringAutowiredAttributeProvider(String localName, NodeSupplier<BaseAttribute> attributeSupplier) {
        this.localName = localName;
        this.attributeSupplier = attributeSupplier;
    }

    @Override
    public final String getLocalName() {
        return localName;
    }

    @Override
    public final BaseAttribute provideAttribute() {
        BaseAttribute element = attributeSupplier.get(localName);
        beanFactory.autowireBean(element);
        return element;
    }
}

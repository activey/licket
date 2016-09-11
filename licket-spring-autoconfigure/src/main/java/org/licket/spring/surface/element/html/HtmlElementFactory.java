package org.licket.spring.surface.element.html;

import org.licket.surface.attribute.AttributeProvider;
import org.licket.surface.element.ElementProvider;
import org.licket.surface.tag.AbstractElementFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author activey
 */
public class HtmlElementFactory extends AbstractElementFactory {

    public static final String NAMESPACE = "http://www.w3.org/1999/xhtml";

    @Autowired(required = false)
    private Collection<ElementProvider> factoryElementsProviders = newArrayList();

    @Autowired(required = false)
    private Collection<AttributeProvider> factoryAttributesProviders = newArrayList();

    public HtmlElementFactory() {
        super(NAMESPACE);
    }

    @PostConstruct
    private void fillFactory() {
        factoryElementsProviders.forEach(super::element);
        factoryAttributesProviders.forEach(super::attribute);
    }
}

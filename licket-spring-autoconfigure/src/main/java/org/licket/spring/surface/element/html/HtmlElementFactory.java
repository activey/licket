package org.licket.spring.surface.element.html;

import org.licket.surface.element.ElementProvider;
import org.licket.surface.tag.AbstractElementFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * @author activey
 */
public class HtmlElementFactory extends AbstractElementFactory {

    public static final String NAMESPACE = "http://www.w3.org/1999/xhtml";

    @Autowired
    private Collection<ElementProvider> factoryElementsProviders;

    public HtmlElementFactory() {
        super(NAMESPACE);
    }

    @PostConstruct
    private void fillFactory() {
        factoryElementsProviders.forEach(elementProvider -> element(elementProvider.provideElement()));
    }
}

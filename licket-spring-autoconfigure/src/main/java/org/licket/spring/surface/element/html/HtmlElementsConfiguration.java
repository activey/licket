package org.licket.spring.surface.element.html;

import org.licket.surface.element.ElementProvider;
import org.licket.surface.tag.ElementFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author activey
 */
@Configuration
public class HtmlElementsConfiguration {

    @Bean
    @RequestScope
    public ElementFactory elementFactory() {
        return new HtmlElementFactory();
    }

    @Bean
    @RequestScope
    public ElementProvider headElement() {
        return new HeadElement();
    }
}

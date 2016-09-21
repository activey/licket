package org.licket.core.view.angular;


import org.junit.Test;
import org.licket.core.id.CompositeId;
import org.licket.core.view.container.AbstractLicketContainer;

import static org.licket.core.id.CompositeId.fromStringValue;
import static org.licket.core.view.angular.ClassConstructorBuilder.constructorBuilder;
import static org.licket.core.view.angular.ComponentBuilder.component;
import static org.licket.core.view.angular.ComponentClassBuilder.classBuilder;

/**
 * @author activey
 */
public class ComponentBuilderTest {


    private AbstractLicketContainer container;

    @Test
    public void test1() {

        String output = component(fromStringValue("test"))
                .selector("test")
                .templateUrl("")
                .clazz(classBuilder()
                        .constructor(constructorBuilder(container))).build().toSource(4);
        System.out.println(output);
    }
}
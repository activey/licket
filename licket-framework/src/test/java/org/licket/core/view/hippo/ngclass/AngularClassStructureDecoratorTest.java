package org.licket.core.view.hippo.ngclass;

import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import org.junit.Before;
import org.junit.Test;
import org.licket.framework.hippo.AssignmentBuilder;

/**
 * @author activey
 */
public class AngularClassStructureDecoratorTest {

    private AngularClassStructureDecorator structureReader;

    @Before
    public void before() {}

    @Test
    public void shouldReadDefinedAngularClassFunctions() {
        // given
        AssignmentBuilder assignment = assignment();

//        AngularClass angularClass = new LicketRemoteCommunication();

        // when
//        fromAngularClass(angularClass).decorate(assignment);

        System.out.println(assignment.build().toSource());
    }
}

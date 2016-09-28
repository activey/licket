package org.licket.core.view.hippo.testing.ngmodule;

import org.licket.framework.hippo.AssignmentBuilder;
import org.mozilla.javascript.ast.Assignment;

/**
 * @author activey
 */
public class AngularModuleStructureDecorator {

    private AngularModule angularModule;

    public static AngularModuleStructureDecorator fromAngularModule(AngularModule angularModule) {
        return new AngularModuleStructureDecorator(angularModule);
    }

    private AngularModuleStructureDecorator(AngularModule angularModule) {
        this.angularModule = angularModule;
    }

    public final void decorate(AssignmentBuilder assignment) {
        Assignment previousAssignment = assignment.build();

        System.out.println("aaa");
    }

}

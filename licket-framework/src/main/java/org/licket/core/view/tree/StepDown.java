package org.licket.core.view.tree;

import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.licket.framework.hippo.ArrayElementGetBuilder;

import static org.licket.framework.hippo.ArrayElementGetBuilder.arrayElementGet;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class StepDown extends AbstractStep {

    public StepDown(String childId) {
        super(childId);
    }

    @Override
    protected ArrayElementGetBuilder decorate(AbstractAstNodeBuilder<?> step, String componentId) {
        return arrayElementGet().target(property(step, name("$refs"))).element(componentId);
    }
}

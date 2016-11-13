package org.licket.core.view.tree;

import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

import static org.licket.framework.hippo.NameBuilder.name;

/**
 * @author activey
 */
public class StepUp extends AbstractStep {

    public StepUp(String parentId) {
        super(parentId);
    }

    @Override
    protected PropertyNameBuilder decorate(AbstractAstNodeBuilder<?> previousStep, String componentId) {
        return PropertyNameBuilder.property(previousStep, name("$parent"));
    }
}

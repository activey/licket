package org.licket.core.view.tree;

import org.licket.core.view.LicketComponent;
import org.licket.framework.hippo.AbstractAstNodeBuilder;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newLinkedList;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class LicketComponentTreeWalkSequence {

    private final LicketComponent<?> source;
    private final LicketComponent<?> target;

    private AbstractAstNodeBuilder<?> rootProperty = property(thisLiteral(), name("$parent"));
    private AbstractAstNodeBuilder<?> childProperty = thisLiteral();

    private LicketComponentTreeWalkSequence(LicketComponent<?> source, LicketComponent<?> target) {
        this.source = checkNotNull(source, "Source can not be null.");
        this.target = checkNotNull(target, "Target can not be null.");
    }

    public static Builder source(LicketComponent<?> source) {
        return new Builder(source);
    }

    public final AbstractAstNodeBuilder<?> generateTreeWalkSequence() {
        if (source.equals(target)) {
            return thisLiteral();
        }
        List<AbstractStep> steps = newLinkedList();
        Optional<LicketComponent<?>> commonParent = source.traverseUp(sourceParent -> {
            steps.add(new StepUp(sourceParent.getId()));

            List<AbstractStep> stepsDown = newLinkedList();
            stepsDown.add(0, new StepDown(target.getId()));
            if (target.traverseUp(targetParent -> {
                if (sourceParent.getCompositeId().equals(targetParent.getCompositeId())
                        || source.getCompositeId().equals(targetParent.getCompositeId())) {
                    steps.addAll(stepsDown);
                    return true;
                }
                stepsDown.add(0, new StepDown(targetParent.getId()));
                return false;
            }).isPresent()) {
                return true;
            }
            return false;
        });
        if (commonParent.isPresent()) {
            steps.stream().skip(1).forEach(step -> rootProperty = step.decorate(rootProperty));
            return rootProperty;
        }
        // when there is no common parent
        List<AbstractStep> childSteps = newLinkedList();
        List<AbstractStep> downSteps = newLinkedList();
        source.traverseDown(sourceChild -> {
            downSteps.add(new StepDown(sourceChild.getId()));
            if (target.getCompositeId().equals(sourceChild.getCompositeId())) {
                childSteps.addAll(downSteps);
                return false;
            }
            return true;
        });
        childSteps.forEach(step -> childProperty = step.decorate(childProperty));
        return childProperty;
    }

    public static class Builder {

        private LicketComponent<?> source;
        private LicketComponent<?> target;

        private Builder(LicketComponent<?> source) {
            this.source = source;
        }

        public final Builder target(LicketComponent<?> target) {
            this.target = target;
            return this;
        }

        public final AbstractAstNodeBuilder<?> traverseSequence() {
            return new LicketComponentTreeWalkSequence(source, target).generateTreeWalkSequence();
        }
    }
}

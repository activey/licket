package org.licket.core.view.tree;

import org.junit.Before;
import org.junit.Test;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.container.TestContainer;
import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.mockito.Mock;

import static org.licket.core.view.tree.LicketComponentTreeWalkSequence.source;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author activey
 */
public class LicketComponentTreeWalkSequenceTest {

    private TestContainer levelA1;
    private TestContainer levelA2;
    private TestContainer levelA3;
    private TestContainer levelA4;
    private TestContainer levelA5;

    private TestContainer levelB2;
    private TestContainer levelB3;
    private TestContainer levelB4;
    private TestContainer levelB5;
    private TestContainer levelB6;

    @Mock
    private LicketComponentModelReloader modelReloader;

    @Before
    public void before() {
        initMocks(this);

        givenComponentTreeStructure();
    }

    @Test
    public void shouldGenerateProperSequenceWhenTargetIsChildOfSourceAtAnyLevel() {
        // when
        AbstractAstNodeBuilder<?> sequence = source(levelA5).target(levelB6).traverseSequence();

        // then
        System.out.println(sequence.build().toSource());
    }

    @Test
    public void shouldGenerateProperSequenceWhenTargetIsParentOfSourceAtAnyLevel() {

    }

    @Test
    public void shouldGenerateProperSequenceWhenTargetIsChildOfDifferentParent() {

    }

    private void givenComponentTreeStructure() {
        levelA1 = new TestContainer("levelA1", modelReloader);
        levelA2 = new TestContainer("levelA2", modelReloader);
        levelA3 = new TestContainer("levelA3", modelReloader);
        levelA4 = new TestContainer("levelA4", modelReloader);
        levelA5 = new TestContainer("levelA5", modelReloader);

        levelB2 = new TestContainer("levelB2", modelReloader);
        levelB3 = new TestContainer("levelB3", modelReloader);
        levelB4 = new TestContainer("levelB4", modelReloader);
        levelB5 = new TestContainer("levelB5", modelReloader);
        levelB6 = new TestContainer("levelB6", modelReloader);

        levelA1.add(levelA2);
        levelA2.add(levelA3);
        levelA3.add(levelA4);
        levelA4.add(levelA5);

        levelA1.add(levelB2);
        levelB2.add(levelB3);
        levelB3.add(levelB4);
        levelB4.add(levelB5);
        levelB5.add(levelB6);
    }
}
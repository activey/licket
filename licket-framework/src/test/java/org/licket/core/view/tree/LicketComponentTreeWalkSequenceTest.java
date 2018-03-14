package org.licket.core.view.tree;

import org.junit.Before;
import org.junit.Test;
import org.licket.core.view.container.TestContainer;
import org.licket.framework.hippo.AbstractAstNodeBuilder;

import static org.fest.assertions.Assertions.assertThat;
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

  private TestContainer levelC2;
  private TestContainer levelC3;
  private TestContainer levelC4;
  private TestContainer levelC5;
  private TestContainer levelC6;

  @Before
  public void before() {
    initMocks(this);

    givenComponentTreeStructure();
  }

  @Test
  public void shouldGenerateProperSequenceWhenTargetIsChildOfSourceAtAnyLevel() {
    // when
    AbstractAstNodeBuilder<?> sequence = source(levelA2).target(levelA5).traverseSequence();

    // then
    assertThat(sequence.build().toSource())
            .isEqualTo("this.$parent.$refs[\"levelA3\"].$refs[\"levelA4\"].$refs[\"levelA5\"]");

    // when
    sequence = source(levelA5).target(levelB6).traverseSequence();

    // then
    assertThat(sequence.build().toSource()).isEqualTo(
            "this.$parent.$parent.$parent.$parent.$refs[\"levelB2\"].$refs[\"levelB3\"].$refs[\"levelB4\"].$refs[\"levelB5\"].$refs[\"levelB6\"]");

    // when
    sequence = source(levelA1).target(levelA5).traverseSequence();

    // then
    assertThat(sequence.build().toSource()).isEqualTo("this.$refs[\"levelA2\"].$refs[\"levelA3\"].$refs[\"levelA4\"].$refs[\"levelA5\"]");

    // when
    sequence = source(levelA2).target(levelB2).traverseSequence();

    // then
    assertThat(sequence.build().toSource()).isEqualTo("this.$parent.$refs[\"levelB2\"]");
  }

  @Test
  public void shouldGenerateProperSequenceWhenTargetIsDirectChildOfSource() {
    // when
    AbstractAstNodeBuilder<?> sequence = source(levelA1).target(levelB2).traverseSequence();

    // then
    assertThat(sequence.build().toSource()).isEqualTo("this.$refs[\"levelB2\"]");

    // when
    sequence = source(levelA1).target(levelC2).traverseSequence();

    // then
    assertThat(sequence.build().toSource()).isEqualTo("this.$refs[\"levelC2\"]");

  }

  private void givenComponentTreeStructure() {
    levelA1 = new TestContainer("levelA1");
    levelA2 = new TestContainer("levelA2");
    levelA3 = new TestContainer("levelA3");
    levelA4 = new TestContainer("levelA4");
    levelA5 = new TestContainer("levelA5");

    levelB2 = new TestContainer("levelB2");
    levelB3 = new TestContainer("levelB3");
    levelB4 = new TestContainer("levelB4");
    levelB5 = new TestContainer("levelB5");
    levelB6 = new TestContainer("levelB6");

    levelC2 = new TestContainer("levelC2");
    levelC3 = new TestContainer("levelC3");
    levelC4 = new TestContainer("levelC4");
    levelC5 = new TestContainer("levelC5");
    levelC6 = new TestContainer("levelC6");

    levelA1.add(levelA2);
    levelA2.add(levelA3);
    levelA3.add(levelA4);
    levelA4.add(levelA5);

    levelA1.add(levelB2);
    levelB2.add(levelB3);
    levelB3.add(levelB4);
    levelB4.add(levelB5);
    levelB5.add(levelB6);

    levelA1.add(levelC2);
    levelC2.add(levelC3);
    levelC3.add(levelC4);
    levelC4.add(levelC5);
    levelC5.add(levelC6);
  }
}

package org.licket.core.view;

import org.junit.Before;
import org.junit.Test;
import org.licket.core.id.CompositeId;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.core.view.container.TestContainer;

import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;
import static org.licket.core.id.CompositeId.fromStringValue;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author activey
 */
public class ComponentChildLocatorTest {

    private LicketComponentContainer<?> root;

    @Before
    public void before() {
        initMocks(this);

        givenComponentStructure();
    }

    private void givenComponentStructure() {
        root = new TestContainer("1");
        LicketComponentContainer<?> level2 = new TestContainer("2");
        root.add(level2);

        LicketComponentContainer<?> level3 = new TestContainer("3");
        level2.add(level3);

        LicketComponentContainer<?> level4 = new TestContainer("4");
        level3.add(level4);

        LicketComponentContainer<?> level5 = new TestContainer("5");
        level4.add(level5);
    }

    @Test
    public void shouldFindChildAtNthLevelWhenCompositeIdMatchesComponentTree() {
        // given
        ComponentChildLocator locator = childLocator();
        CompositeId compositeId = fromStringValue("2:3:4:5");

        // when
        Optional<? extends LicketComponent<?>> rootComponent = locator.findByCompositeId(compositeId);

        // then
        assertThat(rootComponent.isPresent()).isTrue();
    }

    @Test
    public void shouldFindChildAtNthLevelWhenCompositeIdIsShorterThanComponentTreeButMatches() {
        // given
        ComponentChildLocator locator = childLocator();
        CompositeId compositeId = fromStringValue("2:3");

        // when
        Optional<? extends LicketComponent<?>> rootComponent = locator.findByCompositeId(compositeId);

        // then
        assertThat(rootComponent.isPresent()).isTrue();
    }

    @Test
    public void shouldNotFindChildAtNthLevelWhenCompositeIdIsLongerThanComponentTree() {
        // given
        ComponentChildLocator locator = childLocator();
        CompositeId compositeId = fromStringValue("2:3:4:5:6:7:8");

        // when
        Optional<? extends LicketComponent<?>> rootComponent = locator.findByCompositeId(compositeId);

        // then
        assertThat(rootComponent.isPresent()).isFalse();
    }

    @Test
    public void shouldNotFindChildAtNthLevelWhenCompositeIdDoesNotMatchAtAll() {
        // given
        ComponentChildLocator locator = childLocator();
        CompositeId compositeId = fromStringValue("6:3:1:3");

        // when
        Optional<? extends LicketComponent<?>> rootComponent = locator.findByCompositeId(compositeId);

        // then
        assertThat(rootComponent.isPresent()).isFalse();
    }

    private ComponentChildLocator childLocator() {

        return new ComponentChildLocator(root);
    }
}
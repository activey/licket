package org.licket.core.view.container;

import org.junit.Before;
import org.junit.Test;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.LicketComponent;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author activey
 */
public class AbstractLicketContainerTest {

    @Mock
    private LicketComponentModelReloader modelReloader;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldFindChildVeryDeep() {
//        // given
//        TestContainer container = new TestContainer("test", Void.class, modelReloader);
//        container.add(givenComponent(level1, "level1"));
//        level1.add(givenComponent(level2, "level2"));
//        level2.slot(givenComponent(level3, "level3"));
//        level3.add(givenComponent(level4, "level4"));
//        level4.add(givenComponent(level5, "level5"));
//
//        // when
//        LicketComponent<?> child = container.findChild(fromStringValue("level1:level2:level3:level4:level5"));
//
//        // then
//        assertThat(child).isNotNull();
    }

    private LicketComponent<?> givenComponent(LicketComponent<?> component, String id) {
        given(component.getId()).willReturn(id);
        return component;
    }
}
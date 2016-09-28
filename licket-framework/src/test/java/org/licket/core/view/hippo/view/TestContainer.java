package org.licket.core.view.hippo.view;

import org.licket.core.view.container.AbstractLicketContainer;

import static org.licket.core.view.ComponentContainerView.fromComponentContainerClass;

/**
 * @author activey
 */
public class TestContainer extends AbstractLicketContainer<TestType> {

    public TestContainer(String id) {
        super(id, TestType.class, fromComponentContainerClass(TestContainer.class));
    }
}

package org.licket.core.view.mount;

import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.link.AbstractLicketLink;
import org.licket.core.view.mount.params.MountingParamsAggregator;

/**
 * @author lukaszgrabski
 */
public class MountedComponentLink<T> extends AbstractLicketLink {

  private final Class<? extends LicketComponent<T>> componentClass;

  public MountedComponentLink(String id, Class<? extends LicketComponent<T>> componentClass) {
    super(id);
    this.componentClass = componentClass;
  }

  @Override
  protected final void onClick(ComponentFunctionCallback componentFunctionCallback) {
    // TODO retrieve component instance knowing it's componentClass
    componentFunctionCallback.navigateToMounted(componentClass, paramsAggregator -> aggregateParams(paramsAggregator));
  }

  protected void aggregateParams(MountingParamsAggregator paramsAggregator) {
    // override this to provide mounting parameters
  }
}

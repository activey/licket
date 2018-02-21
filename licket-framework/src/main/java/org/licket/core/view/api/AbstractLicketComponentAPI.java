package org.licket.core.view.api;

import org.licket.core.view.LicketComponent;
import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.hippo.ComponentModelProperty;
import org.licket.core.view.mount.MountedComponentNavigation;
import org.licket.framework.hippo.FunctionCallBuilder;

import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author lukaszgrabski
 */
public abstract class AbstractLicketComponentAPI {

  private MountedComponentNavigation mountedComponentNavigation = new MountedComponentNavigation();
  private LicketComponent<?> licketComponent;
  private ComponentFunctionCallback functionCallback;

  public AbstractLicketComponentAPI(LicketComponent<?> licketComponent, ComponentFunctionCallback functionCallback) {
    this.licketComponent = licketComponent;
    this.functionCallback = functionCallback;
  }

  protected LicketComponent<?> component() {
    return licketComponent;
  }

  protected ComponentFunctionCallback functionCallback() {
    return functionCallback;
  }

  public void navigateTo(ComponentModelProperty componentMountedPath) {
    functionCallback.call(mountedComponentNavigation.navigateToPath(componentMountedPath, property("this", "$router")));
  }
}

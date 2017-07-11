package org.licket.core.view.api;

import org.licket.core.view.LicketComponent;
import org.licket.core.view.ComponentFunctionCallback;

/**
 * @author lukaszgrabski
 */
public abstract class AbstractLicketComponentAPI {

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
}

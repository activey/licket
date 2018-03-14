package org.licket.core.view.api;

import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.LicketComponent;

/**
 * @author lukaszgrabski
 */
public class DefaultLicketComponentAPI extends AbstractLicketComponentAPI {

  public DefaultLicketComponentAPI(LicketComponent<?> licketComponent, ComponentFunctionCallback functionCallback) {
    super(licketComponent, functionCallback);
  }
}

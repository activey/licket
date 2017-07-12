package org.licket.core.view.api;

import org.licket.core.view.LicketComponent;
import org.licket.core.view.ComponentFunctionCallback;

/**
 * @author lukaszgrabski
 */
public class DefaultLicketComponentAPI extends AbstractLicketComponentAPI {

  public DefaultLicketComponentAPI(LicketComponent<?> licketComponent, ComponentFunctionCallback functionCallback) {
    super(licketComponent, functionCallback);
  }
}

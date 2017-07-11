package org.licket.core.model;

import org.licket.core.view.LicketComponent;

/**
 * @author lukaszgrabski
 */
public class LicketComponentReloadingModel {

  private final LicketComponent<?> licketComponent;
  private final boolean patch;

  public LicketComponentReloadingModel(LicketComponent<?> licketComponent, boolean patch) {
    this.licketComponent = licketComponent;
    this.patch = patch;
  }

  public LicketComponent<?> getLicketComponent() {
    return licketComponent;
  }

  public boolean isPatch() {
    return patch;
  }
}

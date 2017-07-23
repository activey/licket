package org.licket.semantic.component.dimmer;

/**
 * @author lukaszgrabski
 */
public class DimmerSettings {

  private boolean pageWide;

  public DimmerSettings() {}

  public DimmerSettings(boolean pageWide) {
    this.pageWide = pageWide;
  }

  public boolean isPageWide() {
    return pageWide;
  }

  public void setPageWide(boolean pageWide) {
    this.pageWide = pageWide;
  }
}

package org.licket.core.view.mount;

import org.licket.core.view.LicketComponent;

/**
 * @author lukaszgrabski
 */
public interface MountedComponents {

  void setMountedLink(Class<? extends LicketComponent> componentClass, String mountPoint);

  /**
   * Returns mount point mapping with respect to Vue parametersFormat. Basically it converts /mountedComponent/{variable} format into /mountedComponent/:variable
   * @param licketComponentClass Component class to render mount mountedComponent to
   * @return String value in format /mountedComponent/:variable
   */
  MountedComponent mountedComponent(Class<? extends LicketComponent> licketComponentClass);
}

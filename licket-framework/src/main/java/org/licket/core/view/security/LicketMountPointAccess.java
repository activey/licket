package org.licket.core.view.security;

/**
 * @author lukaszgrabski
 */
public enum LicketMountPointAccess {

  PUBLIC,
  SECURED;

  public boolean isPublic() {
    return this == PUBLIC;
  }
}

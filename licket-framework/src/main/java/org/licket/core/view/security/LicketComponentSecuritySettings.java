package org.licket.core.view.security;

import org.licket.core.view.LicketComponent;

/**
 * @author lukaszgrabski
 */
public interface LicketComponentSecuritySettings {

  Class<? extends LicketComponent<?>> loginPanelComponentClass();
}

package org.licket.core.view.security;

import org.licket.core.view.container.LicketComponentContainer;

/**
 * @author lukaszgrabski
 */
public interface LicketComponentSecuritySettings {

  Class<? extends LicketComponentContainer> loginPanelComponentClass();
}

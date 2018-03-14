package org.licket.core.view.container;

import org.licket.core.view.LicketComponent;

/**
 * @author grabslu
 */
public interface LicketComponentContainer<T> extends LicketComponent<T> {

  void add(LicketComponent<?> licketComponent);
}

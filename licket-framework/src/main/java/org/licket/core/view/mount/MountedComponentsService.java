package org.licket.core.view.mount;

import org.licket.core.view.LicketComponent;

import java.util.function.BiConsumer;

/**
 * @author lukaszgrabski
 */
public interface MountedComponentsService {

  String getMountedLink(Class<? extends LicketComponent> componentClass);

  void setMountedLink(Class<? extends LicketComponent> componentClass, String mountPoint);

  void forEachMountPoint(BiConsumer<Class<? extends LicketComponent>, String> mountPoint);
}

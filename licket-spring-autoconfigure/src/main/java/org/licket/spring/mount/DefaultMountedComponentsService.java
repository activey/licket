package org.licket.spring.mount;

import org.licket.core.view.LicketComponent;
import org.licket.core.view.mount.MountedComponentsService;

import java.util.Map;
import java.util.function.BiConsumer;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author lukaszgrabski
 */
public class DefaultMountedComponentsService implements MountedComponentsService {

  private Map<Class<? extends LicketComponent>, String> mountMap = newHashMap();

  @Override
  public String getMountedLink(Class<? extends LicketComponent> componentClass) {
    return mountMap.get(componentClass);
  }

  @Override
  public void setMountedLink(Class<? extends LicketComponent> componentClass, String mountPoint) {
    mountMap.put(componentClass, mountPoint);
  }

  @Override
  public void forEachMountPoint(BiConsumer<Class<? extends LicketComponent>, String> mountPoint) {
    mountMap.forEach(mountPoint);
  }
}

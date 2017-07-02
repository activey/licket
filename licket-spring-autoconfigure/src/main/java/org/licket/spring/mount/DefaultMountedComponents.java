package org.licket.spring.mount;

import org.licket.core.view.LicketComponent;
import org.licket.core.view.mount.MountedComponent;
import org.licket.core.view.mount.MountedComponents;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author lukaszgrabski
 */
public class DefaultMountedComponents implements MountedComponents {

  private Map<Class<? extends LicketComponent>, String> mountMap = newHashMap();

  @Override
  public void setMountedLink(Class<? extends LicketComponent> componentClass, String mountPoint) {
    mountMap.put(componentClass, mountPoint);
  }

  @Override
  public MountedComponent mountedComponent(Class<? extends LicketComponent> licketComponentClass) {
    String mountPath = mountMap.get(licketComponentClass);
    if (mountPath == null) {
      // TODO what then?
    }
    // TODO include mountMap, for now just simple stupid
    return new MountedComponent(mountPath);
  }
}

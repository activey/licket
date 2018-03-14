package org.licket.core.view.mount;

import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.framework.hippo.BlockBuilder;

/**
 * @author lukaszgrabski
 */
public interface MountComponentInterceptor {
  void intercept(BlockBuilder blockBuilder, LicketMountPoint mountPoint);
}

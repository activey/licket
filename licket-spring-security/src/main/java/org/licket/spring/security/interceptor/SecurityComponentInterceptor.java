package org.licket.spring.security.interceptor;

import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.core.view.mount.MountComponentInterceptor;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.spring.security.vue.VueComponentSecurityGuardDecorator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lukaszgrabski
 */
public class SecurityComponentInterceptor implements MountComponentInterceptor {

  @Autowired
  private VueComponentSecurityGuardDecorator securityGuardDecorator;

  @Override
  public void intercept(BlockBuilder blockBuilder, LicketMountPoint mountPoint) {
    securityGuardDecorator.decorate(blockBuilder, mountPoint.access());
  }
}

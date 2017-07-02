package org.licket.core.view.hippo.vue.annotation;

import org.licket.core.view.hippo.vue.extend.VueClass;

import java.util.function.Predicate;

/**
 * @author lukaszgrabski
 */
public enum VueComponentFunctionPredicate {

  ANY(vueClass -> true),
  MOUNTED_ONLY(vueClass -> vueClass.getClass().getAnnotation(LicketMountPoint.class) != null);

  private Predicate<VueClass> predicate;

  VueComponentFunctionPredicate(Predicate<VueClass> predicate) {
    this.predicate = predicate;
  }

  public Predicate<VueClass> predicate() {
    return predicate;
  }
}

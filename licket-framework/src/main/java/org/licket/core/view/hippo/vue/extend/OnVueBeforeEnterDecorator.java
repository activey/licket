package org.licket.core.view.hippo.vue.extend;

import org.licket.framework.hippo.BlockBuilder;

import static java.util.Arrays.stream;

/**
 * @author lukaszgrabski
 */
public class OnVueBeforeEnterDecorator {

  private final VueClass vueClass;

  public static OnVueBeforeEnterDecorator fromVueClass(VueClass vueClass) {
    return new OnVueBeforeEnterDecorator(vueClass);
  }

  public final BlockBuilder decorate(BlockBuilder constructorBody) {
    return constructorBody;
  }

  private OnVueBeforeEnterDecorator(VueClass vueClass) {
    this.vueClass = vueClass;
  }
}

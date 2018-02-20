package org.licket.core.view.hippo.vue.extend;

import org.licket.core.view.ComponentFunctionCallback;

/**
 * @author lukaszgrabski
 */
public abstract class AbstractVueClassCallableAPI {

  private ComponentFunctionCallback functionCallback;

  public AbstractVueClassCallableAPI(ComponentFunctionCallback functionCallback) {
    this.functionCallback = functionCallback;
  }

  protected ComponentFunctionCallback functionCallback() {
    return functionCallback;
  }
}

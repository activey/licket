package org.licket.core.view.tree;

import org.licket.framework.hippo.AbstractAstNodeBuilder;

/**
 * @author activey
 */
public abstract class AbstractStep {

  private final String componentId;

  public AbstractStep(String componentId) {
    this.componentId = componentId;
  }

  public final AbstractAstNodeBuilder<?> decorate(AbstractAstNodeBuilder<?> previousStep) {
    return decorate(previousStep, componentId);
  }

  protected abstract AbstractAstNodeBuilder<?> decorate(AbstractAstNodeBuilder<?> step, String componentId);
}

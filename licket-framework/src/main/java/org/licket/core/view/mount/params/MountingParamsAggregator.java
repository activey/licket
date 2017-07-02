package org.licket.core.view.mount.params;

import java.util.List;
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author lukaszgrabski
 */
public class MountingParamsAggregator {

  private List<MountingParamDecorator> aggregatedMountingParamDecorators = newArrayList();

  public MountingParamDecorator name(String paramName) {
    MountingParamDecorator newMountingParamDecorator = new MountingParamDecorator(paramName);
    aggregatedMountingParamDecorators.add(newMountingParamDecorator);
    return newMountingParamDecorator;
  }

  public void forEach(Consumer<MountingParamDecorator> paramConsumer) {
    aggregatedMountingParamDecorators.forEach(paramConsumer);
  }
}

package org.licket.core.view.mount.params;

import java.util.List;
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author lukaszgrabski
 */
public class MountingParamsAggregator {

  private List<Param> aggregatedParams = newArrayList();

  public Param name(String paramName) {
    Param newParam = new Param(paramName);
    aggregatedParams.add(newParam);
    return newParam;
  }

  public void forEach(Consumer<Param> paramConsumer) {
    aggregatedParams.forEach(paramConsumer);
  }
}

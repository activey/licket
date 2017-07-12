package org.licket.core.view;

import org.licket.core.model.LicketComponentReloadingModel;

import java.util.List;
import java.util.function.BiConsumer;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.stream;

/**
 * @author activey
 */
public class ComponentActionCallback extends ComponentFunctionCallback {

  private List<LicketComponentReloadingModel> components = newArrayList();

  // TODO currently reloading of AbstractLicketList is not supported ;/
  public final void reload(LicketComponent<?>... components) {
    stream(components).forEach(licketComponent -> this.components.add(new LicketComponentReloadingModel(licketComponent, false)));
  }

  public final void patch(LicketComponent<?>... components) {
    stream(components).forEach(licketComponent -> this.components.add(new LicketComponentReloadingModel(licketComponent, true)));
  }

  public final void forEachToBeReloaded(BiConsumer<LicketComponent<?>, Boolean> componentConsumer) {
    components.forEach(reloadingModel -> componentConsumer.accept(reloadingModel.getLicketComponent(), reloadingModel.isPatch()));
  }
}

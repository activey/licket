package org.licket.core.view;

import org.licket.core.id.CompositeId;

import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Optional.ofNullable;

/**
 * @author activey
 */
public class ComponentChildLocator {

  private LicketComponent<?> startingComponent;

  public ComponentChildLocator(LicketComponent<?> startingComponent) {
    this.startingComponent = startingComponent;
  }

  public final Optional<LicketComponent<?>> findByCompositeId(CompositeId compositeId) {
    ChildrenTraverse traverse = new ChildrenTraverse(compositeId);
    startingComponent.traverseDown(traverse);
    return traverse.getFound();
  }

  private class ChildrenTraverse implements Predicate<LicketComponent<?>> {

    private CompositeId compositeId;
    private LicketComponent<?> found;

    public ChildrenTraverse(CompositeId compositeId) {
      this.compositeId = compositeId;
    }

    public Optional<LicketComponent<?>> getFound() {
      return ofNullable(found);
    }

    @Override
    public boolean test(LicketComponent<?> component) {
      if (compositeId.hasMore() && compositeId.current().equals(component.getId())) {
        compositeId.forward();
        return true;
      }
      if (compositeId.current().equals(component.getId())) {
        this.found = component;
      }
      return false;
    }
  }
}

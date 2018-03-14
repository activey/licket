package org.licket.surface.element;

/**
 * @author activey
 */
public interface ElementTraverser {

  static ElementTraverser withComponentIdSet() {
    return element -> element.isComponentIdSet();
  }

  boolean elementMatch(SurfaceElement element);
}

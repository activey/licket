package org.licket.surface.element;

/**
 * @author activey
 */
public interface ElementProvider {

  static ElementProvider empty(String localName) {
    return new ElementProvider() {
      @Override
      public String getLocalName() {
        return localName;
      }

      @Override
      public SurfaceElement provideElement() {
        return null;
      }
    };
  }

  String getLocalName();

  SurfaceElement provideElement();
}

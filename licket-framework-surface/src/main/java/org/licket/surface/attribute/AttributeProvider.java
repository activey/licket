package org.licket.surface.attribute;

/**
 * @author activey
 */
public interface AttributeProvider {

  static AttributeProvider empty(String localName) {
    return new AttributeProvider() {
      @Override
      public String getLocalName() {
        return localName;
      }

      @Override
      public BaseAttribute provideAttribute() {
        return null;
      }
    };
  }

  String getLocalName();

  BaseAttribute provideAttribute();
}

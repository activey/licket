package org.licket.spring.websocket.reload;

/**
 * @author lukaszgrabski
 */
public class LicketComponentModel {

  private String compositeId;
  private Object model;

  public LicketComponentModel() {

  }

  public LicketComponentModel(String compositeId, Object model) {
    this.compositeId = compositeId;
    this.model = model;
  }

  public String getCompositeId() {
    return compositeId;
  }

  public void setCompositeId(String compositeId) {
    this.compositeId = compositeId;
  }

  public Object getModel() {
    return model;
  }

  public void setModel(Object model) {
    this.model = model;
  }
}

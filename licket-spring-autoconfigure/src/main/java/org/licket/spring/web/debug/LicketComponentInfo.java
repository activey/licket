package org.licket.spring.web.debug;

import org.licket.core.view.LicketComponent;

import java.util.List;
import java.util.function.Function;

/**
 * @author lukaszgrabski
 */
public class LicketComponentInfo {

  private List<LicketComponentInfo> children;
  private String id;
  private String compositeId;
  private String className;

  public static Function<LicketComponent<?>, LicketComponentInfo> fromComponentInstance() {
    return licketComponent -> {
      LicketComponentInfo componentInfo = new LicketComponentInfo();
      componentInfo.setId(licketComponent.getId());
      componentInfo.setCompositeId(licketComponent.getCompositeId().getValue());
      componentInfo.setClassName(licketComponent.getClass().getName());
      return componentInfo;
    };
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCompositeId() {
    return compositeId;
  }

  public void setCompositeId(String compositeId) {
    this.compositeId = compositeId;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public List<LicketComponentInfo> getChildren() {
    return children;
  }

  public void setChildren(List<LicketComponentInfo> children) {
    this.children = children;
  }
}

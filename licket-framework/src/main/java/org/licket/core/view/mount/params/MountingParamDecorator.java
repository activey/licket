package org.licket.core.view.mount.params;

/**
 * @author lukaszgrabski
 */
public class MountingParamDecorator {

  private final String name;
  private MountingParamValueDecorator value;

  public MountingParamDecorator(String name) {
    this.name = name;
  }

  public void value(MountingParamValueDecorator value) {
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public MountingParamValueDecorator getValue() {
    return value;
  }
}
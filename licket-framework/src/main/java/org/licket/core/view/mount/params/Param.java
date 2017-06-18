package org.licket.core.view.mount.params;

/**
 * @author lukaszgrabski
 */
public class Param {

  private final String name;
  private ParamValue value;

  public Param(String name) {
    this.name = name;
  }

  public void value(ParamValue value) {
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public ParamValue getValue() {
    return value;
  }
}
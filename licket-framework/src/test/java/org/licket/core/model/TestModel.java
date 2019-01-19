package org.licket.core.model;

public class TestModel {

  private int intValue;
  private String stringValue;

  public TestModel() {
  }

  public TestModel(int intValue, String stringValue) {
    this.intValue = intValue;
    this.stringValue = stringValue;
  }

  public int getIntValue() {
    return intValue;
  }

  public void setIntValue(int intValue) {
    this.intValue = intValue;
  }

  public String getStringValue() {
    return stringValue;
  }

  public void setStringValue(String stringValue) {
    this.stringValue = stringValue;
  }
}

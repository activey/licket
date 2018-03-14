package org.licket.framework.hippo;

import org.mozilla.javascript.ast.StringLiteral;

/**
 * @author activey
 */
public class StringLiteralBuilder extends AbstractAstNodeBuilder<StringLiteral> {

  private String value;
  private char quoteChar = '"';

  private StringLiteralBuilder(String value) {
    this.value = value;
  }

  public static StringLiteralBuilder stringLiteral(String value) {
    return new StringLiteralBuilder(value);
  }

  public StringLiteralBuilder quoteChar(char quoteChar) {
    this.quoteChar = quoteChar;
    return this;
  }

  @Override
  public StringLiteral build() {
    StringLiteral stringLiteral = new StringLiteral();
    stringLiteral.setQuoteCharacter(quoteChar);
    stringLiteral.setValue(value);
    return stringLiteral;
  }
}

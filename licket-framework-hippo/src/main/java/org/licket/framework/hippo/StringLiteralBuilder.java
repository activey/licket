package org.licket.framework.hippo;

import org.mozilla.javascript.ast.StringLiteral;

/**
 * @author activey
 */
public class StringLiteralBuilder extends AbstractAstNodeBuilder<StringLiteral> {

    private String value;

    private StringLiteralBuilder(String value) {
        this.value = value;
    }

    public static StringLiteralBuilder stringLiteral(String value) {
        return new StringLiteralBuilder(value);
    }

    @Override
    public StringLiteral build() {
        StringLiteral stringLiteral = new StringLiteral();
        stringLiteral.setValue(value);
        stringLiteral.setQuoteCharacter('"');
        return stringLiteral;
    }
}

package org.licket.framework.hippo;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.KeywordLiteral;

/**
 * @author activey
 */
public class KeywordLiteralBuilder extends AbstractAstNodeBuilder<KeywordLiteral> {

    private int tokenValue;

    private KeywordLiteralBuilder(int tokenValue) {
        this.tokenValue = tokenValue;
    }

    public static KeywordLiteralBuilder thisLiteral() {
        return new KeywordLiteralBuilder(Token.THIS);
    }

    @Override
    public KeywordLiteral build() {
        KeywordLiteral keywordLiteral = new KeywordLiteral();
        keywordLiteral.setType(tokenValue);
        return keywordLiteral;
    }
}

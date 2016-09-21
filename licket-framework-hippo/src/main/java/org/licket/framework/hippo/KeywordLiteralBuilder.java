package org.licket.framework.hippo;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.KeywordLiteral;

/**
 * @author activey
 */
public class KeywordLiteralBuilder extends AbstractAstNodeBuilder<KeywordLiteral> {

    private KeywordLiteralBuilder() {}

    public static KeywordLiteralBuilder keywordLiteral() {
        return new KeywordLiteralBuilder();
    }

    @Override
    public KeywordLiteral build() {
        KeywordLiteral keywordLiteral = new KeywordLiteral();
        keywordLiteral.setType(Token.THIS);
        return keywordLiteral;
    }
}

package org.licket.framework.hippo;

import org.mozilla.javascript.ast.AstNode;

/**
 * @author activey
 */
public abstract class AbstractAstNodeBuilder<T extends AstNode> {

    public abstract T build();
}

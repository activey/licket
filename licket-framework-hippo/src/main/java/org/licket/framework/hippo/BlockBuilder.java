package org.licket.framework.hippo;

import org.mozilla.javascript.ast.Block;
import org.mozilla.javascript.ast.ExpressionStatement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author activey
 */
public class BlockBuilder extends AbstractAstNodeBuilder<Block> {

    private List<AbstractAstNodeBuilder> statements = new LinkedList<>();

    private BlockBuilder() {}

    public static BlockBuilder block() {
        return new BlockBuilder();
    }

    public BlockBuilder statement(AbstractAstNodeBuilder<ExpressionStatement> statementBuilder) {
        statements.add(0, statementBuilder);
        return this;
    }

    public BlockBuilder statement(FunctionCallBuilder functionCallBuilder) {
        statements.add(0, functionCallBuilder);
        return this;
    }

    public BlockBuilder statement(AssignmentBuilder assignment) {
        statements.add(0, assignment);
        return this;
    }

    @Override
    public Block build() {
        Block block = new Block();
        statements.forEach(statement -> block.addStatement(statement.build()));
        return block;
    }
}

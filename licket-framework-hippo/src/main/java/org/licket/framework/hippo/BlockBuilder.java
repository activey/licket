package org.licket.framework.hippo;

import org.mozilla.javascript.ast.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * @author activey
 */
public class BlockBuilder extends AbstractAstNodeBuilder<Block> {

    private List<AbstractAstNodeBuilder> statements = new ArrayList();

    private BlockBuilder() {}

    public static BlockBuilder block() {
        return new BlockBuilder();
    }

    public BlockBuilder statement(ExpressionStatementBuilder statementBuilder) {
        statements.add(statementBuilder);
        return this;
    }

    public BlockBuilder statement(FunctionCallBuilder functionCallBuilder) {
        statements.add(functionCallBuilder);
        return this;
    }

    public BlockBuilder statement(AssignmentBuilder assignment) {
        statements.add(assignment);
        return this;
    }

    @Override
    public Block build() {
        Block block = new Block();
        statements.forEach(statement -> block.addStatement(statement.build()));
        return block;
    }
}

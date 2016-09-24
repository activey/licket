package org.licket.framework.hippo;

import org.mozilla.javascript.ast.Block;
import org.mozilla.javascript.ast.ExpressionStatement;

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

    public BlockBuilder prependStatement(AbstractAstNodeBuilder<ExpressionStatement> statementBuilder) {
        statements.add(0, statementBuilder);
        return this;
    }

    public BlockBuilder appendStatement(ReturnStatementBuilder statementBuilder) {
        statements.add(statementBuilder);
        return this;
    }

    public BlockBuilder appendStatement(AbstractAstNodeBuilder<ExpressionStatement> statementBuilder) {
        statements.add(statementBuilder);
        return this;
    }

    public BlockBuilder prependStatement(FunctionCallBuilder functionCallBuilder) {
        statements.add(0, functionCallBuilder);
        return this;
    }

    public BlockBuilder appendStatement(FunctionCallBuilder functionCallBuilder) {
        statements.add(functionCallBuilder);
        return this;
    }

    public BlockBuilder prependStatement(AssignmentBuilder assignment) {
        statements.add(0, assignment);
        return this;
    }

    public BlockBuilder appendStatement(AssignmentBuilder assignment) {
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

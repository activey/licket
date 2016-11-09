package org.licket.framework.hippo;

import org.mozilla.javascript.ast.FunctionNode;

import java.util.ArrayList;
import java.util.List;

import static org.licket.framework.hippo.BlockBuilder.block;

/**
 * @author activey
 */
public class FunctionNodeBuilder extends AbstractAstNodeBuilder<FunctionNode> {

    private List<NameBuilder> paramBuilders = new ArrayList();
    private BlockBuilder blockBuilder = block();

    private FunctionNodeBuilder() {}

    public static FunctionNodeBuilder functionNode() {
        return new FunctionNodeBuilder();
    }

    public FunctionNodeBuilder param(NameBuilder paramNameBuilder) {
        paramBuilders.add(paramNameBuilder);
        return this;
    }

    public FunctionNodeBuilder body(BlockBuilder blockBuilder) {
        this.blockBuilder = blockBuilder;
        return this;
    }

    @Override
    public FunctionNode build() {
        FunctionNode functionNode = new FunctionNode();
        paramBuilders.forEach(paramBuilder -> functionNode.addParam(paramBuilder.build()));
        functionNode.setBody(blockBuilder.build());
        return functionNode;
    }
}

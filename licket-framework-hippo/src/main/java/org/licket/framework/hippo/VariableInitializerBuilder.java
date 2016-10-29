package org.licket.framework.hippo;

import org.mozilla.javascript.ast.VariableInitializer;

/**
 * @author activey
 */
public class VariableInitializerBuilder extends AbstractAstNodeBuilder<VariableInitializer> {

    private AbstractAstNodeBuilder<?> target;
    private AbstractAstNodeBuilder<?> initializer;

    private VariableInitializerBuilder() {}

    public static VariableInitializerBuilder variableInitializer() {
        return new VariableInitializerBuilder();
    }

    public VariableInitializerBuilder target(NameBuilder name) {
        this.target = name;
        return this;
    }

    public VariableInitializerBuilder initializer(AbstractAstNodeBuilder<?> initializer) {
        this.initializer = initializer;
        return this;
    }

    @Override
    public VariableInitializer build() {
        VariableInitializer variableInitializer = new VariableInitializer();
        variableInitializer.setTarget(target.build());
        variableInitializer.setInitializer(initializer.build());
        return variableInitializer;
    }
}

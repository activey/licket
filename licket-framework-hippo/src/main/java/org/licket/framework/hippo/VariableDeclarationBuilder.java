package org.licket.framework.hippo;

import org.mozilla.javascript.ast.VariableDeclaration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author activey
 */
public class VariableDeclarationBuilder extends AbstractAstNodeBuilder<VariableDeclaration> {

    private List<VariableInitializerBuilder> variableInitializers = new ArrayList<>();

    private VariableDeclarationBuilder() {}

    public static VariableDeclarationBuilder variableDeclaration() {
        return new VariableDeclarationBuilder();
    }

    public VariableDeclarationBuilder variable(VariableInitializerBuilder variableInitializer) {
        variableInitializers.add(variableInitializer);
        return this;
    }

    @Override
    public VariableDeclaration build() {
        VariableDeclaration variableDeclaration = new VariableDeclaration();
        variableInitializers.forEach(variable -> variableDeclaration.addVariable(variable.build()));
        return variableDeclaration;
    }
}

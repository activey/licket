package org.licket.framework.hippo;

import org.mozilla.javascript.ast.Assignment;

import static org.mozilla.javascript.Token.ASSIGN;

/**
 * @author activey
 */
public class AssignmentBuilder extends AbstractAstNodeBuilder<Assignment> {

    private AbstractAstNodeBuilder<?> left;
    private AbstractAstNodeBuilder<?> right;

    private AssignmentBuilder() {}

    public static AssignmentBuilder assignment() {
        return new AssignmentBuilder();
    }

    public AssignmentBuilder left(NameBuilder nameBuilder) {
        this.left = nameBuilder;
        return this;
    }

    public AssignmentBuilder left(PropertyNameBuilder propertyNameBuilder) {
        this.left = propertyNameBuilder;
        return this;
    }

    public AssignmentBuilder right(NameBuilder nameBuilder) {
        this.right = nameBuilder;
        return this;
    }

    public AssignmentBuilder right(PropertyNameBuilder propertyNameBuilder) {
        this.right = propertyNameBuilder;
        return this;
    }

    public AssignmentBuilder right(StringLiteralBuilder stringLiteralBuilder) {
        this.right = stringLiteralBuilder;
        return this;
    }

    public AssignmentBuilder right(ObjectLiteralBuilder objectLiteralBuilder) {
        this.right = objectLiteralBuilder;
        return this;
    }

    public AssignmentBuilder right(FunctionCallBuilder functionCall) {
        this.right = functionCall;
        return this;
    }

    public AssignmentBuilder right(FunctionNodeBuilder functionNode) {
        this.right = functionNode;
        return this;
    }

    @Override
    public Assignment build() {
        Assignment assignment = new Assignment();
        assignment.setLeft(left.build());
        assignment.setRight(right.build());
        assignment.setOperator(ASSIGN);
        return assignment;
    }
}

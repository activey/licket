package org.licket.framework.hippo;

import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.ForLoop;
import org.mozilla.javascript.ast.InfixExpression;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.NodeVisitor;
import org.mozilla.javascript.ast.NumberLiteral;
import org.mozilla.javascript.ast.VariableDeclaration;
import org.mozilla.javascript.ast.VariableInitializer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author grabslu
 */
public class Test {

  public static void main(String[] args) throws IOException {
    AstRoot astRoot = new Parser().parse(
        new InputStreamReader(Test.class.getClassLoader().getResourceAsStream("TestComponent.js")),
        "test.js", 0);

//    astRoot.visit(visitor());

//    astRoot.forEach(action -> {
//      ForLoop forLoop = (ForLoop) action;
//
//      InfixExpression expression = new InfixExpression();
//      Name leftName = new Name();
//      leftName.setIdentifier("test");
//
//      NumberLiteral literal = new NumberLiteral();
//      literal.setValue("100");
//      literal.setType(40);
//      literal.setNumber(100);
//
//      expression.setLeft(leftName);
//      expression.setRight(literal);
//      expression.setOperator(14);
//      forLoop.setCondition(expression);
//
//      System.out.println(forLoop.toSource());
//    });

    astRoot.forEach(action -> {
      VariableDeclaration componentDeclaration = (VariableDeclaration) action;
      componentDeclaration.getVariables().forEach(initializer -> {

        System.out.println(initializer.getInitializer().toSource(4));
      });

    });

//    System.out.println(astRoot.toSource(4));
  }

  private static NodeVisitor visitor() {
    return astNode -> {
      System.out.println(astNode.getClass().getName());
      astNode.visit(visitor());
      return false;
    };
  }
}

package org.licket.framework.hippo;

import org.mozilla.javascript.ast.ArrayLiteral;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.PropertyGet;

import java.util.LinkedList;
import java.util.List;

/**
 * @author activey
 */
public class ArrayLiteralBuilder extends AbstractAstNodeBuilder<ArrayLiteral> {

    private List<AbstractAstNodeBuilder> elements = new LinkedList<>();

    private ArrayLiteralBuilder() {}

    public static ArrayLiteralBuilder arrayLiteral() {
        return new ArrayLiteralBuilder();
    }

    public ArrayLiteralBuilder element(NameBuilder name) {
        elements.add(name);
        return this;
    }

    public ArrayLiteralBuilder element(AbstractAstNodeBuilder<PropertyGet> propertyGet) {
        elements.add(propertyGet);
        return this;
    }

    public ArrayLiteralBuilder element(FunctionNodeBuilder functionNode) {
        elements.add(functionNode);
        return this;
    }

    @Override
    public ArrayLiteral build() {
        ArrayLiteral arrayLiteral = new ArrayLiteral();
        elements.forEach(element -> arrayLiteral.addElement(element.build()));
        return arrayLiteral;
    }

    public final int elementsSize() {
        return elements.size();
    }
}

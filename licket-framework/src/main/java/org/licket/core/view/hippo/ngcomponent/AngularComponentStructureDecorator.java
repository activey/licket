package org.licket.core.view.hippo.ngcomponent;

import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.AngularStructuralDecorator;
import org.licket.framework.hippo.*;

import static org.licket.core.view.LicketUrls.componentViewUrl;
import static org.licket.core.view.hippo.ngclass.AngularInjectablesDecorator.forAngularClassDependencies;
import static org.licket.core.view.hippo.ngcomponent.LicketComponentChildrenDecorator.forLicketComponentChildren;
import static org.licket.framework.hippo.ArrayLiteralBuilder.arrayLiteral;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author activey
 */
public class AngularComponentStructureDecorator implements AngularStructuralDecorator {

    private LicketComponent<?> licketComponent;

    public static AngularComponentStructureDecorator fromLicketComponent(LicketComponent<?> licketComponent) {
        return new AngularComponentStructureDecorator(licketComponent);
    }

    private AngularComponentStructureDecorator(LicketComponent<?> licketComponent) {
        this.licketComponent = licketComponent;
    }

    public final AssignmentBuilder decorate(AngularStructuralDecorator decorable) {
        return assignment()
                .left(licketComponent.angularName())
                .right(functionCall()
                        .target(createModuleFunctionCall(decorable))
                        .argument(decorable.body()));
    }

    private PropertyNameBuilder createModuleFunctionCall(AngularStructuralDecorator decorable) {
        return property(
                functionCall()
                        .target(property(property(name("ng"), name("core")), mostRight()))
                        .argument(body()),
                decorable.mostRight());
    }

    @Override
    public NameBuilder mostRight() {
        return name("Component");
    }

    @Override
    public ObjectLiteralBuilder body() {
        ObjectLiteralBuilder structureBody = objectLiteral()
                .objectProperty(propertyBuilder().name("selector").value(stringLiteral(licketComponent.getId())))
                .objectProperty(propertyBuilder().name("templateUrl").value(stringLiteral(componentViewUrl(licketComponent))));
        ArrayLiteralBuilder componentServicesDependencies = componentServicesDependencies();
        if (componentServicesDependencies.elementsSize() > 0) {
            structureBody.objectProperty(propertyBuilder()
                    .name("providers")
                    .arrayValue(componentServicesDependencies));
        }
        ArrayLiteralBuilder directives = componentChildren();
        if (directives.elementsSize() > 0) {
            structureBody.objectProperty(propertyBuilder()
                    .name("directives")
                    .arrayValue(componentChildren()));
        }
        return structureBody;
    }

    private ArrayLiteralBuilder componentServicesDependencies() {
        return forAngularClassDependencies(licketComponent).decorate(arrayLiteral());
    }

    private ArrayLiteralBuilder componentChildren() {
        return forLicketComponentChildren(licketComponent).decorate(arrayLiteral());
    }
}

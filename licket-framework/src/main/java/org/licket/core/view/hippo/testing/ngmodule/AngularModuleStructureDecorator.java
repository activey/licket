package org.licket.core.view.hippo.testing.ngmodule;

import org.licket.core.LicketApplication;
import org.licket.core.view.hippo.testing.AngularStructuralDecorator;
import org.licket.framework.hippo.ArrayLiteralBuilder;
import org.licket.framework.hippo.AssignmentBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.FluentIterable.from;
import static org.licket.framework.hippo.ArrayLiteralBuilder.arrayLiteral;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class AngularModuleStructureDecorator implements AngularStructuralDecorator {

    private AngularModule angularModule;
    private LicketApplication application;

    public static AngularModuleStructureDecorator fromAngularModule(AngularModule angularModule, LicketApplication application) {
        return new AngularModuleStructureDecorator(angularModule, application);
    }

    private AngularModuleStructureDecorator(AngularModule angularModule, LicketApplication application) {
        this.angularModule = angularModule;
        this.application = application;
    }

    public final AssignmentBuilder decorate(AngularStructuralDecorator decorable) {
        return assignment()
                .left(angularModule.angularName())
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
        return name("NgModule");
    }

    @Override
    public ObjectLiteralBuilder body() {
        return objectLiteral()
                .objectProperty(propertyBuilder().name("imports").arrayValue(moduleImports()))
                .objectProperty(propertyBuilder().name("declarations").arrayValue(declarations()))
                .objectProperty(propertyBuilder().name("bootstrap").arrayValue(bootstrapComponents()));
    }

    private ArrayLiteralBuilder moduleImports() {
        ArrayLiteralBuilder arrayLiteralBuilder = arrayLiteral();
        from(application.modules())
                .filter(not(equalTo(angularModule)))
                .forEach(module -> arrayLiteralBuilder.element(module.angularName()));
        return arrayLiteralBuilder;
    }

    private ArrayLiteralBuilder declarations() {
        ArrayLiteralBuilder arrayLiteralBuilder = arrayLiteral();
        angularModule.injectables().forEach(injectable -> arrayLiteralBuilder.element(injectable.angularName()));
        angularModule.classes().forEach(angularClass -> arrayLiteralBuilder.element(angularClass.angularName()));
        return arrayLiteralBuilder;
    }

    private ArrayLiteralBuilder bootstrapComponents() {
        return arrayLiteral().element(application.rootComponentContainer().angularName());
    }
}

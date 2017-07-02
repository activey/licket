package org.licket.core.view.link;

import org.licket.core.view.LicketComponent;
import org.licket.core.view.mount.params.MountingParamsAggregator;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.ObjectPropertyBuilder;

import java.util.List;
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author activey
 */
public class ComponentFunctionCallback {

    private List<FunctionCallBuilder> functionCalls = newArrayList();

    public final void call(FunctionCallBuilder functionCallBuilder) {
        functionCalls.add(functionCallBuilder);
    }

    public final void navigateToMounted(Class<? extends LicketComponent<?>> componentClass) {
        navigateToMounted(componentClass, paramsAggregator -> {});
    }

    public final void navigateToMounted(Class<? extends LicketComponent<?>> componentClass, Consumer<MountingParamsAggregator> params) {
        call(functionCall()
                .target(property(property("this", "$router"), "push"))
                .argument(objectLiteral()
                        .objectProperty(propertyBuilder().name("name").value(stringLiteral(componentClass.getName())))
                        .objectProperty(propertyBuilder().name("params").value(mountingParams(params)))));
    }

    private ObjectLiteralBuilder mountingParams(Consumer<MountingParamsAggregator> params) {
        MountingParamsAggregator paramsAggregator = new MountingParamsAggregator();
        params.accept(paramsAggregator);

        ObjectLiteralBuilder paramsLiteral = objectLiteral();
        paramsAggregator.forEach(param -> {
            ObjectPropertyBuilder propertyBuilder = propertyBuilder().name(param.getName());
            param.getValue().decorateProperty(propertyBuilder);
            paramsLiteral.objectProperty(propertyBuilder);
        });
        return paramsLiteral;
    }

    public final void forEachCall(Consumer<FunctionCallBuilder> functionCallConsumer) {
        functionCalls.forEach(functionCallConsumer);
    }
}

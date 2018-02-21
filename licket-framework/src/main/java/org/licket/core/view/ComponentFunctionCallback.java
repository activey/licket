package org.licket.core.view;

import org.licket.core.view.mount.MountedComponentNavigation;
import org.licket.core.view.mount.params.MountingParamsAggregator;
import org.licket.framework.hippo.FunctionCallBuilder;

import java.util.List;
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class ComponentFunctionCallback {

    private MountedComponentNavigation mountedComponentNavigation = new MountedComponentNavigation();
    private List<FunctionCallBuilder> functionCalls = newArrayList();

    public final void call(FunctionCallBuilder functionCallBuilder) {
        functionCalls.add(functionCallBuilder);
    }

    public final void navigateToMounted(Class<? extends LicketComponent<?>> componentClass) {
        mountedComponentNavigation.navigateToMounted(componentClass, property("this", "$router"), paramsAggregator -> {});
    }

    public final void navigateToMounted(Class<? extends LicketComponent<?>> componentClass, Consumer<MountingParamsAggregator> params) {
        mountedComponentNavigation.navigateToMounted(componentClass, property("this", "$router"), params);
    }

    public final void forEachCall(Consumer<FunctionCallBuilder> functionCallConsumer) {
        functionCalls.forEach(functionCallConsumer);
    }
}

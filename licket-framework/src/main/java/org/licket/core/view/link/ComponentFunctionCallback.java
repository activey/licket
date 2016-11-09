package org.licket.core.view.link;

import org.licket.framework.hippo.FunctionCallBuilder;

import java.util.List;
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author activey
 */
public class ComponentFunctionCallback {

    private List<FunctionCallBuilder> functionCalls = newArrayList();

    public final void call(FunctionCallBuilder functionCallBuilder) {
        functionCalls.add(functionCallBuilder);
    }

    public final void forEachCall(Consumer<FunctionCallBuilder> functionCallConsumer) {
        functionCalls.forEach(functionCallConsumer);
    }
}

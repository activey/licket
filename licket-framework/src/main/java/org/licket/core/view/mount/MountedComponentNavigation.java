package org.licket.core.view.mount;

import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.ComponentModelProperty;
import org.licket.core.view.mount.params.MountingParamsAggregator;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.ObjectPropertyBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

import java.util.function.Consumer;

import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author lukaszgrabski
 */
public class MountedComponentNavigation {

  private static final String PUSH_FUNCTION = "push";

  public final FunctionCallBuilder navigateToMounted(Class<? extends LicketComponent<?>> componentClass, NameBuilder routerReference, Consumer<MountingParamsAggregator> params) {
    return functionCall()
            .target(property(routerReference, PUSH_FUNCTION))
            .argument(pushArguments(componentClass, params));
  }

  public final FunctionCallBuilder navigateToMounted(Class<? extends LicketComponent<?>> componentClass, PropertyNameBuilder routerReference, Consumer<MountingParamsAggregator> params) {
    return functionCall()
            .target(property(routerReference, PUSH_FUNCTION))
            .argument(pushArguments(componentClass, params));
  }

  private ObjectLiteralBuilder pushArguments(Class<? extends LicketComponent<?>> componentClass, Consumer<MountingParamsAggregator> params) {
    return objectLiteral()
            .objectProperty(propertyBuilder().name("name").value(stringLiteral(componentClass.getName())))
            .objectProperty(propertyBuilder().name("params").value(mountingParams(params)));
  }

  public final FunctionCallBuilder navigateToPath(ComponentModelProperty path, PropertyNameBuilder routerReference) {
    return functionCall()
            .target(property(routerReference, PUSH_FUNCTION))
            .argument(
                    objectLiteral().objectProperty(propertyBuilder().name("path").value(path.builder()))
            );
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
}

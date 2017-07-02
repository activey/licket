package org.licket.core.view.mount;

import org.licket.core.view.LicketComponent;
import org.licket.core.view.link.AbstractLicketLink;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.core.view.mount.params.MountingParamsAggregator;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.ObjectPropertyBuilder;

import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author lukaszgrabski
 */
public class MountedComponentLink<T> extends AbstractLicketLink {

  private final Class<? extends LicketComponent<T>> componentClass;

  public MountedComponentLink(String id, Class<? extends LicketComponent<T>> componentClass) {
    super(id);
    this.componentClass = componentClass;
  }

  @Override
  protected final void onClick(ComponentActionCallback componentActionCallback) {
    // TODO retrieve component instance knowing it's componentClass
    componentActionCallback.call(functionCall()
            .target(property(property("this", "$router"), "push"))
            .argument(objectLiteral()
                      .objectProperty(propertyBuilder().name("name").value(stringLiteral(componentClass.getName())))
                      .objectProperty(propertyBuilder().name("params").value(mountingParams()))));
  }

  private ObjectLiteralBuilder mountingParams() {
    MountingParamsAggregator paramsAggregator = new MountingParamsAggregator();
    aggregateParams(paramsAggregator);

    ObjectLiteralBuilder paramsLiteral = objectLiteral();
    paramsAggregator.forEach(param -> {
      ObjectPropertyBuilder propertyBuilder = propertyBuilder().name(param.getName());
      param.getValue().decorateProperty(propertyBuilder);
      paramsLiteral.objectProperty(propertyBuilder);
    });
    return paramsLiteral;
  }

  protected void aggregateParams(MountingParamsAggregator paramsAggregator) {
    // override this to provide mounting parameters
  }
}

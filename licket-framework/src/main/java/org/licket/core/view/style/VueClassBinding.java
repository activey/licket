package org.licket.core.view.style;

import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.surface.element.SurfaceElement;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author lukaszgrabski
 */
public class VueClassBinding {

  private final SurfaceElement surfaceElement;
  private Map<String, VueClassBindingCondition> classBindingConditionMap = newHashMap();

  private VueClassBinding(SurfaceElement surfaceElement) {
    this.surfaceElement = surfaceElement;
  }

  public static VueClassBinding styleClassBindingFor(SurfaceElement surfaceElement) {
    return new VueClassBinding(surfaceElement);
  }

  public VueClassBinding conditionalClass(String className, VueClassBindingCondition classBindingCondition) {
    classBindingConditionMap.put(className, classBindingCondition);
    return this;
  }

  public void apply() {
    ObjectLiteralBuilder objectLiteralBuilder = ObjectLiteralBuilder.objectLiteral();
    classBindingConditionMap.forEach((className, vueClassBindingCondition) -> {
      objectLiteralBuilder.objectProperty(
              propertyBuilder()
                      .name(stringLiteral(className).quoteChar('\''))
                      .value(vueClassBindingCondition.expression())
      );
    });
    surfaceElement.addAttribute("v-bind:class", objectLiteralBuilder.build().toSource());
  }
}

package org.licket.core.view.mount;

import com.google.common.annotations.VisibleForTesting;
import org.licket.framework.hippo.ObjectLiteralBuilder;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newLinkedList;
import static java.lang.String.format;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author lukaszgrabski
 */
public class MountedComponent {

  private static final Pattern PATH_ENTRY_PATTERN = Pattern.compile("\\{([^}]*)\\}");
  private List<String> pathVariables = newLinkedList();
  private String vuePath;

  public MountedComponent(String mountPath) {
    this.vuePath = processMountPath(mountPath);
  }

  @VisibleForTesting
  String processMountPath(String mountPath) {
    Matcher matcher = PATH_ENTRY_PATTERN.matcher(mountPath);
    StringBuffer pathBuffer = new StringBuffer(mountPath.length());
    while (matcher.find()) {
      String pathVariable = matcher.group(1);
      pathVariables.add(pathVariable);
      matcher.appendReplacement(pathBuffer, format(":%s", pathVariable));
    }
    matcher.appendTail(pathBuffer);
    return pathBuffer.toString();
  }

  public final String path() {
    return vuePath;
  }

  public ObjectLiteralBuilder params() {
    ObjectLiteralBuilder objectLiteralBuilder = objectLiteral();
    pathVariables.forEach(variable -> objectLiteralBuilder.objectProperty(
            // TODO dummy implementation for now ..
            propertyBuilder().name(variable).value(property(property("this", "model"), name(variable))))
    );
    return objectLiteralBuilder;
  }
}


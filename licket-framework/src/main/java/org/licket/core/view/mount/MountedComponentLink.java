package org.licket.core.view.mount;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.link.AbstractLicketActionLink;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.xml.dom.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static java.lang.String.format;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author lukaszgrabski
 */
public class MountedComponentLink<T> extends AbstractLicketActionLink {

  private final static Logger LOGGER = LoggerFactory.getLogger(MountedComponentLink.class);
  private final Class<? extends LicketComponent<T>> componentClass;

  public MountedComponentLink(String id, LicketRemote licketRemote, LicketComponentModelReloader modelReloader, Class<? extends LicketComponent<T>> componentClass) {
    super(id, licketRemote, modelReloader);
    this.componentClass = componentClass;
  }

//  @Override
//  protected void onBeforeRender(ComponentRenderingContext renderingContext) {
//    renderingContext.onSurfaceElement(surfaceElement -> {
//      if (!surfaceElement.getLocalName().equals("a")) {
//        LOGGER.error(format("Component %s should be of type <a>.", getId()));
//        surfaceElement.replaceWith(new Comment("Component %s should be of type <a>.", getId()));
//      }
//    });
//  }

  @Override
  protected void onAfterClick(ComponentActionCallback componentActionCallback) {
    componentActionCallback.call(functionCall()
            .target(property(property("this", "$router"), "push"))
            .argument(stringLiteral("/contact")));
  }
}

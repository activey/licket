package org.licket.spring.surface.element.html;

import org.licket.core.resource.ResourceStorage;
import org.licket.surface.SurfaceContext;
import org.licket.surface.element.SurfaceElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.spring.surface.element.html.HtmlElementFactory.HTML_NAMESPACE;
import static org.licket.spring.surface.element.html.LinkElement.LinkRelType.STYLESHEET;

/**
 * @author activey
 */
public class HeadElement extends SurfaceElement {

  private static final Logger LOGGER = LoggerFactory.getLogger(HeadElement.class);

  @Autowired
  private ResourceStorage resourcesStorage;

  public HeadElement(String name) {
    super(name, HTML_NAMESPACE);
  }

  @Override
  protected void onFinish(SurfaceContext surfaceContext) {
    resourcesStorage.getHeadJavascriptResources().forEach(resource -> {
      LOGGER.debug("Using head JS resource: {}", resource.getName());

      ScriptElement scriptElement = new ScriptElement();
      scriptElement.setSrc(resourcesStorage.getResourceUrl(resource));
      addChildElement(scriptElement);
    });

    resourcesStorage.getStylesheetResources().forEach(resource -> {
      LOGGER.debug("Using head CSS resource: {}", resource.getName());

      LinkElement linkElement = new LinkElement();
      linkElement.setHref(resourcesStorage.getResourceUrl(resource));
      linkElement.setType(resource.getMimeType());
      linkElement.setRelType(STYLESHEET);
      addChildElement(linkElement);
    });
  }
}

package org.licket.spring.web;

import org.licket.core.LicketApplication;
import org.licket.core.id.CompositeId;
import org.licket.core.resource.ByteArrayResource;
import org.licket.core.resource.Resource;
import org.licket.core.resource.ResourceStorage;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.spring.surface.element.html.compiler.ComponentTemplateCompiler;
import org.licket.surface.SurfaceContext;
import org.licket.surface.tag.ElementFactories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * @author activey
 */
@Controller
@RequestMapping("/")
public class LicketRootController {

  private static final Logger LOGGER = LoggerFactory.getLogger(LicketRootController.class);

  @Autowired
  private LicketApplication licketApplication;

  @Autowired
  private ResourceStorage resourcesStorage;

  @Autowired
  private ElementFactories surfaceElementFactories;

  @PostConstruct
  private void initialize() {
    LOGGER.debug("Initializing Licket application: {}.", licketApplication.getName());

    // compiling all mounted components
    licketApplication.traverseDown(licketComponent -> {
      LicketMountPoint mountPoint = licketComponent.getClass().getAnnotation(LicketMountPoint.class);
      if (mountPoint == null) {
        return true;
      }
      ComponentTemplateCompiler templateCompiler =
              new ComponentTemplateCompiler(() -> licketComponent);
      if (isRootComponent(licketComponent)) {
        // compiling root component template
        resourcesStorage.putResource(new ByteArrayResource("index.html", TEXT_HTML_VALUE,
                templateCompiler.compile(surfaceContext(licketComponent.getCompositeId()))));
        return true;
      }
      resourcesStorage.putResource(new ByteArrayResource(licketComponent.getCompositeId().getValue(), TEXT_HTML_VALUE,
              templateCompiler.compile(surfaceContext(licketComponent.getCompositeId()))));
      return true;
    });
  }

  private boolean isRootComponent(LicketComponent<?> licketComponent) {
    return licketApplication.rootComponentContainer().getCompositeId().equals(licketComponent.getCompositeId());
  }

  private SurfaceContext surfaceContext(CompositeId parentCompositeId) {
    return new SurfaceContext(surfaceElementFactories, parentCompositeId);
  }

  @GetMapping(value = "/index", produces = TEXT_HTML_VALUE)
  public @ResponseBody ResponseEntity<InputStreamResource> generateRootHtml() {
    Optional<Resource> resourceOptional = resourcesStorage.getResource("index.html");
    if (!resourceOptional.isPresent()) {
      return status(NOT_FOUND).contentLength(0).body(null);
    }
    Resource resource = resourceOptional.get();
    return ok().contentType(parseMediaType(resource.getMimeType()))
        .body(new InputStreamResource(resource.getStream()));
  }
}

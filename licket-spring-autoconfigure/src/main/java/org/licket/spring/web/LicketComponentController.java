package org.licket.spring.web;

import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.ResponseEntity.ok;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import org.licket.core.LicketApplication;
import org.licket.core.resource.ByteArrayResource;
import org.licket.core.resource.Resource;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.model.LicketControllerModel;
import org.licket.spring.resource.ResourcesStorage;
import org.licket.surface.SurfaceContext;
import org.licket.surface.tag.ElementFactories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping("/licket/component")
public class LicketComponentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicketComponentController.class);

    @Autowired
    private LicketApplication licketApplication;

    @Autowired
    private ElementFactories surfaceElementFactories;

    @Autowired
    private ResourcesStorage resourcesStorage;

    @PostConstruct
    private void initialize() {
        LOGGER.debug("Initializing licket application: {}.", licketApplication.getName());

        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        LicketComponent component = licketApplication.getRootComponent();

        new SurfaceContext(surfaceElementFactories)
                .processTemplateContent(component.getComponentView().readViewContent(), byteArrayStream, true);
        resourcesStorage
                .putResource(new ByteArrayResource(component.getId(), "text/html", byteArrayStream.toByteArray()));
    }

    @GetMapping(value = "/{compositeId}")
    public @ResponseBody LicketControllerModel getLicketComponentModel(@PathVariable String compositeId) {
        Optional<LicketComponent<?>> component = licketApplication.findComponent(compositeId);
        if (!component.isPresent()) {
            // TODO return 404 when there is no such component
            return null;
        }
        return new LicketControllerModel(component.get().getComponentModel().get());
    }

    @GetMapping(value = "/{compositeId}/controller")
    public ResponseEntity<InputStreamResource> generateComponentControllerCode(@PathVariable String compositeId) {
        // TODO implement component js code
        return null;
    }

//    @Cacheable("component-view-cache")
    @GetMapping(value = "/{compositeId}/view", produces = "text/html")
    public ResponseEntity<InputStreamResource> generateComponentViewCode(@PathVariable String compositeId) {
        Optional<LicketComponent<?>> component = licketApplication.findComponent(compositeId);
        if (!component.isPresent()) {
            // TODO return 404 when there is no such component
            return null;
        }
        Optional<Resource> componentViewResourceOptional = resourcesStorage.getResource(compositeId);
        if (!componentViewResourceOptional.isPresent()) {
            // TODO return 404
            return null;
        }
        return ok().contentType(parseMediaType(componentViewResourceOptional.get().getMimeType()))
            .body(new InputStreamResource(componentViewResourceOptional.get().getStream()));
    }
}

package org.licket.spring.web;

import static org.licket.core.view.LicketUrls.CONTEXT_COMPONENT;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.ResponseEntity.ok;
import java.util.Optional;
import org.licket.core.LicketApplication;
import org.licket.core.resource.Resource;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.model.LicketComponentModel;
import org.licket.spring.resource.ResourcesStorage;
import org.licket.surface.tag.ElementFactories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(CONTEXT_COMPONENT)
public class LicketComponentController {

    @Autowired
    private LicketApplication licketApplication;

    @Autowired
    private ElementFactories surfaceElementFactories;

    @Autowired
    private ResourcesStorage resourcesStorage;

    @GetMapping(value = "/{compositeId}", produces = "application/json")
    public @ResponseBody LicketComponentModel getLicketComponentModel(@PathVariable String compositeId) {
        Optional<LicketComponent<?>> component = licketApplication.findComponent(compositeId);
        if (!component.isPresent()) {
            // TODO return 404 when there is no such component
            return null;
        }
        return new LicketComponentModel(component.get().getComponentModel().get());
    }

    @PostMapping(value = "/{compositeId}")
    public @ResponseBody LicketComponentModel invokeComponentAction(@PathVariable String compositeId) {
        Optional<LicketComponent<?>> component = licketApplication.findComponent(compositeId);
        if (!component.isPresent()) {
            // TODO return 404 when there is no such component
            return null;
        }
        return null;
    }

    // @Cacheable("component-view-cache")
    @GetMapping(value = "/{compositeId}/view", produces = TEXT_HTML_VALUE)
    public @ResponseBody ResponseEntity<InputStreamResource> generateComponentViewCode(@PathVariable String compositeId) {
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

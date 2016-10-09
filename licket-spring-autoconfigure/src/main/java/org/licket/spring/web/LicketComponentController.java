package org.licket.spring.web;

import static org.licket.core.view.LicketUrls.CONTEXT_COMPONENT;
import static org.licket.spring.web.ComponentNotFoundException.componentNotFound;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.ResponseEntity.ok;

import java.util.Optional;

import org.licket.core.LicketApplication;
import org.licket.core.resource.Resource;
import org.licket.core.resource.ResourceStorage;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.model.LicketComponentModelGroup;
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
    private ResourceStorage resourcesStorage;

//    @GetMapping(value = "/{compositeId}", produces = "application/json")
//    public @ResponseBody
//    LicketComponentModelGroup getComponentModel(@PathVariable String compositeId) {
//        Optional<LicketComponent<?>> component = licketApplication.findComponent(compositeId);
//        if (!component.isPresent()) {
//            throw componentNotFound(compositeId);
//        }
//        return new LicketComponentModelGroup(component.get().getComponentModel().get());
//    }

    // @Cacheable("component-view-cache")
    @GetMapping(value = "/{compositeId}/view", produces = TEXT_HTML_VALUE)
    public @ResponseBody ResponseEntity<InputStreamResource> generateComponentViewCode(@PathVariable String compositeId) {
        Optional<LicketComponent<?>> component = licketApplication.findComponent(compositeId);
        if (!component.isPresent()) {
            throw componentNotFound(compositeId);
        }
        Optional<Resource> componentViewResourceOptional = resourcesStorage.getResource(compositeId);
        if (!componentViewResourceOptional.isPresent()) {
            // TODO throw some different exception here
            throw componentNotFound(compositeId);
        }
        return ok().contentType(parseMediaType(componentViewResourceOptional.get().getMimeType()))
            .body(new InputStreamResource(componentViewResourceOptional.get().getStream()));
    }
}

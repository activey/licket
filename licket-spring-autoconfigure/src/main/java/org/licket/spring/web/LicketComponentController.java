package org.licket.spring.web;

import com.fasterxml.jackson.databind.JsonNode;
import org.licket.core.LicketApplication;
import org.licket.core.resource.Resource;
import org.licket.core.resource.ResourceStorage;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.core.model.LicketComponentModelGroup;
import org.licket.surface.tag.ElementFactories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

import static org.licket.core.view.LicketUrls.CONTEXT_COMPONENT;
import static org.licket.spring.web.ComponentNotFoundException.componentNotFound;
import static org.licket.spring.web.component.ComponentActionHandler.onComponent;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping(CONTEXT_COMPONENT)
@CrossOrigin(origins = "*")
public class LicketComponentController {

    @Autowired
    private LicketApplication licketApplication;

    @Autowired
    private ElementFactories surfaceElementFactories;

    @Autowired
    private ResourceStorage resourcesStorage;

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

    @PostMapping(value = "/{compositeId}/mount", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody LicketComponentModelGroup mountComponent(@RequestBody JsonNode formData,
                                                              @PathVariable String compositeId) {
        Optional<LicketComponent<?>> componentOptional = licketApplication.findComponent(compositeId);
        if (!componentOptional.isPresent()) {
            throw componentNotFound(compositeId);
        }

        // handling mount
        ComponentActionCallback componentActionCallback = new ComponentActionCallback();
        onComponent(componentOptional.get()).tryMountComponent(formData, componentActionCallback);

        // refreshing component model after mounting operation
        LicketComponentModelGroup modelGroup = new LicketComponentModelGroup();
        modelGroup.addModel(componentOptional.get().getCompositeId().getValue(), componentOptional.get().getComponentModel().get());

        // sending back list of reloaded component models
        componentActionCallback.forEachToBeReloaded(component -> modelGroup
                .addModel(component.getCompositeId().getValue(), component.getComponentModel().get()));

        return modelGroup;
    }
}

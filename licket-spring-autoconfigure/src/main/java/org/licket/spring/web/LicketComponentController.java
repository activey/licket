package org.licket.spring.web;

import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.ResponseEntity.ok;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import org.licket.core.LicketApplication;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.model.LicketControllerModel;
import org.licket.surface.SurfaceContext;
import org.licket.surface.tag.ElementFactories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/licket/component")
public class LicketComponentController {

    @Autowired
    private LicketApplication licketApplication;

    @Autowired
    private ElementFactories surfaceElementFactories;

    @GetMapping(value = "/{compositeId}")
    public @ResponseBody LicketControllerModel getLicketComponentModel(@PathVariable String compositeId) {
        Optional<AbstractLicketComponent<?>> component = licketApplication.findComponent(compositeId);
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
    public ResponseEntity<ByteArrayResource> generateComponentViewCode(@PathVariable String compositeId) {
        Optional<AbstractLicketComponent<?>> component = licketApplication.findComponent(compositeId);
        if (!component.isPresent()) {
            // TODO return 404 when there is no such component
            return null;
        }

        // TODO very temporary, surface context should be invoked on session create, not here!
        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        new SurfaceContext(surfaceElementFactories)
            .processTemplateContent(component.get().getComponentView().readViewContent(), byteArrayStream, true);

        return ok().contentType(parseMediaType("text/html"))
            .body(new ByteArrayResource(byteArrayStream.toByteArray()));
    }
}

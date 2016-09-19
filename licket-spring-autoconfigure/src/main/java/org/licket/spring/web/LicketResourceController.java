package org.licket.spring.web;

import static org.licket.core.view.LicketUrls.CONTEXT_RESOURCES;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import java.util.Optional;
import org.licket.core.LicketApplication;
import org.licket.core.resource.Resource;
import org.licket.spring.resource.ResourcesStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author activey
 */
@Controller
@RequestMapping(CONTEXT_RESOURCES)
public class LicketResourceController {

    @Autowired
    private LicketApplication licketApplication;

    @Autowired
    private ResourcesStorage resourcesStorage;

    @GetMapping(value = "/{resourceName:.+}")
    public ResponseEntity<InputStreamResource> getResource(@PathVariable String resourceName) {
        Optional<Resource> resourceOptional = resourcesStorage.getResource(resourceName);
        if (!resourceOptional.isPresent()) {
            return status(HttpStatus.NOT_FOUND).contentLength(0).body(null);
        }
        Resource resource = resourceOptional.get();
        return ok()
                .contentType(parseMediaType(resource.getMimeType()))
                .body(new InputStreamResource(resource.getStream()));
    }
}

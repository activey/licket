package org.licket.spring.web;

import org.licket.core.LicketApplication;
import org.licket.core.resource.Resource;
import org.licket.core.resource.ResourceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.licket.core.LicketUrls.CONTEXT_RESOURCES;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * @author activey
 */
@Controller
@RequestMapping(CONTEXT_RESOURCES)
public class LicketResourceController {

  @Autowired
  private LicketApplication licketApplication;

  @Autowired
  private ResourceStorage resourcesStorage;

  @GetMapping(value = "/**")
  public ResponseEntity<InputStreamResource> getResource(HttpServletRequest request) {
    String path = (String) request.getAttribute(
            HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    String finalPath = antPathMatcher.extractPathWithinPattern(bestMatchPattern, path);

    Optional<Resource> resourceOptional = resourcesStorage.getResource(finalPath);
    if (!resourceOptional.isPresent()) {
      return status(HttpStatus.NOT_FOUND).contentLength(0).body(null);
    }
    Resource resource = resourceOptional.get();
    return ok()
            .contentType(parseMediaType(resource.getMimeType()))
            .body(new InputStreamResource(resource.getStream()));
  }
}

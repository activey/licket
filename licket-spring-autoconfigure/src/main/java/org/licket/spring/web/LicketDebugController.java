package org.licket.spring.web;

import org.licket.core.resource.ResourceHeader;
import org.licket.core.resource.ResourceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.licket.core.view.LicketUrls.CONTEXT_DEBUG;

/**
 * @author lukaszgrabski
 */
@Controller
@RequestMapping(CONTEXT_DEBUG)
public class LicketDebugController {

  @Autowired
  private ResourceStorage resourcesStorage;

  @GetMapping(value = "/resources")
  public @ResponseBody List<ResourceHeader> resourceHeaderList() {
    return resourcesStorage.getAllResources().collect(toList());
  }
}

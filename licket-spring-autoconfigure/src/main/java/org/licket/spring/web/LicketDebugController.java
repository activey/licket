package org.licket.spring.web;

import org.licket.core.LicketApplication;
import org.licket.core.resource.ResourceHeader;
import org.licket.core.resource.ResourceStorage;
import org.licket.core.view.LicketComponent;
import org.licket.spring.web.debug.LicketComponentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
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

  @Autowired
  private LicketApplication licketApplication;

  @GetMapping(value = "/resources")
  public @ResponseBody List<ResourceHeader> resourceHeaderList() {
    return resourcesStorage.getAllResources().collect(toList());
  }

  @GetMapping(value = "/components")
  public @ResponseBody List<LicketComponentInfo> components() {
    List<LicketComponentInfo> components = newArrayList();
    licketApplication.traverseDown(licketComponent -> {
      if (licketComponent.getCompositeId().length() > 1) {
        return false;
      }
      List<LicketComponentInfo> children = newArrayList();
      addChildren(licketComponent, children);

      LicketComponentInfo componentInfo = LicketComponentInfo.fromComponentInstance().apply(licketComponent);
      componentInfo.setChildren(children);
      components.add(componentInfo);
      return false;
    });
    return components;
  }

  private void addChildren(LicketComponent<?> licketComponent, List<LicketComponentInfo> children) {
    licketComponent.traverseDown(childComponent -> {
      LicketComponentInfo componentInfo = LicketComponentInfo.fromComponentInstance().apply(childComponent);
      List<LicketComponentInfo> subChildren = newArrayList();
      componentInfo.setChildren(subChildren);
      addChildren(childComponent, subChildren);
      children.add(componentInfo);
      return false;
    });
  }
}

package org.licket.spring.surface.element.html.router;

import org.licket.spring.surface.element.html.DefaultHtmlElement;

/**
 * @author activey
 */
public class RootComponentWrapperElement extends DefaultHtmlElement {

  public RootComponentWrapperElement(String rootComponentId) {
    super("div");
    addAttribute("id", rootComponentId);
  }
}

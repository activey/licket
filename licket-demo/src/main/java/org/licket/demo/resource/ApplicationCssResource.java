package org.licket.demo.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.css.StylesheetResource;

/**
 * @author lukaszgrabski
 */
public class ApplicationCssResource extends StylesheetResource implements HeadParticipatingResource {

  public ApplicationCssResource() {
    super("application.css", "css/application.css");
  }
}

package org.licket.core.view.redirect;

import org.licket.framework.hippo.AssignmentBuilder;

import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author lukaszgrabski
 */
public class BrowserRedirect {

  public final AssignmentBuilder redirectToInternalUri(String uri) {
    return assignment().left(property(property("window", "location"), "href")).right(stringLiteral(uri));
  }
}

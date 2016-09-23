package org.licket.framework.hippo.testing;

import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.StringLiteralBuilder;
import org.licket.framework.hippo.testing.annotation.AngularComponent;

import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author grabslu
 */
@AngularComponent
public class ContactsApplicationClass implements AngularClass {

  private ObjectLiteralBuilder model = objectLiteral();
  private StringLiteralBuilder compositeId = stringLiteral("contacts-page");
  private LicketRemoteCommunication remote;

  public ContactsApplicationClass(
      @Name("app.ComponentCommunicationService") LicketRemoteCommunication remote) {
    this.remote = remote;
  }

  public void invokeAction(NameBuilder callerId, BlockBuilder functionBlock) {

  }
}

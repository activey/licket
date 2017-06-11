package org.licket.demo.view;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.core.view.mount.MountedComponentLink;
import org.licket.demo.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

/**
 * @author lukaszgrabski
 */
@LicketMountPoint("/contact/{id}")
public class ViewContactPanel extends AbstractLicketMultiContainer<Contact> {

  @Autowired
  private LicketRemote remoteCommunication;

  @Autowired
  private LicketComponentModelReloader modelReloader;

  public ViewContactPanel(String id, LicketComponentModelReloader modelReloader) {
    super(id, Contact.class, ofModelObject(new Contact()), fromComponentClass(ViewContactPanel.class), modelReloader);
  }

  @Override
  protected void onInitializeContainer() {
    add(new MountedComponentLink("rootLink", remoteCommunication, modelReloader, ContactsAppRoot.class));
  }
}

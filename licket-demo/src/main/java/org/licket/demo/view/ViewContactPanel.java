package org.licket.demo.view;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.LicketLabel;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.core.view.mount.MountedComponentLink;
import org.licket.demo.model.Contact;
import org.licket.demo.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

/**
 * @author lukaszgrabski
 */
@LicketMountPoint("/contact/{id}")
public class ViewContactPanel extends AbstractLicketMultiContainer<Contact> {

  @Autowired
  private LicketComponentModelReloader modelReloader;

  @Autowired
  private ContactsService contactsService;

  public ViewContactPanel(String id) {
    super(id, Contact.class, ofModelObject(new Contact()), fromComponentClass(ViewContactPanel.class));
  }

  @Override
  protected void onInitializeContainer() {
    add(new LicketLabel("name"));
    add(new MountedComponentLink("rootLink", ContactsAppRoot.class));
  }

  @Override
  protected LicketComponentModelReloader getModelReloader() {
    return modelReloader;
  }

  @Override
  protected void onComponentMounted(Contact componentMountingParams) {
    Optional<Contact> contactOptional = contactsService.getContactById(componentMountingParams.getId());
    if (!contactOptional.isPresent()) {
      return;
    }
    setComponentModel(ofModelObject(contactOptional.get()));
  }
}

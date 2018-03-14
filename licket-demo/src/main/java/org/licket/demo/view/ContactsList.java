package org.licket.demo.view;

import org.licket.core.view.LicketLabel;
import org.licket.core.view.hippo.ComponentModelProperty;
import org.licket.core.view.list.AbstractLicketList;
import org.licket.core.view.media.LicketImage;
import org.licket.core.view.mount.MountedComponentLink;
import org.licket.core.view.mount.params.MountingParamsAggregator;
import org.licket.demo.model.Contact;

import java.util.Optional;

import static java.util.Optional.of;
import static org.licket.core.view.mount.params.MountingParamValueDecorator.fromParentModelProperty;

public class ContactsList extends AbstractLicketList {

  public ContactsList(String id, ComponentModelProperty componentModelProperty) {
    super(id, componentModelProperty);
  }

  @Override
  protected void onInitializeContainer() {
    add(new LicketImage("pictureUrl"));
    add(new LicketLabel("name"));
    add(new LicketLabel("description"));
    add(new MountedComponentLink<Contact>("view-contact", ViewContactPanel.class) {
      @Override
      protected void aggregateParams(MountingParamsAggregator paramsAggregator) {
        paramsAggregator.name("id").value(fromParentModelProperty("id"));
      }
    });
  }

  @Override
  protected Optional<String> keyPropertyName() {
    return of("id");
  }
}

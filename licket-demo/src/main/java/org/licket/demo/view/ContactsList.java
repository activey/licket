package org.licket.demo.view;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.LicketLabel;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.core.view.list.AbstractLicketList;
import org.licket.core.view.mount.MountedComponentLink;
import org.licket.core.view.mount.params.MountingParamsAggregator;
import org.licket.demo.model.Contact;
import org.licket.demo.model.EmailAddress;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.model.LicketComponentModel.ofString;
import static org.licket.core.view.mount.params.ParamValue.fromParentModel;

public class ContactsList extends AbstractLicketList<Contact> {

    @Autowired
    private LicketRemote licketRemote;

    @Autowired
    private LicketComponentModelReloader modelReloader;

    public ContactsList(String id, LicketComponentModel<String> enclosingComponentPropertyModel) {
        super(id, enclosingComponentPropertyModel);
    }

    @Override
    protected void onInitializeContainer() {
        add(new LicketLabel("name"));
        add(new LicketLabel("description"));
        add(new AbstractLicketList<EmailAddress>("email", ofString("emails")) {

            @Override
            protected LicketComponentModelReloader getModelReloader() {
                return modelReloader;
            }

            @Override
            protected void onInitializeContainer() {
                add(new LicketLabel("value"));
            }
        });
        add(new MountedComponentLink<Contact>("view-contact", licketRemote, modelReloader(), ViewContactPanel.class) {
            @Override
            protected void aggregateParams(MountingParamsAggregator paramsAggregator) {
                paramsAggregator.name("id").value(fromParentModel("id"));
            }

            @Override
            protected void onAfterMount(ComponentActionCallback componentActionCallback) {
                componentActionCallback.reload();
            }
        });
    }

    @Override
    protected LicketComponentModelReloader getModelReloader() {
        return modelReloader;
    }
}

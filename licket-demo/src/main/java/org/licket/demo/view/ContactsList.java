package org.licket.demo.view;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.LicketLabel;
import org.licket.core.view.list.AbstractLicketList;
import org.licket.core.view.mount.MountedComponentLink;
import org.licket.core.view.mount.params.MountingParamsAggregator;
import org.licket.demo.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.view.mount.params.MountingParamValueDecorator.fromParentModel;

public class ContactsList extends AbstractLicketList<Contact> {

    @Autowired
    private LicketComponentModelReloader modelReloader;

    public ContactsList(String id, LicketComponentModel<String> enclosingComponentPropertyModel) {
        super(id, enclosingComponentPropertyModel);
    }

    @Override
    protected void onInitializeContainer() {
        add(new LicketLabel("name"));
        add(new LicketLabel("description"));
        add(new MountedComponentLink<Contact>("view-contact", ViewContactPanel.class) {
            @Override
            protected void aggregateParams(MountingParamsAggregator paramsAggregator) {
                paramsAggregator.name("id").value(fromParentModel("id"));
            }
        });
    }

    @Override
    protected LicketComponentModelReloader getModelReloader() {
        return modelReloader;
    }
}

package org.licket.core.view.list;

import org.licket.core.model.LicketModel;
import org.licket.core.view.AbstractLicketComponent;

import static org.licket.core.model.LicketModel.empty;
import static org.licket.core.view.LicketComponentView.fromCurrentMarkup;

public abstract class AbstractLicketList<T> extends AbstractLicketComponent<Iterable<T>> {

    public AbstractLicketList(String id, LicketModel<Iterable<T>> listModel) {
        super(id, fromCurrentMarkup(), listModel);
    }

    public AbstractLicketList(String id) {
        super(id, fromCurrentMarkup(), empty());
    }
}

package org.licket.core.view.model;

/**
 * @author activey
 */
public class LicketComponentModel {

    private Object modelObject;

    public LicketComponentModel(Object modelObject) {
        this.modelObject = modelObject;
    }

    public Object getModelObject() {
        return modelObject;
    }

    public void setModelObject(Object modelObject) {
        this.modelObject = modelObject;
    }
}

package org.licket.core.view.model;

/**
 * @author activey
 */
public class LicketControllerModel {

    private Object modelObject;

    public LicketControllerModel(Object modelObject) {
        this.modelObject = modelObject;
    }

    public Object getModelObject() {
        return modelObject;
    }

    public void setModelObject(Object modelObject) {
        this.modelObject = modelObject;
    }
}

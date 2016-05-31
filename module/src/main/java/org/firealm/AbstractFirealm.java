package org.firealm;

/**
 * Created by Vinoth on 31-5-16.
 */
public abstract class AbstractFirealm {

    public AbstractFirealm(FirealmModel model) {
        this.model = model;
        this.property = model.firealmProperty();
    }

    private FirealmModel model;
    private FirealmProperty property;

    public FirealmModel getModel() {
        return model;
    }

    public FirealmProperty getProperty() {
        return property;
    }
}

package com.vinkas.firealm;

import com.vinkas.firealm.model.FirealmModel;

/**
 * Created by Vinoth on 3-6-16.
 */
public class AbstractBridge {

    public AbstractBridge(FirealmModel model) {
        setModel(model);
    }

    private FirealmModel model;
    private static Firealm firealm;

    public FirealmModel getModel() {
        return model;
    }

    public static Firealm getFirealm() {
        return firealm;
    }

    public static void setFirealm(Firealm value) {
        firealm = value;
    }

    protected void setModel(FirealmModel model) {
        this.model = model;
    }
}

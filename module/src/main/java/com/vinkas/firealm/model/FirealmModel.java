package com.vinkas.firealm.model;

import com.vinkas.firealm.WriteListener;
import com.vinkas.realm.model.Firebase;

/**
 * Created by Vinoth on 31-5-16.
 */
public interface FirealmModel extends WriteListener {
    Firebase firebase();
    void setFirebase(Firebase value);
}

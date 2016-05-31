package org.firealm;

import com.google.firebase.database.Exclude;

import io.realm.RealmObject;

/**
 * Created by Vinoth on 31-5-16.
 */
public interface FirealmModel {
    FirealmProperty firealmProperty();
}

package org.firealm;

import com.google.firebase.database.Exclude;

/**
 * Created by Vinoth on 31-5-16.
 */
public interface FirealmModel {
    String getPrimaryKey();
    void setPrimaryKey(String value);
}

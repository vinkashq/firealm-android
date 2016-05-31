package org.firealm;

import android.support.annotation.Nullable;

import com.google.firebase.database.Exclude;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Vinoth on 27-5-16.
 */
public class FirealmProperty extends RealmObject {

    public FirealmProperty() {

    }

    public FirealmProperty(String key, @Nullable Object priority) {
        setKey(key);
        setPriority(priority);
    }

    @PrimaryKey
    @Required
    private String key;

    @Ignore
    private Object priorityObject;

    private String priority;

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String value) {
        this.key = value;
    }

    @Exclude
    public Object getPriority() {
        return priority;
    }

    public void setPriority(Object value) {
        priorityObject = value;
        if (priorityObject == null)
            priority = null;
        else
            priority = priorityObject.toString();
    }

}
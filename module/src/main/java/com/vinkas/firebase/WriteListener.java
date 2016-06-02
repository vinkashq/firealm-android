package com.vinkas.firebase;

import com.google.firebase.database.DatabaseError;
import com.vinkas.firealm.model.FirealmModel;

/**
 * Created by Vinoth on 28-5-16.
 */
public interface WriteListener {
    void onFirebaseWrite(FirealmModel model);
    void onFirebaseError(FirealmModel model, DatabaseError error);
}

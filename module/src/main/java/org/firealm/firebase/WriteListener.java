package org.firealm.firebase;

import com.google.firebase.database.DatabaseError;

/**
 * Created by Vinoth on 28-5-16.
 */
public interface WriteListener {
    void writtenOnFirebase();
    void errorOnFirebase(DatabaseError error);
}

package org.firealm;

import com.google.firebase.database.DatabaseError;

/**
 * Created by Vinoth on 27-5-16.
 */
public interface CompletionListener {
    void writtenOnRealm();
    void writtenOnFirebase();
    void errorOnRealm(Throwable error);
    void errorOnFirebase(DatabaseError error);
}

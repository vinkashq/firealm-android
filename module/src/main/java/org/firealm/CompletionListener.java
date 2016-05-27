package org.firealm;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Vinoth on 27-5-16.
 */
public interface CompletionListener {
    void successOnRealm();
    void successOnFirebase();
    void errorOnRealm(Throwable error);
    void errorOnFirebase(DatabaseError error);
}

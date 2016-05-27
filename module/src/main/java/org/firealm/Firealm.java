package org.firealm;

import com.google.firebase.database.FirebaseDatabase;

import io.realm.Realm;

/**
 * Created by Vinoth on 27-5-16.
 */
public class Firealm {

    private static Firealm firealm;
    public static Firealm getInstance() {
        return firealm;
    }

    private Firealm() {

    }

    public static Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    public static FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

}
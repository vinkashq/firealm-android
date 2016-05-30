package org.firealm;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Vinoth on 28-5-16.
 */
public class Firealm {

    private static Context androidContext;

    public static Context getAndroidContext() {
        return androidContext;
    }

    public static void setAndroidContext(Context context) {
        androidContext = context;
        setRealm();
    }

    private static Realm realm;
    public static Object defaultRealmModule;

    public static Realm getRealm() {
        return realm;
    }

    protected static void setRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder(getAndroidContext())
                .name("firealm.realm")
                .modules(defaultRealmModule, new FirealmModule())
                .build();
        realm = Realm.getInstance(configuration);
    }

    public static FirebaseDatabase getFirebase() {
        return FirebaseDatabase.getInstance();
    }
}

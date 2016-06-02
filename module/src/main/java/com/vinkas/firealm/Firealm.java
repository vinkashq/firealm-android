package com.vinkas.firealm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vinkas.firealm.model.FirealmModel;
import com.vinkas.firebase.FirebaseBridge;
import com.vinkas.realm.RealmBridge;
import com.vinkas.realm.module.FirealmModule;

import java.util.ArrayList;

import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;

/**
 * Created by Vinoth on 28-5-16.
 */
public class Firealm implements WriteListener {

    public static class Builder {

        protected Firealm firealm;

        public Builder(Context context, Object defaultRealmModule) {
            firealm = new Firealm(context, defaultRealmModule);
        }

        public Builder addFirebaseReferencePath(Class<? extends FirealmModel> cls, String firebaseReferencePath) {
            firealm.getMap().put(cls, firebaseReferencePath);
            return this;
        }

        /*public Builder addRealmModules() {

            return this;
        }*/

        public Firealm build() {
            firealm.setRealmInstance();
            return firealm;
        }

    }

    private Firealm(Context context, Object defaultRealmModule) {
        setAndroidContext(context);
        setDefaultRealmModule(defaultRealmModule);
        FirebaseBridge.setFirealm(this);
        RealmBridge.setFirealm(this);
        setMap(new FirealmMap());
    }

    private FirealmMap map;
    private ArrayList<WriteListener> listeners;

    public FirealmMap getMap() {
        return map;
    }

    public void setMap(FirealmMap map) {
        this.map = map;
    }

    public DatabaseReference getDatabaseReference(Class<? extends FirealmModel> cls) {
        return getFirebaseInstance().getReference(getMap().get(cls));
    }

    protected ArrayList<WriteListener> getListeners() {
        return listeners;
    }

    protected void setListeners(ArrayList<WriteListener> listeners) {
        this.listeners = listeners;
    }

    private Context androidContext;

    protected Context getAndroidContext() {
        return androidContext;
    }

    private void setAndroidContext(Context context) {
        androidContext = context;
    }

    private Object defaultRealmModule;

    protected Object getDefaultRealmModule() {
        return defaultRealmModule;
    }

    protected void setDefaultRealmModule(Object defaultRealmModule) {
        this.defaultRealmModule = defaultRealmModule;
    }

    private io.realm.Realm realmInstance;

    public io.realm.Realm getRealmInstance() {
        return realmInstance;
    }

    protected void setRealmInstance() {
        RealmConfiguration configuration = new RealmConfiguration.Builder(getAndroidContext())
                .name("firealm.realm")
                .modules(getDefaultRealmModule(), new FirealmModule())
                .build();
        realmInstance = io.realm.Realm.getInstance(configuration);
    }

    public FirebaseDatabase getFirebaseInstance() {
        return FirebaseDatabase.getInstance();
    }

    public RealmAsyncTask writeAsync(@NonNull final FirealmModel model, @Nullable final WriteListener listener) {
        setListeners(new ArrayList<WriteListener>());
        getListeners().add(model);
        getListeners().add(listener);

        RealmBridge realmBridge = new RealmBridge(model);
        return realmBridge.writeAsync(this);
    }

    @Override
    public void onRealmWrite(FirealmModel model) {
        FirebaseBridge firebaseBridge = new FirebaseBridge(model);
        if (firebaseBridge.getDatabaseReference() == null) {
            DatabaseError databaseError = DatabaseError.fromException(new Throwable("Firebase reference path not exist"));
            onFirebaseError(model, databaseError);
        } else
            firebaseBridge.write(this);
        for (WriteListener listener : getListeners()) {
            listener.onRealmWrite(model);
        }
    }

    @Override
    public void onFirebaseWrite(FirealmModel model) {
        for (WriteListener listener : getListeners()) {
            listener.onFirebaseWrite(model);
        }
    }

    @Override
    public void onFirebaseError(FirealmModel model, DatabaseError error) {
        for (WriteListener listener : getListeners()) {
            listener.onFirebaseError(model, error);
        }
    }

    @Override
    public void onRealmError(FirealmModel model, Throwable error) {
        for (WriteListener listener : getListeners()) {
            listener.onRealmError(model, error);
        }
    }
}
package org.firealm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

/**
 * Created by Vinoth on 28-5-16.
 */
public class Firealm {

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
        setMap(new FirealmMap());
    }

    private FirealmMap map;

    public FirealmMap getMap() {
        return map;
    }

    public void setMap(FirealmMap map) {
        this.map = map;
    }

    public DatabaseReference getDatabaseReference(Class<? extends FirealmModel> cls) {
        return getFirebaseInstance().getReference(getMap().get(cls));
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
        Realm realm = new Realm(model);
        final Firebase firebase = new Firebase(model);
        return realm.writeAsync(new org.firealm.realm.WriteListener() {
            @Override
            public void writtenOnRealm() {
                if (listener != null)
                    listener.writtenOnRealm();
                if (firebase.getDatabaseReference() == null) {
                    if (listener != null)
                        listener.errorOnFirebase(DatabaseError.fromException(new Throwable("Firebase reference path not exist")));
                } else
                    firebase.write(listener);
            }

            @Override
            public void errorOnRealm(Throwable error) {
                listener.errorOnRealm(error);
            }
        });
    }

    public class Realm extends AbstractFirealm implements io.realm.Realm.Transaction {

        public Realm(FirealmModel model) {
            super(model);
        }

        @Override
        public void execute(io.realm.Realm realm) {
            RealmObject object = (RealmObject) getModel();
            realm.copyToRealmOrUpdate(object);
        }

        public void write() {
            getRealmInstance().executeTransaction(this);
        }

        public RealmAsyncTask writeAsync(final org.firealm.realm.WriteListener listener) {
            return getRealmInstance().executeTransactionAsync(this, new OnSuccess() {
                @Override
                public void onSuccess() {
                    listener.writtenOnRealm();
                }
            }, new OnError() {
                @Override
                public void onError(Throwable error) {
                    listener.errorOnRealm(error);
                }
            });
        }

    }

    public class Firebase extends AbstractFirealm {

        public Firebase(FirealmModel model) {
            super(model);
            setDatabaseReference(Firealm.this.getDatabaseReference(getModel().getClass()).child(getProperty().getKey()));
        }

        private DatabaseReference databaseReference;

        public DatabaseReference getDatabaseReference() {
            return databaseReference;
        }

        public void setDatabaseReference(DatabaseReference databaseReference) {
            this.databaseReference = databaseReference;
        }

        protected void write(DatabaseReference.CompletionListener listener) {
            if (getProperty().getPriority() == null)
                getDatabaseReference().setValue(getModel(), getProperty().getPriority(), listener);
            else
                getDatabaseReference().setValue(getModel(), listener);
        }

        public void write(final org.firealm.firebase.WriteListener listener) {
            write(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        if (listener != null)
                            listener.writtenOnFirebase();
                    } else {
                        if (listener != null)
                            listener.errorOnFirebase(databaseError);
                    }
                }
            });
        }
    }

}
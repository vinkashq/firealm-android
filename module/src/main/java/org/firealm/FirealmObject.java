package org.firealm;

import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Vinoth on 27-5-16.
 */
public class FirealmObject<H extends RealmObject> {

    H h;

    public FirealmObject(H h) {
        setRealm(new RealmData());
        this.h = h;
    }

    @Exclude
    public static Realm getLocal() {
        return Firealm.getRealm();
    }

    @Ignore
    private static DatabaseReference cloud;

    @Exclude
    public static DatabaseReference getCloud() {
        return cloud;
    }

    public static void setCloud(DatabaseReference databaseReference) {
        cloud = databaseReference;
    }

    public static void setFirebaseReferencePath(String path) {
        cloud = Firealm.getFirebase().getReference(path);

    }

    @PrimaryKey
    @Required
    private String firebaseKey;

    private String priority;

    public FirealmObject() {
        setRealm(new RealmData());
    }

    public RealmAsyncTask writeAsync() {
        return writeAsync(null);
    }

    public RealmAsyncTask writeAsync(Object priority) {
        getFirebase().setPriority(priority);
        return writeAsync();
    }

    public RealmAsyncTask writeAsync(@Nullable final WriteListener listener) {
        return getRealm().writeAsync(new org.firealm.realm.WriteListener() {
            @Override
            public void writtenOnRealm() {
                if (listener != null)
                    listener.writtenOnRealm();
                if (getCloud() == null) {
                    if (listener != null)
                        listener.errorOnFirebase(DatabaseError.fromException(new Throwable("Firebase reference path not exist")));
                } else
                    getFirebase().write(listener);
            }

            @Override
            public void errorOnRealm(Throwable error) {
                listener.errorOnRealm(error);
            }
        });
    }

    public RealmAsyncTask writeAsync(@Nullable final WriteListener listener, Object priority) {
        getFirebase().setPriority(priority);
        return writeAsync(listener);
    }

    @Ignore
    private FirebaseData firebase;

    @Exclude
    public FirebaseData getFirebase() {
        return firebase;
    }

    protected void setFirebase(FirebaseData cloud) {
        this.firebase = cloud;
    }

    public void setKey(String key) {
        setFirebase(new FirebaseData(key));
    }

    @Ignore
    private RealmData realm;

    @Exclude
    public RealmData getRealm() {
        return realm;
    }

    public void setRealm(RealmData realm) {
        this.realm = realm;
    }

    public class RealmData implements Realm.Transaction {

        public String getPriority() {
            return priority;
        }

        @Override
        public void execute(Realm realm) {
            realm.copyToRealmOrUpdate(h);
        }

        public void write() {
            getLocal().executeTransaction(this);
        }

        public RealmAsyncTask writeAsync(final org.firealm.realm.WriteListener listener) {
            return getLocal().executeTransactionAsync(this, new OnSuccess() {
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

    public class FirebaseData {

        public FirebaseData(String key) {
            setKey(key);
            databaseReference = getCloud().child(key);
        }

        private Object priority;
        private DatabaseReference databaseReference;

        public DatabaseReference getDatabaseReference() {
            return databaseReference;
        }

        public void setDatabaseReference(DatabaseReference databaseReference) {
            this.databaseReference = databaseReference;
        }

        public String getKey() {
            return firebaseKey;
        }

        public void setKey(String key) {
            firebaseKey = key;
        }

        public Object getPriority() {
            return priority;
        }

        public void setPriority(Object priority) {
            this.priority = priority;
            FirealmObject.this.priority = priority.toString();
        }

        protected void write(DatabaseReference.CompletionListener listener) {
            if (getPriority() == null)
                getDatabaseReference().setValue(FirealmObject.this, getPriority(), listener);
            else
                getDatabaseReference().setValue(FirealmObject.this, listener);
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
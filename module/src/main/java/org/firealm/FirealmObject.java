package org.firealm;

import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Vinoth on 27-5-16.
 */
public class FirealmObject extends RealmObject {

    public FirealmObject() {
    }

    public static Firealm getFirealm() {
        return Firealm.getInstance();
    }

    @PrimaryKey
    @Required
    private String key;

    @Exclude
    public String getKey() {
        return key;
    }

    protected void setKey(String key) {
        this.key = key;
    }

    private String priority;

    @Exclude
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Exclude
    protected FirealmObject getFirealmObject(Realm realm) {
        return realm.copyToRealm(this);
    }

    @Ignore
    private DatabaseReference databaseReference;

    @Exclude
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    protected void setDatabaseReference(DatabaseReference reference) {
        databaseReference = reference;
    }

    public void setFirebaseReferencePath(String referencePath, String key) {
        setDatabaseReference(getFirealm().getFirebaseDatabase().getReference(referencePath).child(key));
    }

    public RealmAsyncTask writeAsync() {
        return writeAsync(null);
    }

    public RealmAsyncTask writeAsync(@Nullable final CompletionListener listener) {
        return getFirealm().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                writeTo(realm);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (listener != null)
                    listener.writtenOnRealm();
                if (getDatabaseReference() == null) {
                    if (listener != null)
                        listener.errorOnFirebase(DatabaseError.fromException(new Throwable("Firebase reference path not exist")));
                } else
                    writeToFirebase(new DatabaseReference.CompletionListener() {
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
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (listener != null)
                    listener.errorOnRealm(error);
            }
        });
    }

    protected void writeToFirebase(DatabaseReference.CompletionListener listener) {
        if (getPriority() == null)
            getDatabaseReference().setValue(this, getPriority(), listener);
        else
            getDatabaseReference().setValue(this, listener);
    }

    protected void writeTo(Realm realm) {
        getFirealmObject(realm);
    }

}
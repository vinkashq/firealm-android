package com.vinkas.firebase;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.vinkas.firealm.AbstractBridge;
import com.vinkas.firealm.model.FirealmModel;
import com.vinkas.realm.model.Firebase;

/**
 * Created by Vinoth on 2-6-16.
 */
public class FirebaseBridge extends AbstractBridge {

    public FirebaseBridge(FirealmModel model) {
        super(model);
    }

    @Override
    protected void setModel(FirealmModel model) {
        super.setModel(model);
        setFirebase(model.firebase());
    }

    private FirealmModel model;
    private Firebase firebase;
    private DatabaseReference databaseReference;

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public FirealmModel getModel() {
        return model;
    }

    public Firebase getFirebase() {
        return firebase;
    }

    public void setFirebase(Firebase value) {
        firebase = value;
        setDatabaseReference(getFirealm().getDatabaseReference(getModel().getClass()).child(firebase.getKey()));
    }

    protected void write(DatabaseReference.CompletionListener listener) {
        if (getFirebase().getPriority() == null)
            getDatabaseReference().setValue(getModel(), getFirebase().getPriority(), listener);
        else
            getDatabaseReference().setValue(getModel(), listener);
    }

    public void write(final com.vinkas.firebase.WriteListener listener) {
        write(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    if (listener != null)
                        listener.onFirebaseWrite(getModel());
                } else {
                    if (listener != null)
                        listener.onFirebaseError(getModel(), databaseError);
                }
            }
        });
    }

}

package com.vinkas.realm;

import com.vinkas.firealm.AbstractBridge;
import com.vinkas.firealm.model.FirealmModel;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmObject;

/**
 * Created by Vinoth on 2-6-16.
 */
public class RealmBridge extends AbstractBridge implements Realm.Transaction {

    public RealmBridge(FirealmModel model) {
        super(model);
    }

    @Override
    public void execute(Realm realm) {
        RealmObject object = (RealmObject) getModel();
        realm.copyToRealmOrUpdate(object);
    }

    public void write() {
        getFirealm().getRealmInstance().executeTransaction(this);
    }

    public RealmAsyncTask writeAsync(final com.vinkas.realm.WriteListener listener) {
        return getFirealm().getRealmInstance().executeTransactionAsync(this, new OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onRealmWrite(getModel());
            }
        }, new OnError() {
            @Override
            public void onError(Throwable error) {
                listener.onRealmError(getModel(), error);
            }
        });
    }

}
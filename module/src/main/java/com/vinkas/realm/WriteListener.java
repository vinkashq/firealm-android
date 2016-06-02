package com.vinkas.realm;

import com.vinkas.firealm.model.FirealmModel;

/**
 * Created by Vinoth on 28-5-16.
 */
public interface WriteListener {
    void onRealmWrite(FirealmModel model);
    void onRealmError(FirealmModel model, Throwable error);
}

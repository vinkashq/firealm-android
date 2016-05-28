package org.firealm.realm;

/**
 * Created by Vinoth on 28-5-16.
 */
public interface WriteListener {
    void writtenOnRealm();
    void errorOnRealm(Throwable error);
}

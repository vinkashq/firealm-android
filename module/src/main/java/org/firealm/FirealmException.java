package org.firealm;

/**
 * Created by Vinoth on 27-5-16.
 */
public class FirealmException extends Exception {

    public FirealmException() {
    }

    public FirealmException(String detailMessage) {
        super(detailMessage);
    }

    public FirealmException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public FirealmException(Throwable throwable) {
        super(throwable);
    }
}

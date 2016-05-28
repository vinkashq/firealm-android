package org.firealm.test;

import org.firealm.FirealmObject;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by Vinoth on 27-5-16.
 */
@RealmClass
public class Book implements RealmModel {

    public RealmModel get() {
        return this;
    }

    private String title;
    private String authorName;
    private float price;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

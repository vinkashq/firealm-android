package org.firealm.test;

import org.firealm.FirealmObject;

/**
 * Created by Vinoth on 27-5-16.
 */
public class Book extends FirealmObject {

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

# Firealm for Android

[![](https://jitpack.io/v/org.firealm/firealm-android.svg)](https://jitpack.io/#org.firealm/firealm-android)
[![License: MIT](https://img.shields.io/badge/License-MIT-orange.svg)](https://opensource.org/licenses/MIT)
<hr />

### Firebase + Realm => Firealm
Manage both remote (Firebase) &amp; local (Realm) database

##Add as dependency module
Add this on your project's root 'build.gradle' file
```
  repositories { 
        jcenter()
        maven { url "https://jitpack.io" }
   }
```
Add this on your app folder's 'build.gradle' file
```
  dependencies {
        compile 'org.firealm:firealm-android:0.9.1'
  }
```
#How to use
### Java code
##### Firealm Object Model
```
public class Book extends RealmObject implements FirealmModel {

    @PrimaryKey
    private String id;

    private FirealmProperty property;

    @Override
    public FirealmProperty firealmProperty() {
        property = new FirealmProperty();
        property.setKey(getId());
        return property;
    }

    private String title;
    private String authorName;
    private float price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
```
##### Writing on Realm and Firebase databases
```
        Firealm firealm = new Firealm.Builder(getApplicationContext(), Realm.getDefaultModule())
                .addFirebaseReferencePath(Book.class, "/list/path/books")
                .build();
        Book book = new Book();
        book.setId("book002");
        book.setTitle("Agni Siragugal");
        book.setAuthorName("A. P. J. Abdul Kalam");
        book.setPrice(140);
        firealm.writeAsync(book, new WriteListener() {
            @Override
            public void writtenOnRealm() {
                Log.d("Book", "Book 'Agni Siragugal' written on Realm");
            }

            @Override
            public void writtenOnFirebase() {
                Log.d("Book", "Book 'Agni Siragugal' written on Firebase's remote database");
            }

            @Override
            public void errorOnRealm(Throwable error) {
                error.printStackTrace();
                Log.d("Book", "Error while writing the book 'Agni Siragugal' on Realm. " + error.getMessage());
            }

            @Override
            public void errorOnFirebase(DatabaseError error) {
                error.toException().printStackTrace();
                Log.d("Book", "Error while writing the book 'Agni Siragugal' on Firebase " + error.getMessage());
            }
        });
```

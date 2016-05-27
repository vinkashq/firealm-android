# Firealm for Android

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
        compile 'org.firealm:firealm-android:1.0.0'
  }
```
#How to use
### Java code
##### Firealm Object Model
```
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
```
##### Writing on Realm and Firebase databases
```
        Book book = new Book();
        book.setTitle("Agni Siragugal");
        book.setAuthorName("A. P. J. Abdul Kalam");
        book.setPrice(140);
        book.setFirebaseReferencePath("child/path/books", "book001");
        book.writeAsync(new CompletionListener() {
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
                Log.d("Book", "Error while writing the book 'Agni Siragugal' on Realm");
            }

            @Override
            public void errorOnFirebase(DatabaseError error) {
                Log.d("Book", "Error while writing the book 'Agni Siragugal' on Firebase");
            }
        });
```

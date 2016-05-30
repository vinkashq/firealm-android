package org.firealm.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseError;

import org.firealm.Firealm;
import org.firealm.FirealmObject;
import org.firealm.WriteListener;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firealm.defaultRealmModule = Realm.getDefaultModule();
        Firealm.setAndroidContext(getApplicationContext());
        Book book = new Book();
        book.setPrimaryKey("aaa");
        book.setTitle("Agni Siragugal");
        book.setAuthorName("A. P. J. Abdul Kalam");
        book.setPrice(140);
        FirealmObject.setFirebaseReferencePath("list/path/books");
        FirealmObject<Book> firealmObject = new FirealmObject<>(book);
        firealmObject.writeAsync(new WriteListener() {
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
    }
}

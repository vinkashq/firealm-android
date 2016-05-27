package org.firealm.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.vinkas.firealm.test.R;

import org.firealm.CompletionListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Book book = new Book();
        book.setTitle("Agni Siragugal");
        book.setAuthorName("A. P. J. Abdul Kalam");
        book.setPrice(140);
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
    }
}

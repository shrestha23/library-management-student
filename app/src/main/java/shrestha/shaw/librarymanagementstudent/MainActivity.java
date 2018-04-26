package shrestha.shaw.librarymanagementstudent;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    EditText search;
    ImageButton submitButton;
    private DatabaseReference bookref;
    private String searchedBook;
    TextView loginLink;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitButton = findViewById(R.id.search_btn);
        search  = findViewById(R.id.searchid_field);
        loginLink = findViewById(R.id.loginlink);
        bookref  = FirebaseDatabase.getInstance().getReference("Books");

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchedBook = search.getText().toString();
                if(searchedBook.isEmpty()){
                    Toast.makeText(MainActivity.this, "EMPTY SEARCHED FIELD", Toast.LENGTH_SHORT).show();
                }
                if(!searchedBook.isEmpty()){
                    bookref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(searchedBook) ){
                                Intent intent1 = new Intent(MainActivity.this, BookDetailsActivity.class);
                                intent1.putExtra("book", searchedBook);
                                startActivity(intent1);
                            } else {
                                Toast.makeText(MainActivity.this, "THIS BOOK IS NOT AVAILABLE", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "DATABASE ERROR", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity( new Intent( MainActivity.this , SignInActivity.class));
            }
        });

    }

}




package shrestha.shaw.librarymanagementstudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookDetailsActivity extends AppCompatActivity {
    DatabaseReference databaseReference ;
    String isbn ;
    String stuid;
    TextView bookTitle;
    TextView bookAuthor;
    TextView bookDesc;
    TextView bookCopies;
    Button requestBook;
    FirebaseAuth auth;
    DatabaseReference userref;
    String  currentDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);




                    isbn = getIntent().getStringExtra("book");
                    stuid = getIntent().getStringExtra("s");


                bookTitle = findViewById(R.id.book_title);
        bookAuthor = findViewById(R.id.book_author);
        bookDesc = findViewById(R.id.book_description);
        bookCopies = findViewById(R.id.book_copies);

        requestBook = findViewById(R.id.request_book);
        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Books");



        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        userref = FirebaseDatabase.getInstance().getReference("User");

    }

    @Override
    protected void onStart() {
        super.onStart();



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(isbn)) {
                    Book book = dataSnapshot.child(isbn).getValue(Book.class);

                    if(book != null) {
                        bookTitle.setText(book.getTitle());
                        bookAuthor.setText(book.getAuthor());
                        bookDesc.setText(book.getDescription());
                        bookCopies.setText(String.valueOf(book.getCopies()));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        if (auth.getCurrentUser()==null){
            requestBook.setEnabled(false);
            Toast.makeText(this, "PLEASE LOGIN TO REQUEST THIS BOOK", Toast.LENGTH_SHORT).show();
        }else {
            requestBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final DatabaseReference requstref = FirebaseDatabase.getInstance().getReference("Requestbook");

                    requstref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(stuid)){
                                 long ccount = dataSnapshot.getChildrenCount();
                                if (dataSnapshot.child(stuid).hasChild(isbn)){
                                   Toast.makeText(BookDetailsActivity.this, "THIS BOOK IS ALREADY REQUESTED!!", Toast.LENGTH_SHORT).show();
                                }else {


                                    Map<String, Object> userHashMap =  new HashMap<String , Object>();
                                    userHashMap.put(isbn,currentDate);
                                    requstref.child(stuid).updateChildren(userHashMap);
                                    Toast.makeText(BookDetailsActivity.this, "REQUEST BOOK SUCCESSFUL ", Toast.LENGTH_SHORT).show();

                                }
                            }else {

                                Map<String, Object> userData = new HashMap<String, Object>();
                                userData.put(isbn,currentDate);
                                requstref.child(stuid).setValue(userData);
                                Toast.makeText(BookDetailsActivity.this, "REQUEST BOOK SUCCESSFUL ", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }


    }
}


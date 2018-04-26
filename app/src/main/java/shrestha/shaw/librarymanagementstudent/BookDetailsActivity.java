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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDetailsActivity extends AppCompatActivity {
    DatabaseReference databaseReference ;
    String isbn;
    TextView bookTitle;
    TextView bookAuthor;
    TextView bookDesc;
    TextView bookCopies;
    Button requestBook;
    FirebaseAuth auth;
    EditText libraryId;
    ImageButton tickBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        isbn = getIntent().getStringExtra("book");

        bookTitle = findViewById(R.id.book_title);
        bookAuthor = findViewById(R.id.book_author);
        bookDesc = findViewById(R.id.book_description);
        bookCopies = findViewById(R.id.book_copies);

        requestBook = findViewById(R.id.request_book);
        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

        libraryId.setVisibility(View.GONE);
        tickBtn.setVisibility(View.GONE);
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

        if (auth.getCurrentUser()!= null){
            requestBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

      /*  requestBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (auth.getCurrentUser() == null ){
                    startActivity(new Intent(BookDetailsActivity.this ,SignInActivity.class));
                    finish();
                }else {
                    requestBook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(BookDetailsActivity.this, "Not Implemented", Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });*/
    }
}

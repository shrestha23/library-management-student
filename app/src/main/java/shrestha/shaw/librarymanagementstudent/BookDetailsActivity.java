package shrestha.shaw.librarymanagementstudent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDetailsActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    String isbn;
    TextView bookTitle;
    TextView bookAuthor;
    TextView bookDesc;
    TextView bookCopies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        isbn = getIntent().getStringExtra("isbn");

        bookTitle = findViewById(R.id.book_title);
        bookAuthor = findViewById(R.id.book_author);
        bookDesc = findViewById(R.id.book_description);
        bookCopies = findViewById(R.id.book_copies);

        databaseReference = FirebaseDatabase.getInstance().getReference("Books");
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
    }
}

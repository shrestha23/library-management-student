package shrestha.shaw.librarymanagementstudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchActivity extends AppCompatActivity {
    String librarianId , bookIsbn ;
    ImageButton searchBook;
    EditText isbnField;
    TextView developer_version_text;
    private DatabaseReference bookSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        isbnField = findViewById(R.id.search_isbn);
        searchBook = findViewById(R.id.search_book_by_isbn);
        bookSearch = FirebaseDatabase.getInstance().getReference("Books");
        developer_version_text = findViewById(R.id.developer_version_text1);
        librarianId = getIntent().getStringExtra("librarianId");
        developer_version_text.setText(librarianId);


    }

    @Override
    protected void onStart() {
        super.onStart();
        searchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookIsbn = isbnField.getText().toString();
                if(bookIsbn.isEmpty()){
                    Toast.makeText(SearchActivity.this, "EMPTY ISBN FIELD ", Toast.LENGTH_SHORT).show();
                }
                if(!bookIsbn.isEmpty()) {
                    bookSearch.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(bookIsbn)){
                                Intent intent = new Intent(SearchActivity.this, BookDetailsActivity.class);
                                intent.putExtra("isbn",bookIsbn);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SearchActivity.this, "THIS BOOK IS NOT AVAILABLE ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(SearchActivity.this, "DATABASE ERROR", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });
    }
}

package shrestha.shaw.librarymanagementstudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class SearchActivity extends AppCompatActivity {
    String librarianId;
    ImageButton searchBook;
    EditText isbnField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        isbnField = findViewById(R.id.search_isbn);
        searchBook = findViewById(R.id.search_book_by_isbn);
        librarianId = getIntent().getStringExtra("librarianId");
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isbnField.getText().toString().isEmpty()) {
                    Intent intent = new Intent(SearchActivity.this, BookDetailsActivity.class);
                    intent.putExtra("isbn",isbnField.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}

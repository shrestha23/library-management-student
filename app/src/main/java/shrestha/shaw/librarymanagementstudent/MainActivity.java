package shrestha.shaw.librarymanagementstudent;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText librarianIdField;
    ImageButton submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitButton = findViewById(R.id.search_btn);
        librarianIdField = findViewById(R.id.library_id_field);

    }

    @Override
    protected void onStart() {
        super.onStart();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(librarianIdField.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "NOT VALID ENTER ISBN", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra("librarianId", librarianIdField.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}

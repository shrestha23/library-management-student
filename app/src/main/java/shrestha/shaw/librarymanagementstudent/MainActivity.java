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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText librarianIdField;
    ImageButton submitButton;
    private DatabaseReference studentRef;
    private String studentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitButton = findViewById(R.id.search_btn);
        librarianIdField = findViewById(R.id.library_id_field);
        studentRef = FirebaseDatabase.getInstance().getReference("Students");

    }

    @Override
    protected void onStart() {
        super.onStart();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentId = librarianIdField.getText().toString();
                if(studentId.isEmpty()){
                    Toast.makeText(MainActivity.this, "EMPTY LIBRARY ID FIELD", Toast.LENGTH_SHORT).show();
                }
                if(!studentId.isEmpty()){
                    studentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(studentId)){
                                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                                intent.putExtra("librarianId", studentId);
                                startActivity(intent);
                            }else {
                                Toast.makeText(MainActivity.this, "INVALID LIBRARY ID", Toast.LENGTH_SHORT).show();
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

    }

}




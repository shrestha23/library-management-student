package shrestha.shaw.librarymanagementstudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentActivity extends AppCompatActivity {

    EditText searchBar;
TextView studentname , department ;
    TextView  stuId ,   signOut;
Button submitButton;
Button rentBtn;
String search;
DatabaseReference ref;
FirebaseAuth auth;
FirebaseUser firebaseUser;
String sid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        searchBar = findViewById(R.id.searchbar);
        submitButton = findViewById(R.id.submitbuton);
        rentBtn = findViewById(R.id.rentbtn);

        studentname = findViewById(R.id.name);
        department =findViewById(R.id.accesslevel);
        stuId = findViewById(R.id.stuId);
        signOut =findViewById(R.id.signout);
        ref = FirebaseDatabase.getInstance().getReference("Books");
        auth = FirebaseAuth.getInstance();


        if (auth.getCurrentUser()!=null) {
            firebaseUser = auth.getCurrentUser();
            final String uid = firebaseUser.getUid();
            DatabaseReference profile;
            profile = FirebaseDatabase.getInstance().getReference("User");
            profile.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(uid)) {
                        User user = dataSnapshot.child(uid).getValue(User.class);


                        if (user!=null) {

                            department.setText(user.getAcesslevel());
                            stuId.setText(String.valueOf(user.getId()));
                            studentname.setText(user.getStuname());
                            sid = String.valueOf(user.getId());
                        }
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search = searchBar.getText().toString();
                if(search.isEmpty()){
                    Toast.makeText(StudentActivity.this, "Empty Search Field", Toast.LENGTH_SHORT).show();
                }
                if(!search.isEmpty()){
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(search)){

                                Intent intent = new Intent(StudentActivity.this, BookDetailsActivity.class);
                                intent.putExtra("book",  search);
                                intent.putExtra("s", sid);
                                startActivity(intent);
                            }else {
                                Toast.makeText(StudentActivity.this, "THIS BOOK IS NOT AVAILABLE", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(StudentActivity.this, "Database Error", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }

            });

        rentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this , RentedActivity.class);
                intent.putExtra("studentid",sid );
                startActivity(intent);
            }
        });



        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                startActivity(new Intent(StudentActivity.this , MainActivity.class));
                finish();
            }


        });
    }

}

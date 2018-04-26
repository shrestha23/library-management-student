package shrestha.shaw.librarymanagementstudent;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentActivity extends AppCompatActivity {

    EditText searchBar;
TextView  name1 , department , signOut;
ImageButton submitButton;
Button rentBtn , requestBtn;
String search;
DatabaseReference ref;
FirebaseAuth auth;
FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        searchBar = findViewById(R.id.searchbar);
        submitButton = findViewById(R.id.submitbutton);
        rentBtn = findViewById(R.id.rentbtn);
        requestBtn = findViewById(R.id.requestbtn);
        name1 = findViewById(R.id.name);
        department =findViewById(R.id.department);
        signOut =findViewById(R.id.signout);
        ref = FirebaseDatabase.getInstance().getReference("Books");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
if (user!=null){

    String userId = user.getUid();
    FirebaseDatabase ref;

}

/*
        if (user!=null){

           final   String userUid = user.getUid();
        //

             final DatabaseReference ref ;
            ref = FirebaseDatabase.getInstance().getReference("User");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(userUid)){
                        User userr = dataSnapshot.child(userUid).getValue(User.class);

                        if (userr!= null){
                            name1.setText(userr.getName());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            String userEmail = user.getEmail();

             department.setText(userEmail);


        }*/


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
                                intent.putExtra("book", search);
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
                startActivity(new Intent(StudentActivity.this , RentedActivity.class));
            }
        });

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this , RequestActivity.class));
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

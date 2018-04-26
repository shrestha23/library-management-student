package shrestha.shaw.librarymanagementstudent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    EditText emailField, passwordField;
    Button loginButton;
    FirebaseAuth auth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);
        loginButton = findViewById(R.id.loginbtn);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(SignInActivity.this, StudentActivity.class));
            finish();
        }


       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String email = emailField.getText().toString();
               String password = passwordField.getText().toString();

               if (TextUtils.isEmpty(email)) {
                   Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                   return;
               }

               if (TextUtils.isEmpty(password)) {
                   Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                   return;
               }

               auth.signInWithEmailAndPassword(email , password ).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {


                       if(!task.isSuccessful()){

                           Toast.makeText(SignInActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                       }else {
                           startActivity(new Intent(SignInActivity.this ,StudentActivity.class));
                           finish();
                       }
                   }
               });
           }
       });
    }

    }


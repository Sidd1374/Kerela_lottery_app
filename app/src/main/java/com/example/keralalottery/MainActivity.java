package com.example.keralalottery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText Pwd_lgn, Email_lgn;
    Button lgn_btn;
    TextView sign_pg;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();   // auth obj
        Pwd_lgn = findViewById(R.id.editText); // Pwd EditText
        Email_lgn = findViewById(R.id.LoginNum);  // Email EditText
        sign_pg = findViewById(R.id.textView4); // Sign Up TextView
        lgn_btn = findViewById(R.id.button);     // Login Button

        // Set a click listener for the Login button
        lgn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting the Username and Mobile Number Entered
                String pwd_lgn = Pwd_lgn.getText().toString();
                String email_id = Email_lgn.getText().toString();

                if (TextUtils.isEmpty(email_id) || TextUtils.isEmpty(pwd_lgn)){
                    Toast.makeText(MainActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email_id , pwd_lgn);
                }

                // Check for the Username in the Database
                // You can add your database checking logic here

            }
        });

        // Function to Move to Sign in Window
        sign_pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , SignUp.class));
                finish();
            }
        });
    }
    private void loginUser(String email,String password){
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this , "Login Successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this , Activity2.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle login failure, for example, showing a toast for invalid credentials.
                Toast.makeText(MainActivity.this, "Invalid Credentials. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

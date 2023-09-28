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
import java.util.Random;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.util.Random;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference reference= db.getReference("Ticket-Final");
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
        String accid = Email_lgn.getText().toString();


        // Set a click listener for the Login button
        lgn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting the Username and Mobile Number Entered
                String pwd_lgn = Pwd_lgn.getText().toString();
                String email_id = Email_lgn.getText().toString();

                if (TextUtils.isEmpty(email_id) || TextUtils.isEmpty(pwd_lgn)) {
                    Toast.makeText(MainActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email_id, pwd_lgn);
                }

//                int[] uniqueRandomNumbers = new int[1000]; // Change the size as needed
//
//                // Create a Random object to generate random numbers
//                Random random = new Random();
//
//                // Generate and store unique random numbers
//                int count = 0;
//                while (count < uniqueRandomNumbers.length) {
//                    int randomNumber = random.nextInt(900000) + 100000; // Generate 6-digit random number
//
//                    // Check if the generated number is unique
//                    boolean isUnique = true;
//                    for (int i = 0; i < count; i++) {
//                        if (uniqueRandomNumbers[i] == randomNumber) {
//                            isUnique = false;
//                            break;
//                        }
//                    }
//
//                    // If the number is unique, add it to the array
//                    if (isUnique) {
//                        uniqueRandomNumbers[count] = randomNumber;
//                        count++;
//                    }
//                }
//
//                // Print the unique random numbers
//                System.out.println("Unique Random Numbers:");
//                for (int num : uniqueRandomNumbers) {
//                    reference.child(String.valueOf(num)).setValue(num);
//                }
//
//
//                // Check for the Username in the Database
//                // You can add your database checking logic here
//
//
//                // Define the array to store unique random numbers
//
//
//            }
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

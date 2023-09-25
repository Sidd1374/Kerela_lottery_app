package com.example.keralalottery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    TextView toLogin ;
    EditText inName ,email ,pwd , pwdCfm, mobNo;
    Button signUpBtn;
    private FirebaseAuth mAuth;
    ProgressDialog pd;
    private FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toLogin = findViewById(R.id.textLgn);
        inName = findViewById(R.id.inputName);
        email = findViewById(R.id.RegEmail);
        pwd = findViewById(R.id.inputPwd);
        pwdCfm = findViewById(R.id.inputPwdCnfm);
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        signUpBtn = findViewById(R.id.signUpBtn);
        db = FirebaseDatabase.getInstance();
        String emailStr = email.getText().toString();
        mobNo = findViewById(R.id.inputPhno);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String name = inName.getText().toString();
                 String emailStr = email.getText().toString();
                 String inPwd = pwd.getText().toString();
                 String inPwdCfm = pwdCfm.getText().toString();
                 String ph_no = mobNo.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(emailStr)
                        || TextUtils.isEmpty(inPwd) || TextUtils.isEmpty(inPwdCfm)){
                    Toast.makeText(SignUp.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (pwd.length() < 6){
                    Toast.makeText(SignUp.this, "Password too short!", Toast.LENGTH_SHORT).show();
                    if(pwd.getText().toString() != pwd.getText().toString()){
                        Toast.makeText(SignUp.this, "Password doesn't match" , Toast.LENGTH_SHORT);
                    }
                } else {
                    registerUser(name , emailStr, ph_no , inPwd);
                }
            }
        });
            toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent directToLogin = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(directToLogin);
            }
        });
    }
    private void registerUser(final String name, final String email,final String phno, final String password) {
        mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(SignUp.this , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
//                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                    if (currentUser != null) {
//                        String userEmail = currentUser.getEmail();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("name", name);
                        map.put("email", email);
                        map.put("phNum", phno);
                        map.put("pwd",password);

                        String[] mainStr = email.split("@");
                        String emailStr = mainStr[0];
                        db.getReference().child("keralaLottery").child("userDetails").child(emailStr).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SignUp.this, "User Registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUp.this, MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(SignUp.this,"Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
        });
    }
}
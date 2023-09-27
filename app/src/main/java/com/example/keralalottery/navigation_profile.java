package com.example.keralalottery;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class navigation_profile extends Fragment {
    TextView name, number;
    EditText email;
    MainActivity mn = new MainActivity();

    String eml = String.valueOf(mn.Email_lgn);

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_navigation_profile, container, false);
        name = rootView.findViewById(R.id.userName);
        number = rootView.findViewById(R.id.mobNum);
        email = rootView.findViewById(R.id.LoginNum);


        String[] mainStr = eml.split("@");
        String emailS = "aryan";
        if(emailS != null) {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("keralaLottery").child("userDetails").child(emailS);
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // Assuming the "aryan" child exists in your database

                    if (snapshot.getValue() != null) {
                        String name1 = snapshot.child("email").getValue(String.class);
                        String username = snapshot.child("name").getValue(String.class);

                        name.setText(username);
                        email.setText(name1);
                    } else {
                        // Handle the case where the data doesn't exist
                        name.setText("Data not found");
                        email.setText("Email not found");
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle onCancelled
                    Toast.makeText(getContext(), "Error in fetching Data - " + error,Toast.LENGTH_SHORT).show();
                }
            });
        }else
        {
            Toast.makeText(getContext(),"var emailS has null value",Toast.LENGTH_SHORT).show();
        }


        return rootView;
    }
}
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class navigation_profile extends Fragment {
    TextView name, number;
    String nameTxt,phNumber;
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



        FirebaseDatabase db=FirebaseDatabase.getInstance();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();


//        DatabaseReference reference=db.getReference();
//        DataSnapshot dataSnapshot=reference.get().getResult();
//        reference=db.getReference();
//        reference.child("keralaLottery").child("userDetails").child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                Toast.makeText(getContext(),"Hi",Toast.LENGTH_SHORT).show();
//                if (task.isSuccessful()) {
//                    if (task.getResult().exists()) {
//                        nameTxt = String.valueOf(dataSnapshot.child("name").getValue());
//                        phNumber = String.valueOf(dataSnapshot.child("phNumber").getValue());
////                        name.setText(nameTxt);
////                        number.setText(phNumber);
//
//
//                    }
//                }
//            }
//        });
        String UID=user.getUid();
        String name123=readData(UID);
        //Toast.makeText(getContext(),name123,Toast.LENGTH_SHORT).show();
        //name.setText(name123);
        return rootView;
    }
        public String readData(String UID) {
            DatabaseReference reference;
            reference=FirebaseDatabase.getInstance().getReference();
            reference.child("keralaLottery").child("userDetails").child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            nameTxt = String.valueOf(dataSnapshot.child("name").getValue());
                            phNumber = String.valueOf(dataSnapshot.child("phNumber").getValue());
                            name.setText(nameTxt);
                            number.setText(phNumber);
                        }
                    }

                }});
            return nameTxt;
            }


}






package com.example.keralalottery;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;
import com.example.keralalottery.navigation_cart;

import java.util.Random;

public class navigation_profile extends Fragment {
    TextView name, number;
    String nameTxt, phNumber;
    DatabaseReference reference;
    private FirebaseDatabase database;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigation_profile, container, false);
        name = rootView.findViewById(R.id.userName);
        number = rootView.findViewById(R.id.mobNum);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String UID = user.getUid();
        String name123 = readData(UID);
        //Toast.makeText(getContext(),name123,Toast.LENGTH_SHORT).show();
        //name.setText(name123);
        return rootView;
    }

    public String readData(String UID) {
        reference = FirebaseDatabase.getInstance().getReference();
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
//                        copyDataToUserTable(ticketNumber);
                    }
                }
            }
        });
        return nameTxt;
    }
//    navigation_cart navigationCart=new navigation_cart();
//    int ticketNumber=navigationCart.getTotalTicket();
//    public void copyDataToUserTable(int ticketNumber) {
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        String userUID=currentUser.getUid();
//        database = FirebaseDatabase.getInstance();
//        DatabaseReference ticketFinalRef = database.getReference("Ticket-Final");
//        DatabaseReference userTableRef = database.getReference("userTables").child(userUID);
//        // Read data from "Ticket-Final" and copy it to the user's table
//
//        int[] uniqueRandomNumbers = new int[ticketNumber]; // Change the size as needed
//
//        // Create a Random object to generate random numbers
//        Random random = new Random();
//        // Generate and store unique random numbers
//        int count = 0;
//        while (count < uniqueRandomNumbers.length) {
//            int randomNumber = random.nextInt(900000) + 100000; // Generate 6-digit random number
//
//            // Check if the generated number is unique
//            boolean isUnique = true;
//            for (int i = 0; i < count; i++) {
//                if (uniqueRandomNumbers[i] == randomNumber) {
//                    isUnique = false;
//                    break;
//                }
//            }
//
//            // If the number is unique, add it to the array
//            if (isUnique) {
//                uniqueRandomNumbers[count] = randomNumber;
//                count++;
//            }
//        }
//
//        // Print the unique random numbers
//        System.out.println("Unique Random Numbers:");
//        for (int num : uniqueRandomNumbers) {
//            reference.child(String.valueOf(num)).setValue(num);
//            ticketFinalRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        userTableRef.setValue(num, new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                if (databaseError == null) {
//                                    System.out.println("Data copied to user's table.");
//                                } else {
//                                    System.err.println("Error copying data: " + databaseError.getMessage());
//                                }
//                            }
//                        });
//                    } else {
//                        System.err.println("Data from 'Ticket-Final' does not exist.");
//                    }
//                }
//                public void onCancelled(DatabaseError databaseError) {
//                    System.err.println("Database read cancelled: " + databaseError.getMessage());
//                }
//            });
//        }
//    }
    // Check for the Username in the Database
    // You can add your database checking logic here


    // Define the array to store unique random numbers


}



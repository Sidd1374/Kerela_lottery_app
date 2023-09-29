package com.example.keralalottery;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class navigation_cart extends Fragment {

    TextView ct10, ct50, ct70, ct80, ct500, convenienceFee, amountTextView, totalTextView;
    Button send;
    final int UPI_PAYMENT = 0;
    setData hm = new setData();


    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigation_cart, container, false);

        ct10 = rootView.findViewById(R.id.count10);
        ct50 = rootView.findViewById(R.id.count50);
        ct70 = rootView.findViewById(R.id.count70);
        ct80 = rootView.findViewById(R.id.count80);
        ct500 = rootView.findViewById(R.id.count500);
        convenienceFee = rootView.findViewById(R.id.textView14);
        amountTextView = rootView.findViewById(R.id.textView13);
        totalTextView = rootView.findViewById(R.id.ttl);
        send = rootView.findViewById(R.id.buyNow);

        ct10.setText(String.valueOf(hm.getNum10()));
        ct50.setText(String.valueOf(hm.getNum50()));
        ct70.setText(String.valueOf(hm.getNum70()));
        ct80.setText(String.valueOf(hm.getNum80()));
        ct500.setText(String.valueOf(hm.getNum500()));
        convenienceFee.setText("20");


        int totalAmount = calculateTotalAmount();
        amountTextView.setText(String.valueOf(totalAmount));
        int amountFinal = totalAmount + 20;
        totalTextView.setText(String.valueOf(amountFinal));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateUpiPayment(amountFinal);
            }
        });

        return rootView;
    }

    private void initiateUpiPayment(int amount) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "9034106135@ybl")
//                .appendQueryParameter("pn", name)
                .appendQueryParameter("mc", "")            /// 1st param - use it (it was commented on my earlier tutorial)
//                .appendQueryParameter("tid", "02125412")
                .appendQueryParameter("tr", "25584584")   /// 2nd param - use it (it was commented on my earlier tutorial)
                .appendQueryParameter("tn", "Kerala Lottery - Purchased lottery Ticket")
                .appendQueryParameter("am", String.valueOf(amount))
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (chooser.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(getContext(), getString(R.string.no_upi), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((resultCode == RESULT_OK) || (resultCode == 11)) {
                    if (data != null) {
                        String response = data.getStringExtra("response");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(response);
                        upiPaymentDataOperation(dataList);
                    } else {
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(getContext())) {
            String response = data.get(0);
            String paymentCancel = "";
            if (response == null) response = "discard";
            String status = "";
            String approvalRefNo = "";
            String responseArray[] = response.split("&");
            for (int i = 0; i < responseArray.length; i++) {
                String equalStr[] = responseArray[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                        Toast.makeText(getContext(), "Case 3", Toast.LENGTH_SHORT).show();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                        Toast.makeText(getContext(), "Case 2", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            int num = getTotalTicket();


            if (status.equals("success")) {
                Toast.makeText(getContext(), "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: " + approvalRefNo);
                copyDataToUserTable(num);


            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(getContext(), "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Transaction failed. Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private int calculateTotalAmount() {
        int nm1 = hm.getNum10();
        int nm2 = hm.getNum50();
        int nm3 = hm.getNum70();
        int nm4 = hm.getNum80();
        int nm5 = hm.getNum500();

        return (nm1 * 10) + (nm2 * 50) + (nm3 * 70) + (nm4 * 80) + (nm5 * 500);
    }
    public int getTotalTicket() {
        int nm1 = hm.getNum10();
        int nm2 = hm.getNum50();
        int nm3 = hm.getNum70();
        int nm4 = hm.getNum80();
        int nm5 = hm.getNum500();

        return nm1+nm2+nm3+nm4+nm5;
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected() && netInfo.isConnectedOrConnecting() && netInfo.isAvailable();
        }
        return false;
    }


    private FirebaseDatabase database;
    private DatabaseReference ticketFinalRef;
    private DatabaseReference userTableRef;

    public void copyDataToUserTable(int ticketNumber) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userUID = currentUser.getUid();
            database = FirebaseDatabase.getInstance();
            ticketFinalRef = database.getReference("Ticket-Final");
            userTableRef = database.getReference("userTables").child(userUID);

            generateAndCopyUniqueNumbers(ticketNumber);
        }
    }

    private void generateAndCopyUniqueNumbers(int ticketNumber) {
        int[] uniqueRandomNumbers = new int[ticketNumber];

        // Generate and store unique random numbers
        int count = 0;
        Random random = new Random();
        while (count < uniqueRandomNumbers.length) {
            int randomNumber = random.nextInt(900000) + 100000;
            // Check if the generated number is unique
            boolean isUnique = true;
            for (int i = 0; i < count; i++) {
                if (uniqueRandomNumbers[i] == randomNumber) {
                    isUnique = false;
                    break;
                }
            }
            // If the number is unique, add it to the array
            if (isUnique) {
                uniqueRandomNumbers[count] = randomNumber;
                count++;
            }
        }
        // Add unique numbers to "Ticket-Final" and user's table
        for (int num : uniqueRandomNumbers) {
            ticketFinalRef.child(String.valueOf(num)).setValue(num);
            userTableRef.child(String.valueOf(num)).setValue(num)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                System.out.println("Data copied to user's table.");
                            } else {
                                System.err.println("Error copying data: " + task.getException().getMessage());
                            }
                        }
                    });
        }
    }




//
//    private FirebaseDatabase database;
//    DatabaseReference reference;
//
//
//    int ticketNumber= getTotalTicket();
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

}
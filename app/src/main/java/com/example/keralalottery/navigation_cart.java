package com.example.keralalottery;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class navigation_cart extends Fragment {

    navigation_home hm = new navigation_home();
    TextView ct10, ct50, ct70, ct80, ct500, cvfee, amt, ttl;

    Button send;
    final int UPI_PAYMENT = 0;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigation_cart, container, false);

        ct10 = rootView.findViewById(R.id.count10);
        ct50 = rootView.findViewById(R.id.count50);
        ct70 = rootView.findViewById(R.id.count70);
        ct80 = rootView.findViewById(R.id.count80);
        ct500 = rootView.findViewById(R.id.count500);
        cvfee = rootView.findViewById(R.id.textView14);
        amt = rootView.findViewById(R.id.textView13);
        ttl = rootView.findViewById(R.id.ttl);
        setData hm = new setData();

// Get the values
        int nm1 = hm.getNum10();
        int nm2 = hm.getNum50();
        int nm3 = hm.getNum70();
        int nm4 = hm.getNum80();
        int nm5 = hm.getNum500();


        String str1, str2, str3, str4, str5;
        str1 = String.valueOf(nm1);
        str2 = String.valueOf(nm2);
        str3 = String.valueOf(nm3);
        str4 = String.valueOf(nm4);
        str5 = String.valueOf(nm5);

        ct10.setText(str1);
        ct50.setText(str2);
        ct70.setText(str3);
        ct80.setText(str4);
        ct500.setText(str5);
        cvfee.setText("₹ 20");

        send = rootView.findViewById(R.id.buyNow);
        int totalnm = (nm1* 10)+(nm2 * 50)+(nm3 * 70)+(nm4* 80)+(nm5*500);
        amt.setText("₹ "+String.valueOf(totalnm));
        int cvFee = 20;
        int finalAmount = totalnm + cvFee;
        ttl.setText("₹ "+String.valueOf(finalAmount));
        send.setOnClickListener(new View.OnClickListener() {
            String amnt = String.valueOf(finalAmount);
            @Override
            public void onClick(View view) {
                //Getting the values from the EditTexts
                String amount = amnt;
                String note = "Kerala Lottery - Purchased lottery Ticket ";
                String upiId = "sahilwadhwa06@okhdfcbank";
                payUsingUpi(amount, upiId, note);
            }
        });

        return rootView;
    }

    @SuppressLint("QueryPermissionsNeeded")
    void payUsingUpi(String Amount, String upiId, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", Amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getContext().getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);

        } else {
            Toast.makeText(getContext(),getString(R.string.no_upi),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(getContext())) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                        Toast.makeText(getContext(),"Case 3",Toast.LENGTH_SHORT).show();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                        Toast.makeText(getContext(),"Case 2",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(getContext(), "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(getContext(), "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(), "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;

    }
}
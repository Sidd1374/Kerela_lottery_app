package com.example.keralalottery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class navigation_home extends Fragment {

        private ListView listView;
        private ArrayList<Lottery> lotteryArrayList;
        private MyCustomAdapter adapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.fragment_navigation_home, container, false);

            listView = rootView.findViewById(R.id.lotteryListView);
            lotteryArrayList = new ArrayList<>();

            // Populate the lotteryArrayList with Lottery objects
            lotteryArrayList.add(new Lottery("₹10", R.drawable.rupees_10));
            lotteryArrayList.add(new Lottery("₹50", R.drawable.rupees_50));
            lotteryArrayList.add(new Lottery("₹70", R.drawable.rupees_70));
            lotteryArrayList.add(new Lottery("₹80", R.drawable.rupees_80));
            lotteryArrayList.add(new Lottery("₹500", R.drawable.rupees_500));

            adapter = new MyCustomAdapter(lotteryArrayList, getActivity());
            listView.setAdapter(adapter);

            adapter.setBackgroundColor(0,R.color.ligthBlack);
            adapter.setBackgroundColor(1,R.color.purple);
            adapter.setBackgroundColor(2,R.color.green2);
            adapter.setBackgroundColor(3,R.color.orange);
            adapter.setBackgroundColor(4,R.color.blue);
            return rootView;
        }
    }

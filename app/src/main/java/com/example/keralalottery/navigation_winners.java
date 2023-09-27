package com.example.keralalottery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class navigation_winners extends Fragment {
    private GridView gridView;
    private ArrayList<LotteryWinners> winnersArrayList;
    private CustomAdapterWinners adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_navigation_winners, container, false);

        gridView = rootView.findViewById(R.id.gridView);
        winnersArrayList = new ArrayList<>();

        // Populate the lotteryArrayList with Lottery objects
        winnersArrayList.add(new LotteryWinners(125636));
        winnersArrayList.add(new LotteryWinners(123455));
        winnersArrayList.add(new LotteryWinners(256345));
        winnersArrayList.add(new LotteryWinners(125364));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));
        winnersArrayList.add(new LotteryWinners(246365));

        adapter = new CustomAdapterWinners(winnersArrayList, getActivity());
        gridView.setAdapter(adapter);

        return rootView;
    }
}
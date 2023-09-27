package com.example.keralalottery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapterWinners extends ArrayAdapter<LotteryWinners> {
    private ArrayList<LotteryWinners> winnersArrayList;
    private Context context;

    public CustomAdapterWinners(ArrayList<LotteryWinners> winnersArrayList, Context context) {
        super(context, R.layout.winner_lottery_list, winnersArrayList);
        this.winnersArrayList = winnersArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the layout for each item
            convertView = LayoutInflater.from(context).inflate(R.layout.winner_lottery_list, parent, false);

            // Create a ViewHolder and populate it with views
            viewHolder = new ViewHolder();
            viewHolder.winnersNum = convertView.findViewById(R.id.winnersNum);

            // Set the ViewHolder as a tag for the convertView
            convertView.setTag(viewHolder);
        } else {
            // Reuse the existing ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Bind data from the LotteryWinners object to the views within the ViewHolder
        LotteryWinners lotteryWinners = getItem(position);
        if (lotteryWinners != null) {
            viewHolder.winnersNum.setText(String.valueOf(lotteryWinners.getLottery_win_num()));
            ;
        }

        return convertView;
    }

    // ViewHolder pattern to improve performance by recycling views
    private static class ViewHolder {
        TextView winnersNum;
    }
}
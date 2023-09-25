package com.example.keralalottery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class MyCustomAdapter extends ArrayAdapter<Lottery> {
    private ArrayList<Lottery> lotteryArrayList;
    private Context context;
    private int[] backgroundColors; // An array to hold background colors

    public MyCustomAdapter(ArrayList<Lottery> lotteryArrayList, Context context) {
        super(context, R.layout.lottery_list_layout, lotteryArrayList);
        this.lotteryArrayList = lotteryArrayList;
        this.context = context;

        // Initialize background colors for each item
        backgroundColors = new int[lotteryArrayList.size()];
        Arrays.fill(backgroundColors, R.color.green2);
    }

    // Add a method to set the background color for a specific item
    public void setBackgroundColor(int position, int colorResId) {
        if (position >= 0 && position < backgroundColors.length) {
            backgroundColors[position] = colorResId;
            notifyDataSetChanged(); // Notify the adapter that data has changed
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.lottery_list_layout, parent, false);
            viewHolder.lotteryImage = convertView.findViewById(R.id.lotteryImage);
            viewHolder.lotteryPrize = convertView.findViewById(R.id.lotteryPrize);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set the background color for the item
        convertView.setBackgroundResource(backgroundColors[position]);

        Lottery lottery = getItem(position);
        if (lottery != null) {
            viewHolder.lotteryImage.setImageResource(lottery.getLotteryImage());
            viewHolder.lotteryPrize.setText(lottery.getLotteryPrize());
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView lotteryImage;
        TextView lotteryPrize;
    }
}
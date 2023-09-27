package com.example.keralalottery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.keralalottery.R;

public class navigation_home extends Fragment {

    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5;
    private Button button1, button2, button3, button4, button5;
    private TextView line1, line2, line3, line4, line5, line6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigation_home, container, false);

        // Initialize your views by finding their IDs within the rootView
        imageView1 = rootView.findViewById(R.id.imageView1);
        spinner1 = rootView.findViewById(R.id.spinner1);
        button1 = rootView.findViewById(R.id.button10);
        line1 = rootView.findViewById(R.id.line1);

        imageView2 = rootView.findViewById(R.id.imageView2);
        spinner2 = rootView.findViewById(R.id.spinner2);
        button2 = rootView.findViewById(R.id.button50);
        line2 = rootView.findViewById(R.id.line2);

        imageView3 = rootView.findViewById(R.id.imageView3);
        spinner3 = rootView.findViewById(R.id.spinner3);
        button3 = rootView.findViewById(R.id.button70);
        line3 = rootView.findViewById(R.id.line3);

        imageView4 = rootView.findViewById(R.id.imageView4);
        spinner4 = rootView.findViewById(R.id.spinner4);
        button4 = rootView.findViewById(R.id.button80);
        line4 = rootView.findViewById(R.id.line4);

        imageView5 = rootView.findViewById(R.id.imageView5);
        spinner5 = rootView.findViewById(R.id.spinner5);
        button5 = rootView.findViewById(R.id.button500);
        line5 = rootView.findViewById(R.id.line5);
        line6 = rootView.findViewById(R.id.line6);

        // Set up event listeners or perform other actions as needed
        // For example, you can set click listeners for the buttons to handle user interactions.
        button1.setOnClickListener(v -> {
            // Handle button1 click here
        });

        // Repeat for button2, button3, button4, button5, etc.

        return rootView;
    }
}
package com.example.keralalottery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.xmlpull.v1.XmlPullParser;

public class navigation_home extends Fragment {
    setData data = new setData();

    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5;
    private Button button1, button2, button3, button4, button5, buy;
    private int selectedNumber;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigation_home, container, false);

        // Initialize your views by finding their IDs within the rootView
        spinner1 = rootView.findViewById(R.id.spinner1);
        button1 = rootView.findViewById(R.id.button10);

        spinner2 = rootView.findViewById(R.id.spinner2);
        button2 = rootView.findViewById(R.id.button50);

        spinner3 = rootView.findViewById(R.id.spinner3);
        button3 = rootView.findViewById(R.id.button70);

        spinner4 = rootView.findViewById(R.id.spinner4);
        button4 = rootView.findViewById(R.id.button80);

        spinner5 = rootView.findViewById(R.id.spinner5);
        button5 = rootView.findViewById(R.id.button500);
        // Initialize the Spinners with data (0-10)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.spinner_values, // Create an array resource with values from 0 to 10
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);

        // Set up event listeners for the Spinners
        AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Extract the selected number from the Spinner (0-10)
                String selectedValue = parentView.getItemAtPosition(position).toString();
                int selectedNumber = Integer.parseInt(selectedValue);
                // Now, 'selectedNumber' contains the selected number (0-10)
//                if (parentView == spinner1) {
//                    data.num10 = selectedNumber;
//                } else if (parentView == spinner2) {
//                    data.num50 = selectedNumber;
//                } else if (parentView == spinner3) {
//                    data.num70 = selectedNumber;
//                } else if (parentView == spinner4) {
//                    data.num80 = selectedNumber;
//                } else if (parentView == spinner5) {
//                    data.num500 = selectedNumber;
//                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle nothing selected
            }
        };
        spinner1.setOnItemSelectedListener(spinnerListener);
        spinner2.setOnItemSelectedListener(spinnerListener);
        spinner3.setOnItemSelectedListener(spinnerListener);
        spinner4.setOnItemSelectedListener(spinnerListener);
        spinner5.setOnItemSelectedListener(spinnerListener);
        // Set up event listeners or perform other actions as needed
        // For example, you can set click listeners for the buttons to handle user interactions.
        button1.setOnClickListener(v -> {
            String value10 = spinner1.getSelectedItem().toString();
            Toast.makeText(requireContext(), value10+" tickects added to cart", Toast.LENGTH_SHORT).show();
            data.num10 = Integer.parseInt(value10);
        });
        button2.setOnClickListener(v -> {
            String value50 = spinner2.getSelectedItem().toString();
            Toast.makeText(requireContext(), value50+" tickects added to cart", Toast.LENGTH_SHORT).show();
            data.num50 = Integer.parseInt(value50);
        });
        button3.setOnClickListener(v -> {
            String value70 = spinner3.getSelectedItem().toString();
            Toast.makeText(requireContext(), value70+" tickects added to cart", Toast.LENGTH_SHORT).show();
            data.num70 = Integer.parseInt(value70);
        });
        button4.setOnClickListener(v -> {
            String value80 = spinner4.getSelectedItem().toString();
            Toast.makeText(requireContext(), value80+" tickects added to cart", Toast.LENGTH_SHORT).show();
            data.num80 = Integer.parseInt(value80);
        });
        button5.setOnClickListener(v -> {
            String value500 = spinner5.getSelectedItem().toString();
            Toast.makeText(requireContext(), value500+" tickects added to cart", Toast.LENGTH_SHORT).show();
            data.num500 = Integer.parseInt(value500);
        });
        return rootView;
    }
}
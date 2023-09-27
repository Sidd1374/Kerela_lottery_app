package com.example.keralalottery;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Activity2 extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Lottery> lotteryArrayList;
    private MyCustomAdapter adapter;
    private Button btnLogout;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        ImageView banner = findViewById(R.id.banner);
        int[] imageResources = {R.drawable.banner, R.drawable.banner2};
        final int[] currentIndex = {0};

        // Create a timer that changes the image every 5 seconds
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                currentIndex[0] = (currentIndex[0] + 1) % imageResources.length;
                runOnUiThread(() -> banner.setImageResource(imageResources[currentIndex[0]]));
            }
        };
        timer.schedule(timerTask, 0, 5000); // Change image every 5 seconds (5000 milliseconds)

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    selectedFragment = new navigation_home();
                } else if (itemId == R.id.navigation_cart) {
                    selectedFragment = new navigation_cart();
                } else if (itemId == R.id.navigation_notifications) {
                    selectedFragment = new navigation_notifications();
                } else if (itemId == R.id.navigation_winners) {
                    selectedFragment = new navigation_winners();
                } else if (itemId == R.id.navigation_profile) {
                    selectedFragment = new navigation_profile();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
                return true;
            }
        });

    }
}
package com.example.stem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    TextView viewAll;
    ConstraintLayout homeToTask2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        viewAll = findViewById(R.id.home_view_all);
        homeToTask2 = findViewById(R.id.stemHomeTask2);

        ScrollView scrollView = findViewById(R.id.scroll_home);
        scrollView.scrollTo(0, 0); // scroll to top
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_actions:
                    startActivity(new Intent(getApplicationContext(), Action.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_leaderboard:
                    startActivity(new Intent(getApplicationContext(), Leaderboard.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });

        viewAll.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Action.class);
            startActivity(intent);
        });
        homeToTask2.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Task2.class);
            startActivity(intent);
        });
    }
}
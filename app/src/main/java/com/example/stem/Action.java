package com.example.stem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class Action extends AppCompatActivity {

    private ConstraintLayout layoutTask1;
    private ConstraintLayout layoutTask2;
    private ConstraintLayout layoutTask3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        layoutTask1 = findViewById(R.id.stemActionTask1);
        layoutTask2 = findViewById(R.id.stemActionTask2);
        layoutTask3 = findViewById(R.id.stemActionTask3);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_actions);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_actions:
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

        layoutTask1.setOnClickListener(v -> {
            Intent intent = new Intent(Action.this, Task1.class);
            startActivity(intent);
        });
        layoutTask2.setOnClickListener(v -> {
            Intent intent = new Intent(Action.this, Task2.class);
            startActivity(intent);
        });
        layoutTask3.setOnClickListener(v -> {
            Intent intent = new Intent(Action.this, Task3.class);
            startActivity(intent);
        });

    }
}
package com.example.stem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Action extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);



        ScrollView scrollView = findViewById(R.id.scroll_action_task);
        ScrollView scrollView1 = findViewById(R.id.scroll_action_quiz);
        scrollView1.scrollTo(0, 0); // scroll to top
        scrollView.scrollTo(0, 0); // scroll to top
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_actions);

        Button button = findViewById(R.id.quiz1);

        button.setOnClickListener(v -> {
            // Do something when the button is clicked
            Intent intent = new Intent(Action.this, Quiz1.class);
            startActivity(intent);
        });


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


    }
}
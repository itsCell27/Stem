package com.example.stem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Task2 extends AppCompatActivity {

    FloatingActionButton faB2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

        faB2 = findViewById(R.id.fab2);

        faB2.setOnClickListener(v -> onBackPressed());
    }
}
package com.example.stem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Task1 extends AppCompatActivity {

    FloatingActionButton faB1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        faB1 = findViewById(R.id.fab1);

        faB1.setOnClickListener(v -> onBackPressed());
    }
}
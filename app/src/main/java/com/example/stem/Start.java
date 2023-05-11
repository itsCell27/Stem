package com.example.stem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Start extends AppCompatActivity {

    Button startToSignUp;

    FirebaseAuth auth =FirebaseAuth.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startToSignUp = findViewById(R.id.started);

        // Check if user is already signed in
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser !=null) {
            // User is already signed in, start next activity
            startActivity(new Intent(Start.this, Quote.class));
            finish();
        } else {
            // User is not signed in, start signup activity
            startActivity(new Intent(Start.this, SignUp.class));
            finish();
        }


        startToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}
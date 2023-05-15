package com.example.stem;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Task1 extends AppCompatActivity {

    private DatabaseReference userRef;
    FloatingActionButton faB1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        // Initialize Firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();



        faB1 = findViewById(R.id.fab1);

        faB1.setOnClickListener(v -> onBackPressed());

        if (currentUser != null) {
            // Get the reference to the user in the database
            userRef = databaseReference.child("users").child(currentUser.getUid());

            // Set click listener for the button
            Button addPointsButton = findViewById(R.id.btnDone1);
            addPointsButton.setOnClickListener(v -> {
                addPoints();
                addPointsButton.setEnabled(false); // Disable the button after it is clicked
            });
        }
    }

    private void addPoints() {
        if (userRef == null) {
            return; // User reference is not available, cannot add points
        }

        // Retrieve the user from the database
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().getValue(User.class);

                // Update the points
                if (user != null) {
                    int currentPoints = user.getPoints();
                    int newPoints = currentPoints + 10;
                    user.setPoints(newPoints);

                    // Update the user in the database
                    userRef.setValue(user);
                }
            } else {
                // Handle any errors
                Exception exception = task.getException();

                if (exception instanceof DatabaseException) {
                    // Handle database-related errors
                    String errorMessage = "Database exception: " + exception.getMessage();
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                } else if (exception instanceof FirebaseAuthException) {
                    // Handle authentication-related errors
                    String errorMessage = "Authentication exception: " + exception.getMessage();
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                } else {
                    // Handle other types of exceptions
                    String errorMessage = "Exception: ";
                    if (exception != null) {
                        errorMessage += exception.getMessage();
                    } else {
                        errorMessage += "Unknown error occurred.";
                    }
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
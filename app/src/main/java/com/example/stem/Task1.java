package com.example.stem;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Date;




public class Task1 extends AppCompatActivity {

    private DatabaseReference userRef;
    private DatabaseReference lastButtonClickRef;
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
            lastButtonClickRef = databaseReference.child("users").child(currentUser.getUid()).child("lastButtonClick");
            userRef = databaseReference.child("users").child(currentUser.getUid());

            // Set click listener for the button
            Button addPointsButton = findViewById(R.id.btnDone1);
            addPointsButton.setOnClickListener(v -> {
                addPoints();

            });
        }
    }

    private void addPoints() {
        if (userRef == null || lastButtonClickRef == null) {
            return; // User reference or last button click reference is not available
        }

        // Get the current date
        Date currentDate = new Date();

        // Retrieve the last button click timestamp from the database
        lastButtonClickRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                Long lastButtonClickTimestamp = dataSnapshot.getValue(Long.class);

                // Check if the button can be clicked again
                if (lastButtonClickTimestamp == null || !DateUtils.isSameDay(currentDate, new Date(lastButtonClickTimestamp))) {
                    // Update the last button click timestamp in the database
                    lastButtonClickRef.setValue(currentDate.getTime()).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            // The button can be clicked, proceed with adding points
                            userRef.child("points").get().addOnCompleteListener(pointsTask -> {
                                if (pointsTask.isSuccessful()) {
                                    DataSnapshot pointsSnapshot = pointsTask.getResult();
                                    Integer currentPoints = pointsSnapshot.getValue(Integer.class);

                                    if (currentPoints != null) {
                                        // Increment the points by 10
                                        int newPoints = currentPoints + 10;

                                        // Update the points value in the database
                                        userRef.child("points").setValue(newPoints)
                                                .addOnCompleteListener(updatePointsTask -> {
                                                    if (updatePointsTask.isSuccessful()) {
                                                        // Points updated successfully
                                                        Toast.makeText(Task1.this, "You've earned points", Toast.LENGTH_SHORT).show();
                                                        // Update the UI or perform any other action
                                                    } else {
                                                        // Handle points update failure
                                                        Toast.makeText(Task1.this, "Points update failed", Toast.LENGTH_SHORT).show();
                                                        // TODO: Display an error message to the user
                                                    }
                                                });
                                    } else {
                                        // Handle the case where the points value is null
                                        Toast.makeText(Task1.this, "Points value is null", Toast.LENGTH_SHORT).show();
                                        // TODO: Display an error message or handle it accordingly
                                    }
                                } else {
                                    // Handle points retrieval failure
                                    Toast.makeText(Task1.this, "Points retrieval failed", Toast.LENGTH_SHORT).show();
                                    // TODO: Display an error message to the user
                                }
                            });
                        } else {
                            // Handle update failure
                            Toast.makeText(Task1.this, "Update failed", Toast.LENGTH_SHORT).show();
                            // TODO: Display an error message to the user
                        }
                    });
                } else {
                    // The button cannot be clicked again today
                    Toast.makeText(Task1.this, "This task can only be done once a day", Toast.LENGTH_SHORT).show();
                    // TODO: Display a message to the user or disable the button
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
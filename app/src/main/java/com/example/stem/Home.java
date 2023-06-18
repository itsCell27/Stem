package com.example.stem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {
    private DatabaseReference userRef;
    private TextView homeStemPoints;
    private TextView homeNumberOfTaskCompleted;
    ConstraintLayout homeToTask2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        // Initialize Firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        TextView viewAll = findViewById(R.id.home_view_all);
        homeToTask2 = findViewById(R.id.stemHomeTask2);
        homeStemPoints = findViewById(R.id.home_stem_points);
        homeNumberOfTaskCompleted = findViewById(R.id.home_task_achieved);

        HorizontalScrollView scrollView = findViewById(R.id.scroll_home);
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
        if (currentUser != null) {
            String userId = currentUser.getUid();

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("users").child(userId);

            // Rest of the code to retrieve and display user data
            // ...
            // Retrieve the user data from the database
            databaseReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);

                        // Update the points and numberOfTaskCompleted
                        if (user != null) {
                            int points = user.getPoints();
                            int numberOfTaskCompleted = user.getNumberOfTaskCompleted();

                            // Display the points and numberOfTaskCompleted in TextViews
                            homeStemPoints.setText(String.valueOf(points));
                            homeNumberOfTaskCompleted.setText(String.valueOf(numberOfTaskCompleted));
                        }
                    } else {
                        // Handle case when the user data does not exist
                        homeStemPoints.setText("N/A");
                        homeNumberOfTaskCompleted.setText("N/A");
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
        } else {
            // Handle the case when the user is not logged in
            Toast.makeText(this, "user not login", Toast.LENGTH_SHORT).show();
        }




    }
}
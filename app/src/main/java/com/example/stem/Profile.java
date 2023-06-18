package com.example.stem;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private DatabaseReference userRef;
    private TextView userRank;
    private TextView profileStemPoints;
    private TextView profileNumberOfTaskCompleted;

    Button logoutButton, deleteAccount, editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userRank = findViewById(R.id.profile_rank);
        profileStemPoints = findViewById(R.id.profile_stem_points);
        profileNumberOfTaskCompleted = findViewById(R.id.profile_task_achieved);

        // Initialize Firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        TextView usernameTextView = findViewById(R.id.usernameTextView);

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
                            profileStemPoints.setText(String.valueOf(points));
                            profileNumberOfTaskCompleted.setText(String.valueOf(numberOfTaskCompleted));
                        }
                    } else {
                        // Handle case when the user data does not exist
                        profileStemPoints.setText("N/A");
                        profileNumberOfTaskCompleted.setText("N/A");
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


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user  = auth.getCurrentUser();
        if (user !=null) {
            String uid = user.getUid();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.child("username").getValue(String.class);
                        usernameTextView.setText(username);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show();
        }



        logoutButton = findViewById(R.id.logout_button_profile);
        deleteAccount = findViewById(R.id.deleteAccountButton);
        editProfile = findViewById(R.id.profile_btn_edit_profile);

        ScrollView scrollView = findViewById(R.id.scroll_profile);
        scrollView.scrollTo(0, 0);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
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
                    return true;
            }
            return false;
        });

        logoutButton.setOnClickListener(v -> {
            AlertDialog.Builder builder =  new AlertDialog.Builder(Profile.this);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout, null);


            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            dialogView.findViewById(R.id.btnLogout).setOnClickListener(v1 -> {
                FirebaseAuth.getInstance().signOut();
                // Redirect to the login activity
                Intent intent = new Intent(Profile.this, Login.class);
                startActivity(intent);

                // Finish the current activity (optional)
                finish();
            });
            dialogView.findViewById(R.id.btnBack).setOnClickListener(v12 -> dialog.dismiss());
            if (dialog.getWindow() != null){
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            dialog.show();
        });
        deleteAccount.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_delete_account, null);

            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            dialogView.findViewById(R.id.btnDeleteAccount).setOnClickListener(v13 -> {
            FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
            if (user1 !=null){
                user1.delete()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                // Account deleted successfully
                                Toast.makeText(Profile.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                                // Redirect to the login screen or perform any other necessary actions
                                Intent intent = new Intent(Profile.this, SignUp.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Failed to delete the account
                                Toast.makeText(Profile.this, "Failed to delete account.", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
            });
            dialogView.findViewById(R.id.btnCancelAccount).setOnClickListener(v14 -> dialog.dismiss());
            if (dialog.getWindow() !=null){
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            dialog.show();
        });
        editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, EditProfile.class);
            startActivity(intent);
        });

        // Call the getRank() method
        RankingSystem.getRank(rank -> {
            // Update the TextView with the received rank
            userRank.setText(rank);
        });


    }
}
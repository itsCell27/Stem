package com.example.stem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Leaderboard extends AppCompatActivity {

    private DatabaseReference userRef;

    private TextView myLeaderboardRank;
    private TextView myLeaderboardUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Initialize Firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user  = auth.getCurrentUser();

        myLeaderboardRank = findViewById(R.id.my_leaderboard_rank);
        myLeaderboardUsername = findViewById(R.id.leaderboard_my_username);

        ScrollView scrollView = findViewById(R.id.scroll_leaderboard);
        scrollView.scrollTo(0, 0); // scroll to top

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_leaderboard);

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
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });

        // Call the getRank() method
        RankingSystem.getRank(new RankingSystem.RankCallback() {
            @Override
            public void onRankReceived(String rank) {
                // Update the TextView with the received rank
                myLeaderboardRank.setText(rank);
            }
        });
        if (currentUser != null) {
            // Get the reference to the user in the database
            userRef = databaseReference.child("users").child(currentUser.getUid());


        }
        if (user !=null) {
            String uid = user.getUid();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.child("username").getValue(String.class);
                        myLeaderboardUsername.setText(username);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show();
        }
    }
}
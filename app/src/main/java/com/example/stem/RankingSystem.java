package com.example.stem;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RankingSystem {
    private static final String RANK_RECRUIT = "Recruit";
    private static final String RANK_ADVOCATE = "Awareness Advocate";
    private static final String RANK_VOLUNTEER = "Volunteer";
    private static final String RANK_PROFESSIONAL = "Professional";
    private static final String RANK_VISIONARY = "Visionary";

    private static final int THRESHOLD_RECRUIT = 0;
    private static final int THRESHOLD_ADVOCATE = 100;
    private static final int THRESHOLD_VOLUNTEER = 500;
    private static final int THRESHOLD_PROFESSIONAL = 1000;
    private static final int THRESHOLD_VISIONARY = 5000;

    public static void getRank(final RankCallback callback) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        if (currentUser != null) {
            DatabaseReference userRef = databaseReference.child("users").child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            int points = user.getPoints();
                            String rank = calculateRank(points);
                            callback.onRankReceived(rank);
                        } else {
                            callback.onRankReceived("Unknown Rank");
                        }
                    } else {
                        callback.onRankReceived("Unknown Rank");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.onRankReceived("Unknown Rank");
                }
            });
        } else {
            callback.onRankReceived("Unknown Rank");
        }
    }


    private static String calculateRank(int points) {
        if (points >= THRESHOLD_VISIONARY) {
            return RANK_VISIONARY;
        } else if (points >= THRESHOLD_PROFESSIONAL) {
            return RANK_PROFESSIONAL;
        } else if (points >= THRESHOLD_VOLUNTEER) {
            return RANK_VOLUNTEER;
        } else if (points >= THRESHOLD_ADVOCATE) {
            return RANK_ADVOCATE;
        } else {
            return RANK_RECRUIT;
        }
    }

    public interface RankCallback {
        void onRankReceived(String rank);
    }
}


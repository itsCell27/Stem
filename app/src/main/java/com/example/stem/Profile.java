package com.example.stem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    Button logoutButton, deleteAccount, editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(Profile.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout, null);


                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        // Redirect to the login activity
                        Intent intent = new Intent(Profile.this, Login.class);
                        startActivity(intent);

                        // Finish the current activity (optional)
                        finish();
                    }
                });
                dialogView.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_delete_account, null);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btnDeleteAccount).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user !=null){
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
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
                                    }
                                });

                    }
                    }
                });
                dialogView.findViewById(R.id.btnCancelAccount).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() !=null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, EditProfile.class);
                startActivity(intent);
            }
        });

    }
}
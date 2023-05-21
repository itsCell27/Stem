package com.example.stem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {
    FloatingActionButton editProfileBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = "users/" + uid;
        DatabaseReference userRef = database.getReference(path);

        Button saveUsernameButton = findViewById(R.id.saveUsernameButton);
        saveUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newUsernameEditText = findViewById(R.id.newUsernameEditText);
                String newUsername = newUsernameEditText.getText().toString().trim();

                if (!newUsername.isEmpty()){
                    userRef.child("username").setValue(newUsername)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(EditProfile.this, "Username updated.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(EditProfile.this, Profile.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(EditProfile.this, "Failed to update username.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        editProfileBack = findViewById(R.id.edit_profile_back_btn);

        editProfileBack.setOnClickListener(v -> onBackPressed());


    }
}
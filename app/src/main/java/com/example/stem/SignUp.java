package com.example.stem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;
import java.util.UUID;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword;
    private Button signupButton;
    private TextView loginRedirectText;


    String generateRandomUsername() {
        String prefix = "user";
        // Generate a random number or unique ID
        int randomNumber = new Random().nextInt(10000);
        return prefix + randomNumber;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();






                if (user.isEmpty()){
                    signupEmail.setError("Email cannot be empty");
                }
                if (pass.isEmpty()){
                    signupPassword.setError("Password cannot be empty");
                }else {
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = auth.getCurrentUser();

                                if (user !=null) {
                                    String uid = user.getUid();
                                    String randomUsername = generateRandomUsername();

                                    // Store the username in the Firebase Realtime Database
                                    DatabaseReference userRef =FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                                    userRef.child("username").setValue(randomUsername)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(SignUp.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(SignUp.this, Login.class));
                                                    } else {
                                                        // Failed to store the username
                                                        Toast.makeText(SignUp.this, "Failed to store username.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }else {
                                // Failed to create a new user
                                Toast.makeText(SignUp.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });

    }
}
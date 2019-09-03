package com.example.dnd5echaracterassistant;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;

    private TextView tv_status;
    private TextView tv_detail;
    private EditText et_username;
    private EditText et_password;

    private boolean isCharCreate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        // Views
        tv_status = findViewById(R.id.tv_status);
        tv_detail = findViewById(R.id.tv_detail);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        // Buttons
        findViewById(R.id.b_signIn).setOnClickListener(this);
        findViewById(R.id.b_createProfile).setOnClickListener(this);
        findViewById(R.id.b_signOut).setOnClickListener(this);
        findViewById(R.id.b_verifyEmail).setOnClickListener(this);
        findViewById(R.id.b_toUserMain).setOnClickListener(this);
        findViewById(R.id.b_toUseChar).setOnClickListener(this);
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Error: Account not created.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            tv_status.setText("Authentication Failed");
                        }

                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = et_username.getText().toString();
        if (TextUtils.isEmpty(email)) {
            et_username.setError("Required.");
            valid = false;
        } else {
            et_username.setError(null);
        }

        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            et_password.setError("Required.");
            valid = false;
        } else {
            et_password.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            tv_status.setText("Home Page");
            tv_detail.setText("Welcome");

            findViewById(R.id.et_username).setVisibility(View.GONE);
            findViewById(R.id.et_password).setVisibility(View.GONE);
            findViewById(R.id.b_signIn).setVisibility(View.GONE);
            findViewById(R.id.b_createProfile).setVisibility(View.GONE);

            findViewById(R.id.b_verifyEmail).setVisibility(View.VISIBLE);
            findViewById(R.id.b_signOut).setVisibility(View.VISIBLE);
            findViewById(R.id.b_toUseChar).setVisibility(View.VISIBLE);

            findViewById(R.id.b_verifyEmail).setEnabled(!user.isEmailVerified());
            if(user.isEmailVerified()) {
                findViewById(R.id.b_verifyEmail).setVisibility(View.GONE);
                findViewById(R.id.b_toUserMain).setVisibility(View.VISIBLE);
            }



        } else {
            tv_status.setText("Login Page");
            tv_detail.setText(null);

            findViewById(R.id.et_username).setVisibility(View.VISIBLE);
            findViewById(R.id.et_password).setVisibility(View.VISIBLE);
            findViewById(R.id.b_signIn).setVisibility(View.VISIBLE);
            findViewById(R.id.b_createProfile).setVisibility(View.VISIBLE);

            findViewById(R.id.b_verifyEmail).setVisibility(View.GONE);
            findViewById(R.id.b_signOut).setVisibility(View.GONE);
            findViewById(R.id.b_toUserMain).setVisibility(View.GONE);

            findViewById(R.id.b_createCharacter).setEnabled(isCharCreate);
            if(isCharCreate) {
                findViewById(R.id.b_toUseChar).setVisibility(View.GONE);
            }
        }
    }
    private void sendEmailVerification() {
        // Disable button
        findViewById(R.id.b_verifyEmail).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.b_verifyEmail).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }
    private void toUserMain(){
        Log.d(TAG, "toUserMain: clicked toUserMain");
        Intent intent = new Intent(MainActivity.this, UserMain.class);
        startActivity(intent);
    }
    private void toUserChar(){
        Log.d(TAG, "toUserChar: Clicked toUserChar");
        Intent intent = new Intent(MainActivity.this, UseCharacter.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.b_createProfile) {
            createAccount(et_username.getText().toString(), et_password.getText().toString());
        } else if (i == R.id.b_signIn) {
            signIn(et_username.getText().toString(), et_password.getText().toString());
        } else if (i == R.id.b_signOut) {
            signOut();
        } else if (i == R.id.b_verifyEmail) {
            sendEmailVerification();
        } else if (i == R.id.b_toUserMain) {
            toUserMain();
        } else if (i == R.id.b_toUseChar) {
            toUserChar();
        }
    }
}
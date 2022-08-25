package com.example.facerace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth myAuth;
    private EditText editTextEmail, editTextPassword;
    private Button signInButton;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializer();
        signInButton.setOnClickListener(this);

    }

    private void initializer() {
        myAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email_editText_loginScreen);
        editTextPassword = findViewById(R.id.password_editText_loginScreen);
        signInButton = findViewById(R.id.login_button_loginScreen);
        TextView register = findViewById(R.id.register_textView);
        TextView forgotPassword = findViewById(R.id.forgot_password_textView_loginScreen);
        register.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar_loginScreen);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register_textView) {
            registerUser();
        }
        if (view.getId() == R.id.login_button_loginScreen) {
            userLogin();
        }
        if (view.getId() == R.id.forgot_password_textView_loginScreen) {
            forgotPassword();
        }
    }

    private void registerUser() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void forgotPassword() {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    private void userLogin() {
        String emailString = editTextEmail.getText().toString().trim();
        String passwordString = editTextPassword.getText().toString().trim();

        if (emailString.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            editTextEmail.setError("Valid Email is required");
            editTextEmail.requestFocus();
        }

        if (passwordString.isEmpty() || passwordString.length() < 6) {
            editTextPassword.setError("Password is required and must longer than 6 characters.");
            editTextPassword.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        myAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        if (user.isEmailVerified()) {
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            user.sendEmailVerification();
                            Toast.makeText(LoginActivity.this, "Check your email and verify your account.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "User not found.", Toast.LENGTH_LONG).show();

                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Failed to login. Please check credentials.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
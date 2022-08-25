package com.example.facerace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button forgotButton;
    private EditText emailEditText;
    private ProgressBar progressBar;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initializer();
    }

    public void initializer() {
        myAuth = FirebaseAuth.getInstance();
        forgotButton = findViewById(R.id.forgotPassword_button_forgotPassword);
        emailEditText = findViewById(R.id.email_editText_forgotPassword);
        progressBar = findViewById(R.id.progressBar_forgotPassword);
        progressBar.setVisibility(View.GONE);
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String emailString = emailEditText.getText().toString().trim();

        if (emailString.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            emailEditText.setError("Valid Email is required");
            emailEditText.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        myAuth.sendPasswordResetEmail(emailString).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPasswordActivity.this, "Check your email and follow the instructions to reset your password.", Toast.LENGTH_LONG).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPasswordActivity.this, "Whoops! Something went wrong.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
package com.example.facerace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth myAuth;
    private EditText name, email, password;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializer();
    }

    public void initializer() {
        myAuth = FirebaseAuth.getInstance();
        TextView title = findViewById(R.id.title_textView_register);
        this.name = findViewById(R.id.name_editText_register);
        this.email = findViewById(R.id.email_editText_register);
        this.password = findViewById(R.id.password_editText_registerScreen);
        this.progressBar = findViewById(R.id.progressBar_register);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.title_textView_register) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (view.getId() == R.id.register_button_registerScreen) {
            registerUser();
        }
    }

    private void registerUser() {
        String userNameString = this.name.getText().toString().trim();
        String emailString = this.email.getText().toString().trim();
        String passwordString = this.password.getText().toString().trim();

        if (userNameString.isEmpty()) {
            this.name.setError("Name is required.");
            this.name.requestFocus();
        }

        if (emailString.isEmpty()) {
            this.email.setError("Email is required.");
            this.email.requestFocus();
        }

        if (passwordString.isEmpty() || passwordString.length() < 6) {
            this.password.setError("Password is required and must longer than 6 characters.");
            this.password.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            this.email.setError("Please provide a valid Email.");
            this.email.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        myAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = new User(userNameString, emailString);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {

                                    if (task1.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "User has been registered Successfully", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_LONG).show();
                        System.out.println(task);
                        progressBar.setVisibility(View.GONE);
                    }
                });


    }
}
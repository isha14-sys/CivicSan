package com.mountreach.civicsan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Login button click
        btnLogin.setOnClickListener(v -> validateLogin());

        // Go to Register Activity
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

    private void validateLogin() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Check empty fields
        if (email.isEmpty()) {
            etEmail.setError("Enter email");
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Enter password");
            return;
        }

        // Email must contain @gmail.com
        if (!email.endsWith("@gmail.com")) {
            etEmail.setError("Email must contain @gmail.com");
            return;
        }

        // Basic email pattern check
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter valid email");
            return;
        }

        // Password minimum 8 characters
        if (password.length() < 8) {
            etPassword.setError("Password must be at least 8 characters");
            return;
        }

        // If everything is correct
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

        // Example: Open HomeActivity
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
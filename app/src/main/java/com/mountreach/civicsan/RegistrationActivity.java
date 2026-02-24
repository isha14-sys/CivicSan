package com.mountreach.civicsan;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    EditText etName, etEmail, etMobile, etPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> validateData());
    }

    private void validateData() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Enter full name");
            return;
        }

        // Email must contain @gmail.com
        if (!email.endsWith("@gmail.com")) {
            etEmail.setError("Email must contain @gmail.com");
            return;
        }

        // Mobile validation (10 digits only)
        if (!mobile.matches("\\d{10}")) {
            etMobile.setError("Enter valid 10 digit mobile number");
            return;
        }

        // Password validation
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";

        if (!password.matches(passwordPattern)) {
            etPassword.setError("Password must contain uppercase, lowercase & special character");
            return;
        }

        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
    }
}
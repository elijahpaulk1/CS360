package com.example.project3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText usernameEditText = findViewById(R.id.editTextUsername);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Button registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                attemptRegistration(username, password);
            }
        });
    }

    private void attemptRegistration(String username, String password) {
        // Call your registerUser method from DatabaseHelper
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        if (dbHelper.registerUser(username, password)) {
            // Registration successful, show a success message
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            finish(); // Close the registration activity
        } else {
            // Registration failed, show an error message
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEditText = findViewById(R.id.editTextUsername);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.buttonLogin);
        Button registerButton = findViewById(R.id.buttonRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                attemptLogin(username, password);
            }
        });

        // Set OnClickListener for the registration button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the registration screen
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void attemptLogin(String username, String password) {
        // Call your login method from DatabaseHelper or UserRepository
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        if (dbHelper.loginUser(username, password)) {
            // Login successful, navigate to the next screen
            Intent intent = new Intent(this, DatabaseActivity.class);
            startActivity(intent);
            finish(); // Close the login activity to prevent going back to it
        } else {
            // Invalid credentials, show an error message
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}

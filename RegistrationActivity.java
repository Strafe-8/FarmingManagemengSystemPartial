package com.example.farmingmanagemengsystempartial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText editEmail; // Added Email field
    private TextInputEditText editUsername;
    private TextInputEditText editPassword;
    private Spinner spinnerLocation;
    public static HashMap<String, User> userDatabase = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editEmail = findViewById(R.id.EditEmail);
        editUsername = findViewById(R.id.EditUsername);
        editPassword = findViewById(R.id.EditPassword);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        Button signupButton = findViewById(R.id.signup_button);
        TextView textBackToLogin = findViewById(R.id.text_back_to_login);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.countries_array, android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter);

        signupButton.setOnClickListener(view -> registerUser());
        textBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim(); // Using email
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String country = spinnerLocation.getSelectedItem().toString();

        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || country.equals("Select Country")) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userDatabase.containsKey(username)) {
            Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(email, username, password, country); // Pass email to User
        userDatabase.put(username, newUser);
        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
        finish();
    }

    public static class User {
        String email; // Added email field
        String username;
        String password;
        String country;

        public User(String email, String username, String password, String country) {
            this.email = email; // Initialize email
            this.username = username;
            this.password = password;
            this.country = country;
        }
    }
}
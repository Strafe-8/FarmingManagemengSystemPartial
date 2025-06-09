package com.example.farmingmanagemengsystempartial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editNewPassword;
    private EditText editConfirmNewPassword;
    public static HashMap<String, User> userDatabase = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editEmail = findViewById(R.id.EditEmail);
        editNewPassword = findViewById(R.id.EditNewPassword);
        editConfirmNewPassword = findViewById(R.id.EditConfirmNewPassword);
        Button changePasswordButton = findViewById(R.id.signup_button);
        TextView textBackToLogin = findViewById(R.id.text_back_to_login);

        changePasswordButton.setOnClickListener(view -> changePassword());
        textBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void changePassword() {
        String email = editEmail.getText().toString().trim();
        String newPassword = editNewPassword.getText().toString().trim();
        String confirmNewPassword = editConfirmNewPassword.getText().toString().trim();

        if (email.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User existingUser = userDatabase.get(email);
        if (existingUser == null) {
            Toast.makeText(this, "Email not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        existingUser.password = newPassword;
        Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
        finish();
    }

    public static class User {
        String email;
        String username;
        String password;
        String country;

        public User(String email, String username, String password, String country) {
            this.email = email;
            this.username = username;
            this.password = password;
            this.country = country;
        }
    }
}
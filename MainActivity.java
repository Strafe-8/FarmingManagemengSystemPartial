package com.example.farmingmanagemengsystempartial;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public String storedEmail = "random@gmail.com";
    public String storedPassword = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText emailInput = findViewById(R.id.EditEmail);
        EditText passwordInput = findViewById(R.id.EditPassword);
        Button signInBtn = findViewById(R.id.sign_in_btn);
//        TextView forgotPassword = findViewById(R.id.forgot_password);
        Button regBtn = findViewById(R.id.reg_btn);

        signInBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String pw = passwordInput.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pw)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (email.equals(storedEmail) && pw.equals(storedPassword)) {
                startActivity(new Intent(this, DashboardActivity.class));
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

//        forgotPassword.setOnClickListener(v ->
//                startActivity(new Intent(this, ForgotPasswordActivity.class)));
//
        regBtn.setOnClickListener(v ->
                startActivity(new Intent(this, RegistrationActivity.class)));
    }

}
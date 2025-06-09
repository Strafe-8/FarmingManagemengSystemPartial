package com.example.farmingmanagemengsystempartial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ImageView notification = findViewById(R.id.notificationTab);
        ImageView animalTrack = findViewById(R.id.trackingTab);
        ImageView product = findViewById(R.id.productTab);
        ImageView educationalResources = findViewById(R.id.eduResourceTab);
        ImageView settings = findViewById(R.id.settingsTab);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        animalTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AnimalTrackActivity.class);
                startActivity(intent);
            }
        });

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });

        educationalResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, EducationalResourcesActivity.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
package com.example.farmingmanagemengsystempartial;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class AnimalTrackActivity extends AppCompatActivity {
    ImageView Back;
    Button addGrowthButton;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_track);

        Back = findViewById(R.id.imageView);
        addGrowthButton = findViewById(R.id.add_growth_data);
        barChart = findViewById(R.id.barChart);

        Back.setOnClickListener(v -> {
            Intent intent = new Intent(AnimalTrackActivity.this, DashboardActivity.class);
            startActivity(intent);
        });

        addGrowthButton.setOnClickListener(v -> showAddGrowthDialog());

        setupBarChart(); // Initial setup for the BarChart
    }

    private void showAddGrowthDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_growth, null);
        builder.setView(dialogView);

        Spinner spinnerFarms = dialogView.findViewById(R.id.spinner_farms);
        EditText editNumberOfChickens = dialogView.findViewById(R.id.edit_number_of_chickens);
        EditText editAverageWeight = dialogView.findViewById(R.id.edit_average_weight);
        Button buttonApplyChanges = dialogView.findViewById(R.id.button_apply_changes);
        TextView textCancel = dialogView.findViewById(R.id.text_cancel);

        // Populate the spinner with farm names from FarmManager
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(FarmManager.getInstance().getFarms().keySet()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFarms.setAdapter(adapter);

        buttonApplyChanges.setOnClickListener(v -> {
            String numberOfChickens = editNumberOfChickens.getText().toString();
            String averageWeight = editAverageWeight.getText().toString();
            String selectedFarm = spinnerFarms.getSelectedItem() != null ? spinnerFarms.getSelectedItem().toString() : "";

            if (!numberOfChickens.isEmpty() && !averageWeight.isEmpty() && !selectedFarm.isEmpty()) {
                // Handle the input data
                Toast.makeText(AnimalTrackActivity.this,
                        "Added: " + numberOfChickens + " chickens, " + averageWeight + " kg to " + selectedFarm,
                        Toast.LENGTH_SHORT).show();

                updateBarChart(Float.parseFloat(averageWeight), selectedFarm); // Update the bar chart
            } else {
                Toast.makeText(AnimalTrackActivity.this,
                        "Please fill in all fields and select a farm", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        textCancel.setOnClickListener(v -> dialog.dismiss());
    }

    private void updateBarChart(float averageWeight, String farmName) {
        int totalChickens = FarmManager.getInstance().getFarms().get(farmName).numberOfChickens;
        float totalWeight = averageWeight * totalChickens; // Total weight calculation

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, totalWeight)); // Use total weight

        BarDataSet dataSet = new BarDataSet(entries, farmName); // Use dynamic farm name
        dataSet.setColor(getResources().getColor(R.color.neon_green)); // Set bar color
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);
        barChart.setDrawValueAboveBar(true);

        // Set the width of the bars
        barData.setBarWidth(0.2f); // Adjust this value to make bars slimmer or thicker

        barChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return farmName; // Use dynamic farm name
            }
        });

        barChart.invalidate(); // Refresh the chart
    }

    private void setupBarChart() {
        // Initial setup for the BarChart can be defined here
    }
}
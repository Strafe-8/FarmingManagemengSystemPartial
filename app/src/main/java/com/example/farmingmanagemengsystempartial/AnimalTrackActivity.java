package com.example.farmingmanagemengsystempartial;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AnimalTrackActivity extends AppCompatActivity {
    Dialog dialog;
    ImageView Back, applyChanges;
    Button AddGrowthData;

    private WeightBarView weightBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_animal_track);

        //for setting text in panel
        TextView sizeValString = findViewById(R.id.sizeValue);
        TextView dayValString = findViewById(R.id.daysOldValue);
        TextView averageWeightValString = findViewById(R.id.averageWeightValue);
        TextView targetValString = findViewById(R.id.targetValue);


        weightBarView = findViewById(R.id.weightBar);
        weightBarView.setAverageWeight(2.5f); // Example weight

        AddGrowthData = findViewById(R.id.Add_growth_Data_Btn);


        AddGrowthData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(AnimalTrackActivity.this);
                dialog.setContentView(R.layout.dialog_add_growth);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //fetch values from dialog input
                EditText sizeEdit = dialog.findViewById(R.id.editTextNumber);
                final EditText sampleSizeEdit = dialog.findViewById(R.id.editTextNumber2);
                EditText weightOneEdit = dialog.findViewById(R.id.editTextNumberDecimal);
                EditText weightTwoEdit = dialog.findViewById(R.id.editTextNumberDecimal4);

                View applyChanges = dialog.findViewById(R.id.ApplyChangesBTN);

                //code block for sample size restriction
                sampleSizeEdit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().equals("2")) {
                            sampleSizeEdit.setError("Sample size must be '2' (per app requirements)");
                        } else {
                            sampleSizeEdit.setError(null); // Clear error if valid
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {}
                });

                //dialog box
                applyChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //conversion to string
                        String sizeString = sizeEdit.getText().toString();
                        String sampleSizeString = sampleSizeEdit.getText().toString();
                        String weightOneString = weightOneEdit.getText().toString();
                        String weightTwoString = weightTwoEdit.getText().toString();

                        if (!sizeString.isEmpty() && !sampleSizeString.isEmpty() && !weightOneString.isEmpty() && !weightTwoString.isEmpty()){
                            //conversion to non-string
                            int size = Integer.parseInt(sizeString);
                            int sampleSize = Integer.parseInt(sampleSizeString);
                            float weightOne = Float.parseFloat(weightOneString);
                            float weightTwo = Float.parseFloat(weightTwoString);

                            //weight and deviation
                            float averageWeight = (weightOne + weightTwo) / sampleSize;
                            float deviation = ((averageWeight - 3.0f) / 3.0f) * 1; //please change if the formula is wrong


                            //display results
                            sizeValString.setText(sizeString);
                            dayValString.setText("10"); //days are placeholder value
                            averageWeightValString.setText(String.valueOf(averageWeight) + "/3.0kg");
                            targetValString.setText(String.format("%.2f", deviation) + "%");
                            weightBarView.setAverageWeight(averageWeight);

                        }else{
                            Toast.makeText(AnimalTrackActivity.this, "Please fill required fields.", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        Back = findViewById(R.id.ReturnBtn);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalTrackActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}
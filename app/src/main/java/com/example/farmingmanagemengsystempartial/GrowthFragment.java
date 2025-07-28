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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GrowthFragment extends Fragment {
    Dialog dialog;
    ImageView back;
    Button addGrowthData;
    private WeightBarView weightBarView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_growth, container, false);

        // For setting text in panel
        TextView sizeValString = view.findViewById(R.id.sizeValue);
        TextView dayValString = view.findViewById(R.id.daysOldValue);
        TextView averageWeightValString = view.findViewById(R.id.averageWeightValue);
        TextView targetValString = view.findViewById(R.id.targetValue);

        weightBarView = view.findViewById(R.id.weightBar);
        weightBarView.setAverageWeight(2.5f); // Example weight

        addGrowthData = view.findViewById(R.id.Add_growth_Data_Btn);
        addGrowthData.setOnClickListener(v -> showAddGrowthDataDialog(sizeValString, dayValString, averageWeightValString, targetValString));

        back = view.findViewById(R.id.ReturnBtn);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DashboardActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void showAddGrowthDataDialog(TextView sizeValString, TextView dayValString, TextView averageWeightValString, TextView targetValString) {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_growth);
        dialog.getWindow().setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT); // Set dialog width

        // Fetch values from dialog input
        EditText sizeEdit = dialog.findViewById(R.id.editTextNumber);
        EditText sampleSizeEdit = dialog.findViewById(R.id.editTextNumber2);
        EditText weightOneEdit = dialog.findViewById(R.id.editTextNumberDecimal);
        EditText weightTwoEdit = dialog.findViewById(R.id.editTextNumberDecimal4);
        Button applyChanges = dialog.findViewById(R.id.ApplyChangesBTN);

        // Sample size restriction
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

        // Dialog box
        applyChanges.setOnClickListener(v -> {
            String sizeString = sizeEdit.getText().toString();
            String sampleSizeString = sampleSizeEdit.getText().toString();
            String weightOneString = weightOneEdit.getText().toString();
            String weightTwoString = weightTwoEdit.getText().toString();

            if (!sizeString.isEmpty() && !sampleSizeString.isEmpty() && !weightOneString.isEmpty() && !weightTwoString.isEmpty()) {
                int size = Integer.parseInt(sizeString);
                int sampleSize = Integer.parseInt(sampleSizeString);
                float weightOne = Float.parseFloat(weightOneString);
                float weightTwo = Float.parseFloat(weightTwoString);

                float averageWeight = (weightOne + weightTwo) / sampleSize;
                float deviation = ((averageWeight - 3.0f) / 3.0f) * 100; // Calculating deviation in percentage

                sizeValString.setText(sizeString);
                dayValString.setText("10"); // Placeholder value
                averageWeightValString.setText(String.format("%.2f/3.0kg", averageWeight));
                targetValString.setText(String.format("%.2f%%", deviation));
                weightBarView.setAverageWeight(averageWeight);
            } else {
                Toast.makeText(getActivity(), "Please fill required fields.", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        dialog.show();
    }
}
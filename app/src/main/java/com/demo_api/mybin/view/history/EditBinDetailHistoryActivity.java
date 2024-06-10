package com.demo_api.mybin.view.history;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.demo_api.mybin.DatabaseHelper;
import com.demo_api.mybin.R;
import com.demo_api.mybin.model.BinDetailHistory;

public class EditBinDetailHistoryActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton radioPlastic, radioMetal, radioPaper, radioOther;
    private Button buttonSave;
    private TextView timeTv, accuracyTv;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bin_detail_history);

        // Initialize the views
        radioGroup = findViewById(R.id.radioGroup);
        radioPlastic = findViewById(R.id.radioPlastic);
        radioMetal = findViewById(R.id.radioMetal);
        radioPaper = findViewById(R.id.radioPaper);
        radioOther = findViewById(R.id.radioOther);
        buttonSave = findViewById(R.id.buttonSave);
        timeTv = findViewById(R.id.editTime);
        accuracyTv = findViewById(R.id.editAccuracy);
        databaseHelper = new DatabaseHelper(this);
        // Get the data passed from the previous activity
        Intent intent = getIntent();
        String name = intent.getStringExtra("HISTORY_NAME");
        String time = intent.getStringExtra("HISTORY_TIME");
        String accuracy = intent.getStringExtra("HISTORY_ACCURACY");

        timeTv.setText(time);
        accuracyTv.setText(accuracy);

        // Set the radio button based on the name
        switch (name) {
            case "plastic":
                radioPlastic.setChecked(true);
                break;
            case "metal":
                radioMetal.setChecked(true);
                break;
            case "paper":
                radioPaper.setChecked(true);
                break;
            case "other":
                radioOther.setChecked(true);
                break;
        }

        // Handle the save button click
        buttonSave.setOnClickListener(v -> {
            // Get the selected radio button
            int selectedId = radioGroup.getCheckedRadioButtonId();
            String updatedName = "";

            if (selectedId == radioPlastic.getId()) {
                updatedName = "plastic";
            } else if (selectedId == radioMetal.getId()) {
                updatedName = "metal";
            } else if (selectedId == radioPaper.getId()) {
                updatedName = "paper";
            } else if (selectedId == radioOther.getId()) {
                updatedName = "other";
            }
            BinDetailHistory binDetailHistory = new BinDetailHistory(time.replace("Thời điểm: ", ""), updatedName, accuracy.replace("%", "")
                    .replace("Độ chính xác: ", ""));
//            databaseHelper.createBinTable();
            databaseHelper.addBin(binDetailHistory);
            // Show a toast message with the selected option (for demonstration)
            Toast.makeText(this, "Selected: " + updatedName, Toast.LENGTH_SHORT).show();

            // Here you can perform the update operation (e.g., update the database or notify the adapter)

            // Finish the activity and return to the previous screen
            finish();
        });
    }
}

package com.demo_api.mybin.view.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo_api.mybin.R;
import com.demo_api.mybin.model.User;
import com.demo_api.mybin.DatabaseHelper;
import com.demo_api.mybin.view.profile.ProfileFragment;


public class EditProfileActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPhone, etAddress;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);
        btnSave = findViewById(R.id.btn_save);

        databaseHelper = new DatabaseHelper(this);

        // Get user ID from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);

        // Load user profile information
        loadUserProfile();

        btnSave.setOnClickListener(v -> saveUserProfile());
    }

    private void loadUserProfile() {
        User user = databaseHelper.getUser(userId);
        if (user != null) {
            etUsername.setText(user.getName());
            etEmail.setText(user.getEmail());
            etPhone.setText(user.getPhoneNumber());
            etAddress.setText(user.getAddress());
        }
    }

    private void saveUserProfile() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Hãy điền hết các trường!", Toast.LENGTH_SHORT).show();
            return;
        }

//        User user = new User(userId, username, email, phone, address, null);
        User user = databaseHelper.getUser(userId);
        user.setName(username);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setAddress(address);
        int rowsAffected = databaseHelper.updateUser(user);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Chỉnh sửa thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), ProfileFragment.class);
            startActivity(intent);
//            finish(); // Close the activity
        } else {
            Toast.makeText(this, "Đã có lỗi xảy ra.", Toast.LENGTH_SHORT).show();
        }
    }
}


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
import com.demo_api.mybin.view.MainActivity;
import com.example.app.database.DatabaseHelper;


public class RegistrationActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfPassword;
    private EditText etPhone;
    private EditText etAddress;
    private Button btnRegister;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfPassword = findViewById(R.id.et_confpassword);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);
        btnRegister = findViewById(R.id.btn_register);

        databaseHelper = new DatabaseHelper(this);

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etPassword.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if (password.equals(confirmPassword)) {
                User user = new User(0, username, password, email, phone, address);
                databaseHelper.addUser(user);

                SharedPreferences prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("user_id", user.getId());
                editor.apply();

                // Navigate to MainActivity
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Mật khẩu và xác nhận mật khẩu phải trùng nhau!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Hãy nhập các thông tin cần thiết!", Toast.LENGTH_SHORT).show();
        }
    }
}


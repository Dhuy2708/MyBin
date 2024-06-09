package com.demo_api.mybin.view.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo_api.mybin.DatabaseHelper;
import com.demo_api.mybin.R;
import com.demo_api.mybin.model.User;
import com.demo_api.mybin.view.MainActivity;
import com.demo_api.mybin.view.profile.ProfileFragment;

public class EditPasswordActivity extends AppCompatActivity {
    private EditText etOldPass;
    private EditText etNewPass;
    private EditText etConfirmNewPass;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        etOldPass = findViewById(R.id.et_oldPass);
        etNewPass = findViewById(R.id.et_newPass);
        etConfirmNewPass = findViewById(R.id.et_confpassword1);
        btnSave = findViewById(R.id.btn_save1);
//
        databaseHelper = new DatabaseHelper(this);
//
//        // Get user ID from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);
//
//        // Load user profile information
//        loadUserProfile();
//
        btnSave.setOnClickListener(v -> saveUserPassword());
    }

    private void saveUserPassword() {
        String oldPass = etOldPass.getText().toString().trim();
        String newPass = etNewPass.getText().toString().trim();
        String confirmPass = etConfirmNewPass.getText().toString().trim();

        if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "Hãy điền hết các trường!", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = databaseHelper.getUser(userId);
        if (user != null) {
            if (oldPass.equals(user.getPassword())){
                if (newPass.equals(confirmPass)) {
                    user.setPassword(newPass);
                    int rowsAffected = databaseHelper.updateUser(user);

                    if (rowsAffected > 0){
                        Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Đã có lỗi xảy ra.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Mật khẩu mới phải trùng với xác nhận mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Mật khẩu cũ không trùng khớp!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

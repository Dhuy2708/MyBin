package com.demo_api.mybin.view;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.demo_api.mybin.R;
import com.demo_api.mybin.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.demo_api.mybin.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        databaseHelper.dropTable("DROP TABLE IF EXISTS table_name");
//        databaseHelper.createTable();
//        databaseHelper.deleteAllUsers();
//        User user = new User(0, "cuong21022003", "123456", "@drawable/user_1"
//                ,"Huỳnh Minh Cường", "cuong21022003@gmail.com", "0931964259", "12 Cao Xuân Huy");
//        databaseHelper.addUser(user);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        NavController navController = Navigation.findNavController(this, R.id.hostFragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}
package com.demo_api.mybin.view.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo_api.mybin.R;
import com.demo_api.mybin.model.User;
import com.demo_api.mybin.view.MainActivity;
import com.demo_api.mybin.view.home.HomeFragment;
import com.demo_api.mybin.view.user.EditProfileActivity;
import com.demo_api.mybin.view.user.LoginActivity;
import com.demo_api.mybin.DatabaseHelper;

public class ProfileFragment extends Fragment {
    private TextView promptLogin;
    private Button btnLogin;
    private Button btnLogout;
    private Button btnUpdateProfile;
    private ImageView profileImage;
    private ImageView phoneIcon;
    private ImageView locationIcon;
    private TextView profileUsername;
    private TextView profileEmail;
    private TextView profilePhone;
    private TextView profileAddress;
    private RelativeLayout loginPage;
    private ScrollView profilePage;
    private DatabaseHelper databaseHelper;
    private static final String PREFS_NAME = "user_prefs";
    private static final String PREF_USER_ID = "user_id";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = view.findViewById(R.id.profile_image);
        profileUsername = view.findViewById(R.id.profile_username);
        profileEmail = view.findViewById(R.id.profile_email);
        profilePhone = view.findViewById(R.id.profile_phone);
        profileAddress = view.findViewById(R.id.profile_address);
        locationIcon = view.findViewById(R.id.location_icon);
        phoneIcon = view.findViewById(R.id.phone_icon);
        promptLogin = view.findViewById(R.id.prompt_login);
        btnLogin = view.findViewById(R.id.btn_login);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnUpdateProfile = view.findViewById(R.id.btn_update_profile);
        loginPage = view.findViewById(R.id.login_screen1);
        profilePage = view.findViewById(R.id.profile_page);
        databaseHelper = new DatabaseHelper(getContext());

//        SharedPreferences prefs = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.clear();
//        editor.apply();
//        loadUserProfile();
        // Check login status
        if (isLoggedIn()) {
            loadUserProfile();
        } else {
            SharedPreferences prefs = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            showLoginPrompt();
        }
        btnLogout.setOnClickListener(v -> logoutUser());
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
        btnUpdateProfile.setOnClickListener(v -> editUserProfile());

        return view;
    }

    private void editUserProfile() {
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(intent);
    }

    private boolean isLoggedIn() {
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.contains(PREF_USER_ID);
    }

    private void showLoginPrompt() {
//        profileImage.setVisibility(View.GONE);
//        profileUsername.setVisibility(View.GONE);
//        profileEmail.setVisibility(View.GONE);
//        profilePhone.setVisibility(View.GONE);
//        profileAddress.setVisibility(View.GONE);
        promptLogin.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.VISIBLE);
//        btnLogout.setVisibility(View.GONE);
//        btnUpdateProfile.setVisibility(View.GONE);
//        phoneIcon.setVisibility(View.GONE);
//        locationIcon.setVisibility(View.GONE);
        profilePage.setVisibility(View.GONE);
        loginPage.setVisibility(View.VISIBLE);
    }

    private void loadUserProfile() {
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int userId = prefs.getInt(PREF_USER_ID, -1);
        Log.d("DEBUG1", String.valueOf(userId));
        if (userId != -1) {
            User user = databaseHelper.getUser(userId);
            if (user != null) {
                profileUsername.setText(user.getName());
                profileEmail.setText(user.getEmail());
                profilePhone.setText(user.getPhoneNumber());
                profileAddress.setText(user.getAddress());
                // Set profile image resource if available
                profileImage.setImageResource(R.drawable.user_1);
//                Bitmap bm = BitmapFactory.decodeFile(user.getAvatar());
//                byte[] decodedString = Base64.decode(user.getAvatar(), Base64.DEFAULT);
//                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                profileImage.setImageBitmap(decodedByte);

//                profileImage.setVisibility(View.VISIBLE);
//                profileUsername.setVisibility(View.VISIBLE);
//                profileEmail.setVisibility(View.VISIBLE);
//                profilePhone.setVisibility(View.VISIBLE);
//                profileAddress.setVisibility(View.VISIBLE);
                promptLogin.setVisibility(View.GONE);
                btnLogin.setVisibility(View.GONE);
                profilePage.setVisibility(View.VISIBLE);
                loginPage.setVisibility(View.GONE);
            }
        }
//        // Replace with actual user ID or logic to get the current user's ID
//        int userId = 3;
//
//        User user = databaseHelper.getUser(userId);
//        Log.d("DEBUG1", user.toString());
//        if (user != null) {
//            profileUsername.setText(user.getName());
//            profileEmail.setText(user.getEmail());
//            profilePhone.setText(user.getPhoneNumber());
//            profileAddress.setText(user.getAddress());
//            // Set profile image resource if available
//            profileImage.setImageResource(R.drawable.user_1);
//        } else {
//            Log.d("DEBUG1", "Không tìm thấy người dùng!");
//        }
    }

    private void logoutUser() {
        // Clear SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        // Redirect to login screen
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
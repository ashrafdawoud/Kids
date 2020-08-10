package com.example.faroukproject.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.faroukproject.CategoryName;
import com.example.faroukproject.IntroWithLog.LogInActivity;
import com.example.faroukproject.R;
import com.example.faroukproject.Test.TestLevels;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {
    String name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
             name = user.getDisplayName();
             email = user.getEmail();
            Log.e("email",email+" "+name);

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }


    }

    public void practice(View view) {
        Intent intent=new Intent(this, CategoryName.class);
        startActivity(intent);
    }

    public void Exam(View view) {
        Intent intent=new Intent(this, TestLevels.class);
        startActivity(intent);
    }

    public void Statistics(View view) {
        Intent intent=new Intent(this, KidsZone.class);
        startActivity(intent);
    }

    public void chat(View view) {
        Intent intent=new Intent(this, DoctorsActivity.class);
        startActivity(intent);
    }

    public void profile(View view) {
        Intent intent=new Intent(this, ProfileActivity.class);
        intent.putExtra("email",email);
        startActivity(intent);

    }
}

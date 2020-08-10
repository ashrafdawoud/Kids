package com.example.faroukproject.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.faroukproject.R;

public class DoctorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

    }
    public void openChat1(View view) {
        Intent intent=new Intent(this, ChatActivity.class);
        intent.putExtra("index",1);
        startActivity(intent);
    }

    public void openChat2(View view) {
        Intent intent=new Intent(this, ChatActivity.class);
        intent.putExtra("index",2);
        startActivity(intent);
    }

    public void openChat3(View view) {
        Intent intent=new Intent(this, ChatActivity.class);
        intent.putExtra("index",3);
        startActivity(intent);
    }

    public void openChat4(View view) {
        Intent intent=new Intent(this, ChatActivity.class);
        intent.putExtra("index",4);
        startActivity(intent);
    }
}

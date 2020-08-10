package com.example.faroukproject.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.faroukproject.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatActivity extends AppCompatActivity  {
int key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            key=extras.getInt("index");
        }
        CircleImageView circleImageView=findViewById(R.id.profile_image1);
        TextView textView =findViewById(R.id.name);
        switch (key){
            case 1 :
                circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.d1));
                textView.setText("D Ahmed");
                break;
            case 2 :
                circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.d2));
                textView.setText("D Mohamed");
                break;
            case 3 :
                circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.d3));
                textView.setText("D Sara");
                break;
            case 4 :
                circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.d4));
                textView.setText("D Aya");
                break;
        }
    }


    public void back(View view) {
        finish();
    }
}



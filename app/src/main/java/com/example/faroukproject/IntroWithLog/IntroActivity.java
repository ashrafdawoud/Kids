package com.example.faroukproject.IntroWithLog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TableLayout;

import com.example.faroukproject.Adapters.FragmentAdapter;
import com.example.faroukproject.HomePage.HomePage;
import com.example.faroukproject.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {
    FragmentAdapter fragmentAdapter;
    ViewPager viewPager;
    Button creatacount;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    String mUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        fragmentAdapter = new FragmentAdapter (getSupportFragmentManager ( ));
        viewPager = findViewById (R.id.viewpager);
        viewPager.setAdapter (fragmentAdapter);
        TabLayout tabLayout = (TabLayout) findViewById (R.id.tablayout);
        tabLayout.setupWithViewPager (viewPager, true);
        creatacount=findViewById(R.id.creatacount);//end the code of view pager
        creatacount.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.creatacount:
              //  Intent intent=new Intent(this,LogInActivity.class);
               // startActivity(intent);
                mFirebaseAuth = FirebaseAuth.getInstance();
                mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser == null) {
                    // Not signed in, launch the Sign In activity
                    startActivity(new Intent(this, LogInActivity.class));
                    finish();
                    return;
                } else {
                    mUsername = mFirebaseUser.getDisplayName();
                    if (mFirebaseUser.getPhotoUrl() != null) {
                       // mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
                    }
                    startActivity(new Intent(this, HomePage.class));
                    finish();
                }
                break;
        }

    }
}

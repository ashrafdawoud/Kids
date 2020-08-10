package com.example.faroukproject.HomePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.faroukproject.IntroWithLog.LogInActivity;
import com.example.faroukproject.R;
import com.example.faroukproject.Test.TestLevels;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser mFirebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences sharedPreferences=ProfileActivity.this.getSharedPreferences("user",MODE_PRIVATE);
            TextView textView=findViewById(R.id.email);
            textView.setText(sharedPreferences.getString("useremail",""));
            TextView textView1=findViewById(R.id.username);
            textView1.setText(sharedPreferences.getString("username",""));


        }



    public void logout(View view) {
        SharedPreferences sharedPreferences=ProfileActivity.this.getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
            Intent intent=new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();


    }
    public void change_password() {
        Rect displayRectangle = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
       View dialogViewPlaystore = LayoutInflater.from(this).inflate(R.layout.change_password, viewGroup, false);
        builder.setView(dialogViewPlaystore);
        final AlertDialog alertDialogPlaystore = builder.create();
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(alertDialogPlaystore.getWindow().getAttributes());
        wm.width = (int) (displayRectangle.width() * 0.9f);
        //wm.width=WindowManager.LayoutParams.MATCH_PARENT;
        wm.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wm.gravity = Gravity.CENTER_HORIZONTAL;
        wm.dimAmount = 0.60f;
        EditText oldpass=dialogViewPlaystore.findViewById(R.id.oldPass);
        final EditText newpass=dialogViewPlaystore.findViewById(R.id.newPass);
        Button change=dialogViewPlaystore.findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String newPassword = newpass.getText().toString();

                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.e("changepass","done");
                            }
                        });
                alertDialogPlaystore.dismiss();
            }
        });

        alertDialogPlaystore.show();
        alertDialogPlaystore.getWindow().setAttributes(wm);

        alertDialogPlaystore.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        alertDialogPlaystore.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void change_password(View view) {
        change_password();
    }

    public void chat(View view) {
        Intent intent=new Intent(this, DoctorsActivity.class);
        startActivity(intent);
        finish();
    }
}

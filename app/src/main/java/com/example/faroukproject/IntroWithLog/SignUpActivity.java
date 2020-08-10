package com.example.faroukproject.IntroWithLog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.faroukproject.CategoryName;
import com.example.faroukproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email, password,repassword,name;
    String nameText,emailtext,passwordtext,repasswordtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
        name=findViewById(R.id.name);



    }

    public void Login(View view) {
        Intent intent=new Intent(SignUpActivity.this,LogInActivity.class);
        startActivity(intent);
        finish();

    }
    public void signup(View view) {
        nameText=name.getText().toString();
        emailtext=email.getText().toString();
        passwordtext=password.getText().toString();
        repasswordtext=repassword.getText().toString();
        Log.e("emailtext",emailtext);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Student");
        String emailtext=email.getText().toString();
        final String passwordtext=password.getText().toString();
        query.whereEqualTo("email", emailtext);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject player, ParseException e) {
                if (e == null) {
                    Toast.makeText(SignUpActivity.this, "you have signed in before ", Toast.LENGTH_SHORT).show();
                } else {
                    signup();
                }
            }
        });
    }
    public void signup() {
       /* mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent intent=new Intent(SignUpActivity.this, LogInActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });*/
        ParseInstallation.getCurrentInstallation().saveInBackground();
        //////////////////////////////////////////////////
        final ParseObject soccerPlayers = new ParseObject("Student");
// Store an object
        if (passwordtext.equals(repasswordtext)&&!passwordtext.equals("")&&!repasswordtext.equals("")) {
            Log.e("emailtext",emailtext);
            if (emailtext.contains("@gmail.com")||emailtext.contains("@yahoo.com")) {
                soccerPlayers.put("email", emailtext);
                soccerPlayers.put("password", passwordtext);
                soccerPlayers.put("name", nameText);
                Toast.makeText(SignUpActivity.this, "sign up successfully ", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(SignUpActivity.this, "email is not correct ", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(SignUpActivity.this, "password not matched", Toast.LENGTH_SHORT).show();
        }
// Saving object
        soccerPlayers.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Success
                } else {
                    // Error
                }
            }
        });
    }
}

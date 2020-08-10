package com.example.faroukproject.IntroWithLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.faroukproject.CategoryName;
import com.example.faroukproject.HomePage.HomePage;
import com.example.faroukproject.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LogInActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth mAuth;
    EditText email, password;
GoogleApiClient mGoogleApiClient;
    private FirebaseUser mFirebaseUser;
    public static final int RC_SIGN_IN = 10;
    String mUsername;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        FirebaseApp.initializeApp(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        sharedPreferences=LogInActivity.this.getSharedPreferences("user",MODE_PRIVATE);
        if (!sharedPreferences.getString("useremail","").equals("")){
            Intent intent=new Intent(LogInActivity.this,HomePage.class);
            startActivity(intent);
            finish();
        }
     /*   mFirebaseAuth = FirebaseAuth.getInstance();
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
            Intent intent=new Intent(this, HomePage.class);
            intent.putExtra("email",mFirebaseUser.getEmail());
            startActivity(intent);
            finish();
        }*/

      //  mGoogleApiClient = new GoogleApiClient.Builder(this)
          //     .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
         //          @Override
          //         public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

          //         }
          //     }/* OnConnectionFailedListener */)
           //    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
             //   .build();

    }//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
       /* if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign-In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign-In failed
                Log.e("", "Google Sign-In failed.");
            }
        }*/
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("", "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("", "signInWithCredential", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(LogInActivity.this, HomePage.class));
                            finish();
                        }
                    }
                });
    }

    public void signup(View view) {
        Intent intent=new Intent(LogInActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
       /* if (!email.getText().equals("")&&!password.getText().equals("")) {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                // Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                                // startActivityForResult(signInIntent,RC_SIGN_IN);
                                Intent intent = new Intent(LogInActivity.this, HomePage.class);
                                startActivity(intent);
                                // updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LogInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });*/
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Student");
        final String emailtext=email.getText().toString();
        final String passwordtext=password.getText().toString();
        query.whereEqualTo("email", emailtext);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject player, ParseException e) {
                if (e == null) {
                    Log.e("click", "true");
                    if (player.getString("password").equals(passwordtext)){
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("useremail",emailtext);
                        editor.putString("userpassword",passwordtext);
                        editor.putString("username",player.get("name").toString());
                        editor.putString("userId", player.getString("objectId"));
                        editor.commit();
                        Intent intent=new Intent(LogInActivity.this,HomePage.class);
                        startActivity(intent);
                        Toast.makeText(LogInActivity.this,"Success login ",Toast.LENGTH_LONG).show();
                        finish();
                    }else {
                        Toast.makeText(LogInActivity.this,"Worng password",Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Something is wrong
                    Log.e("return2", "true");
                    Toast.makeText(LogInActivity.this,"we didn't have this account",Toast.LENGTH_LONG).show();
                }
            }
        });
        }


    }



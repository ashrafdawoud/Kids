package com.example.faroukproject.HomePage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.faroukproject.Adapters.KidsAdapter;
import com.example.faroukproject.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KidsZone extends AppCompatActivity {
    EditText searchtext;
    ArrayList<String> image=new ArrayList<>();
    ArrayList<String>text=new ArrayList<>();
    ArrayList<String> imagecopy=new ArrayList<>();
    ArrayList<String>textcopy=new ArrayList<>();
    RecyclerView recyclerView;
    KidsAdapter kidsAdapter;
    TextToSpeech tts;
    int position2;
     Thread thread;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids_zone);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        searchtext = findViewById(R.id.searchbox);
        recyclerView=findViewById(R.id.recy);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setHasFixedSize(true);
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               s=String.valueOf(charSequence);
                findCategory();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void back(View view) {
        finish();
    }

    public void clear(View view) {
        searchtext.setText("");
    }

    public void voice(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        startActivityForResult(intent, 10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
           String Data=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            searchtext.setText(String.valueOf(Data.charAt(0)));

        } else {
            Toast.makeText(getApplicationContext(), "Failed to recognize speech!", Toast.LENGTH_LONG).show();
        }
    }
    public void findCategory() {
        if (!s.equals("")) {
            image.clear();
            text.clear();
            imagecopy.clear();
            textcopy.clear();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Kids");
            //sorting object, ordering it by level number
            //query.orderByAscending("level_number");
            query.setLimit(1000);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> result, ParseException e) {
                    if (e == null) {
                        Log.e("textchange", result.size()+"");
                        for (int i = 0; i < result.size(); i++) {
                            image.add(result.get(i).getString("image_url"));
                            text.add(result.get(i).getString("text"));
                        }
                        for (int i = 0; i < image.size(); i++){
                            String s2= String.valueOf(text.get(i).charAt(0));
                            Log.e("textchange", s+"  "+s2);
                            if (s2.equalsIgnoreCase(s)){
                                imagecopy.add(image.get(i));
                                textcopy.add(text.get(i));
                                Log.e("practicelist__", String.valueOf(text.get(i)));
                            } }
                        Log.e("array item", String.valueOf(image.size()));
                        if (imagecopy.size()!=0) {
                            ImageView speak = findViewById(R.id.speak);
                            speak.setVisibility(View.VISIBLE);
                        }
                        kidsAdapter = new KidsAdapter(imagecopy, textcopy, KidsZone.this);
                        recyclerView.setAdapter(kidsAdapter);
                    } else {
                        Log.e("errhere", e.getMessage());

                    }
                }

            });
        }else {
            ImageView speak =findViewById(R.id.speak);
            speak.setVisibility(View.INVISIBLE);
            image.clear();
            text.clear();
            kidsAdapter = new KidsAdapter(image, text, KidsZone.this);
            recyclerView.setAdapter(kidsAdapter);
        }

    }

    public void speak(View view) {
         position2=0;
      speak(position2);
    }
    void speak(final int postition){

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(final int i) {
                if (i == TextToSpeech.SUCCESS) {
                    String text="";
                     thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                while(true) {
                                    for (int i1=0;i1<textcopy.size();i1++){
                     /*   Handler handler = new Handler();
                        Runnable r = new Runnable() {
                            public void run() {
                                boolean flag=true;
                                // text=text+" "+textcopy.get(i1).toString();
                                tts.setLanguage(Locale.US);
                                if (textcopy.get(postition)!=null)
                                    tts.speak(textcopy.get(i), TextToSpeech.QUEUE_FLUSH, null);
                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                                    @Override
                                    public void onStart(String s) {

                                    }

                                    @Override
                                    public void onDone(String s) {

                                    }

                                    @Override
                                    public void onError(String s) {

                                    }
                                });
                            }
                        };
                        handler.postDelayed(r, 1000);*/
                                        if (tts.isSpeaking())
                                            tts.stop();
                                        if (textcopy.get(postition)!=null)
                                            tts.speak(textcopy.get(i1), TextToSpeech.QUEUE_FLUSH, null);
                                        Log.e("postDelayed","postDelayed");
                                        sleep(2000);
                                    }
                                    thread.interrupt();

                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();

                }
            }
        });

    }

    @Override
    protected void onDestroy() {

        if (tts!=null)
        tts.stop();
        if (thread!=null)
        thread.interrupt();
        super.onDestroy();
    }

}

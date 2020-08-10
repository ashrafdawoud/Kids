package com.example.faroukproject.Test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faroukproject.CategoryName;
import com.example.faroukproject.Model.Practice;
import com.example.faroukproject.R;
import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;

public class Prounnounce_exam extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_AUDIO_PERMISSION = 10151;
    private SpeechRecognizer speechRecognizer;
    ImageView imageView1,imageView2,imageView3,imageView4;
    ProgressBar progressBarQuiz;
    LinearLayout imageViewNextQuestion;
    ImageView imageViewLeftArrow;
    TextView textViewLevelName;
    private List<String> quizList;
    int questionNumber=0;
    private String correctAnswer;
    private int progressStatus = 0;
    private boolean allAnswersCompleted = false;
    int num_correct_ans=0;
    SharedPreferences sharedPreferences;
    private Integer levelNumber;
    private boolean shouldRepeatAnimation = true;
String queryLanguage;
    int num_of_levels;
    List<Practice> practiceList = new ArrayList<>();
    int position,positionCopy;
    String answer_option;
    ArrayList<String> result;
    ImageView rightORwrong;
    int rightAnswer=0;
    boolean right=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prounnounce_exam);
        textViewLevelName = findViewById(R.id.text_view_quiz_level_name);
        imageView1 = findViewById(R.id.image_view_quiz_answer_a);
            imageView2 = findViewById(R.id.image_view_quiz_answer_b);
            imageView3 = findViewById(R.id.image_view_quiz_answer_c);
            imageView4 = findViewById(R.id.image_view_quiz_answer_d);
            rightORwrong=findViewById(R.id.rightOrwrong);
            imageViewLeftArrow = findViewById(R.id.image_view_quiz_left_arrow);
            imageViewNextQuestion = findViewById(R.id.image_view_next_question);
            progressBarQuiz = findViewById(R.id.progress_bar_quiz);
            imageViewLeftArrow.setOnClickListener(this);
            imageViewNextQuestion.setOnClickListener(this);
            imageViewLeftArrow.setClickable(false);
            imageViewNextQuestion.setClickable(false);
            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if (extras == null) {

                } else {
             /*   levelNumber = extras.getInt("LEVEL_NUMBER");
                position=extras.getInt("POSITION");
                positionCopy=position;
                stateName=extras.getString("STATE_QUIZ_NAME");
                key=extras.getString("key");
                //Log.e("position",String.valueOf(position+" "+stateName));*/
                    quizList=extras.getStringArrayList("LEVEL_QUIZ");
                    textViewLevelName.setText(extras.getString("Name"));

                }
            }
            Log.e("quez",quizList.toString());
            getQueryLanguage();
            //load_next_Leavel(stateName,queryLanguage);
            // quizList=practiceList.get(position).getQuiz();
            //set max value of progress bar depending on quizList size
            loadQuestionAndAnswerscolor();
            progressBarQuiz.setMax(quizList.size());
            hideNextQuestionView();
            updateQuizProgressBar();

    }

    public void speek(View view) {
        startListening();
    }

    private void startListening() {
        //Intent to listen to user vocal input and return result in same activity
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        //Use a language model based on free-form speech recognition.
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        //Message to display in dialog box
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "speech_to_text_info");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "speech_not_supported",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && null != data) {
                     result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (answer_option.equals(result.get(0).toLowerCase())||result.get(0).toLowerCase().contains(answer_option)){
                        rightORwrong.setVisibility(View.VISIBLE);
                        rightORwrong.setImageDrawable(getResources().getDrawable(R.drawable.right));
                        right=true;
                        num_correct_ans+=1;
                    }else {
                        rightORwrong.setVisibility(View.VISIBLE);
                        rightORwrong.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
                        Log.e("wrongAnswer",answer_option+"  "+result.get(0));
                    }
                    Log.e("onresult",result.get(0));
                    enableNextQuestionViewClick();
                    unHideNextQuestionView();
                }
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.image_view_quiz_letter_a:
            case R.id.constraint_layout_quiz_answer_a:
            case R.id.text_view_quiz_answer_a:
            case R.id.image_view_quiz_answer_a:


                break;


            case R.id.image_view_quiz_letter_b:
            case R.id.constraint_layout_quiz_answer_b:
            case R.id.text_view_quiz_answer_b:
            case R.id.image_view_quiz_answer_b:

                break;


            case R.id.image_view_quiz_letter_c:
            case R.id.constraint_layout_quiz_answer_c:
            case R.id.text_view_quiz_answer_c:
            case R.id.image_view_quiz_answer_c:


                break;


            case R.id.image_view_quiz_letter_d:
            case R.id.constraint_layout_quiz_answer_d:
            case R.id.text_view_quiz_answer_d:


                break;

            case R.id.image_view_quiz_left_arrow:
                finish();
                break;


            case R.id.image_view_next_question:
                if (right){
                    rightAnswer+=1;
                    Log.e("rightAnswer",String.valueOf(rightAnswer));
                }
                    rightORwrong.setVisibility(View.GONE);
                    right=false;
                    if (!allAnswersCompleted) {
                    imageView1.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.VISIBLE);
                    imageView4.setVisibility(View.VISIBLE);
                    enableSingleClick();
                    loadQuestionAndAnswerscolor();
                    updateQuizProgressBar();
                    stopAnimationNextQuestion();

                }else{
                    //this code called if user click next level
                  /*  allAnswersCompleted = false;
                    quizList = null;
                    questionNumber = 0;
                    progressStatus = 0;
                    position++;
                    levelNumber++;
                    quizList = practiceList.get(position).getQuiz();
                    enableSingleClick();
                    loadQuestionAndAnswerscolor();
                    updateQuizProgressBar();
                    if (Locale.getDefault().getLanguage().equals("hi")){
                    }
                    textViewLevelName.setText(practiceList.get(position).getLevel_name());
                    Log.e("position ____", String.valueOf(position + "  " + quizList.size() + " " + practiceList.get(position).getLevel_number()));

               */
                /*  if (rightAnswer>7){
                      RightAnswer();
                  }else {
                      WrongAnswer();
                  }*/
                        WrongAnswer2(rightAnswer);

                        }

                break;

            default:
                break;
        }

    }

    /** Values are placed in Json Array.
     * Json Objects inside Json Array
     * Need to retrieve object position from "quizList" json array via getJSONArray.
     * This method is the responsible for loading new questions in the activity
     */
    private void loadQuestionAndAnswers() {
        String question,answerA,answerB,answerC,answerD,imageUrl;
        try {
            JSONArray jsonArray= new JSONArray(quizList);
            Log.e("next level",String.valueOf(quizList));
            JSONObject json_data = jsonArray.getJSONObject(questionNumber);
            question = json_data.getString("question");
            answerA = json_data.getString("answer_a");
            answerB = json_data.getString("answer_b");
            answerC = json_data.getString("answer_c");
            answerD = json_data.getString("answer_d");
            imageUrl = json_data.getString("image_url");

            //making this value global in this activity for being use in others methods(showcorrectAnswer)
            correctAnswer = json_data.getString("correct_answer");


            //method to know if answer D includes a value, if it's null(no answer), then hide the view
            checkValueAnswerD(answerD);
            loadUIcolor(question, answerA, answerB, answerC,answerD,imageUrl);

            loadDefaultColors();

            disableNextQuestionViewClick();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //increasing question number, every time the user click on "Next" will jump to next question
        questionNumber++;
    }
    private void loadQuestionAndAnswerscolor() {
        String question,answerA,answerB,answerC,answerD,imageUrl;

        try {
            JSONArray jsonArray= new JSONArray(quizList);
            Log.e("next level",String.valueOf(quizList));
            JSONObject json_data = jsonArray.getJSONObject(questionNumber);
            question = json_data.getString("question");
            answerA = json_data.getString("answer_a");
            answerB = json_data.getString("answer_b");
            answerC = json_data.getString("answer_c");
            answerD = json_data.getString("answer_d");
            imageUrl = json_data.getString("image_url");
            answer_option=json_data.getString("answer_option");
            Log.e("answer_option",answer_option);
            //making this value global in this activity for being use in others methods(showcorrectAnswer)
            correctAnswer = json_data.getString("correct_answer");


            //method to know if answer D includes a value, if it's null(no answer), then hide the view
            checkValueAnswerD(answerD);
            loadUIcolor(question, answerA, answerB, answerC,answerD,imageUrl);

            loadDefaultColors();

            disableNextQuestionViewClick();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //increasing question number, every time the user click on "Next" will jump to next question
        questionNumber++;
        if (questionNumber==11){
            allAnswersCompleted = true;
        }
    }

    //load views with data from the json array
    private void loadUIcolor(String question, String answerA, String answerB, String answerC, String answerD,String imageUrl){
        Log.e("ansewer_a",answerA+" "+answerB);
        Picasso.with(this).load(answerA).into(imageView1);
        Picasso.with(this).load(answerB).into(imageView2);
        Picasso.with(this).load(answerC).into(imageView3);
        Picasso.with(this).load(answerD).into(imageView4);
        checkValueAnswerD(answerD);
        if (answerA.equals("null"))
            imageView1.setVisibility(View.GONE);
        if (answerB.equals("null"))
            imageView2.setVisibility(View.GONE);
        if (answerC.equals("null"))
            imageView3.setVisibility(View.GONE);
        if (answerD.equals("null"))
            imageView4.setVisibility(View.GONE);
        if (imageUrl.equals("null"))
        {
            Log.e("errorImage", "error");
            hideQuestionImageUrl();
        }
        else
        {
            Log.e("errorImage", imageUrl);
            unHideQuestionImageUrl();
            //Picasso.with(this).load(imageUrl).error(R.drawable.ic_green_answer_d)..into(imageViewQuestionImageUrl);
            Picasso.Builder builder=new Picasso.Builder(getApplicationContext());
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    Log.e("error with pic",exception.getMessage());
                }
            });
            Picasso picasso= builder.build();
            Log.e("image",imageUrl);
        }
        //////////////////to handel multiple click///////////////////////////
        imageViewLeftArrow.setClickable(true);
        imageViewNextQuestion.setClickable(true);
        //pbar.setVisibility(View.GONE);

    }


    //draw green the constraintLayout background and the answer icon to show the correct answer

    //method called in order to update progressBar every time a new question is loaded
    private void updateQuizProgressBar()
    {
        if(progressStatus< quizList.size()) {
            progressStatus++;
            progressBarQuiz.setProgress(progressStatus);


        }
        else{

            final View viewPos = findViewById(R.id.myCoordinatorLayout);
            disableMultipleClicks();
            allAnswersCompleted = true;
            if (num_correct_ans>=quizList.size()*80/100){
                SharedPreferences.Editor editor=sharedPreferences.edit();
                int i=levelNumber-1;
                editor.putBoolean(String.valueOf(i),true);
                editor.commit();
                Log.e("passed2",num_correct_ans+"-"+levelNumber);
                Snackbar snackbar = Snackbar.make(viewPos,"you pass this exam ", Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(Prounnounce_exam.this, R.color.redBright));
                snackbar.show();

            }else {
                Snackbar snackbar = Snackbar.make(viewPos,"you didn't pass this exam", Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(Prounnounce_exam.this, R.color.redBright));
                snackbar.show();
            }
            imageViewNextQuestion.setVisibility(View.GONE);
            num_correct_ans=0;
            //   editor.putInt("level_position",-1);
            //   editor.putInt("questionNumber",-1);

        }
    }

    private void startAnimationNextQuestion()
    {
        shouldRepeatAnimation = true;
        AlphaAnimation fadeIn=new AlphaAnimation(0,1);
        AlphaAnimation fadeOut=new AlphaAnimation(1,0);
        final AnimationSet set = new AnimationSet(false);
        set.addAnimation(fadeIn);
        set.addAnimation(fadeOut);

        fadeOut.setStartOffset(1000);
        set.setDuration(2000);

        imageViewNextQuestion.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }
            @Override
            public void onAnimationRepeat(Animation animation) { }
            @Override
            public void onAnimationEnd(Animation animation) {
                if (shouldRepeatAnimation) {
                    imageViewNextQuestion.startAnimation(set);
                }
            }
        });

    }

    private void stopAnimationNextQuestion()
    {
        shouldRepeatAnimation = false;
        imageViewNextQuestion.clearAnimation();
    }


    //loading default colors of both, constraintLayout background and icon number
    private void loadDefaultColors(){

        //set default colors every time user press Next

    }

    //disabling click event of answers after choosing one, prevents multiple clicks
    private void disableMultipleClicks(){




    }

    //enabling click events of answers after pressing in Next
    private void enableSingleClick(){


    }


    private void enableNextQuestionViewClick()
    {
        imageViewNextQuestion.setClickable(true);
    }

    private void disableNextQuestionViewClick()
    {
        imageViewNextQuestion.setClickable(false);
    }

    private void checkValueAnswerD(String answerD)
    {Log.e("D",answerD);
        if (answerD.equals("null"))
        {
            hideAnswer();
        }
        else
        {
            unHideAnswer();
        }
    }


    private void hideAnswer(){


    }

    private void unHideAnswer()
    {

    }

    private void hideQuestionImageUrl()
    {
    }

    private void unHideQuestionImageUrl()
    {
    }

    private void hideNextQuestionView()
    {
        imageViewNextQuestion.setVisibility(View.GONE);
    }

    private void unHideNextQuestionView()
    {
        imageViewNextQuestion.setVisibility(View.VISIBLE);
    }



    @Override
    public void onBackPressed() {
        ////////////check internet connection /////////////////////////
            finish();

    }

    /* private void showDialog()
     {

         Rect displayRectangle = new Rect();
         Window window = PracticeQuizActivity.this.getWindow();
         window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
         final AlertDialog.Builder builder = new AlertDialog.Builder(PracticeQuizActivity.this,R.style.CustomAlertDialog);
         ViewGroup viewGroup = findViewById(android.R.id.content);
         dialogView2 = LayoutInflater.from(PracticeQuizActivity.this).inflate(R.layout.continue_level_dialog, viewGroup, false);
         builder.setView(dialogView2);
         alertDialog2 = builder.create();
         WindowManager.LayoutParams wm= new WindowManager.LayoutParams ();
         wm.copyFrom(alertDialog2.getWindow ().getAttributes ());
         wm.width=(int)(displayRectangle.width ()*0.9f);
         //wm.width=WindowManager.LayoutParams.MATCH_PARENT;
         wm.height=WindowManager.LayoutParams.WRAP_CONTENT;
         wm.gravity= Gravity.CENTER_HORIZONTAL;
         wm.dimAmount=0.60f;
         final CardView yes=dialogView2.findViewById(R.id.btnClickRetry);
         final CardView no=dialogView2.findViewById(R.id.btnClickOk);
         TextView text=dialogView2.findViewById(R.id.text);
         text.setText(getString(R.string.do_you_want_111) +" "+ String.valueOf(levelNumber) + "?");
         yes.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 yes.startAnimation(clickanim);
                 clickanim.setAnimationListener(new Animation.AnimationListener() {
                     @Override
                     public void onAnimationStart(Animation animation) {
                     }
                     @Override
                     public void onAnimationEnd(Animation animation) {
                         if (!allAnswersCompleted){
                             Log.e("position22",questionNumber+" "+sharedPreferences.getInt("questionNumber",0)+"position "+position);
                             //if (questionNumber>sharedPreferences.getInt("questionNumber",0)){
                             questionNumber-=1;
                             SharedPreferences.Editor editor=sharedPreferences.edit();
                             editor.putInt(levelNumber+"level_position",position);
                             editor.putInt(levelNumber+"questionNumber",questionNumber);
                             editor.commit();
                             Log.e("position3", String.valueOf(sharedPreferences.getInt("level_position",-1)));}
                         //}
                         alertDialog2.dismiss();
                         finish();
                     }
                     @Override
                     public void onAnimationRepeat(Animation animation) {
                     }
                 });


             }
         });
         no.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 no.startAnimation(clickanim);
                 clickanim.setAnimationListener(new Animation.AnimationListener() {
                     @Override
                     public void onAnimationStart(Animation animation) {

                     }

                     @Override
                     public void onAnimationEnd(Animation animation) {
                         alertDialog2.dismiss();

                     }

                     @Override
                     public void onAnimationRepeat(Animation animation) {

                     }
                 });

             }
         });

         alertDialog2.show();
         alertDialog2.getWindow ().setAttributes (wm);
         alertDialog2.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
         //  alertDialog2.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
         alertDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         alertDialog2.setCancelable(false);




     }
     private void noInternetDialog(){
         Rect displayRectangle = new Rect();
         Window window = PracticeQuizActivity.this.getWindow();
         window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
         final AlertDialog.Builder builder = new AlertDialog.Builder(PracticeQuizActivity.this,R.style.CustomAlertDialog);
         ViewGroup viewGroup = findViewById(android.R.id.content);
         dialogView = LayoutInflater.from(PracticeQuizActivity.this).inflate(R.layout.alert_dialog_no_connection, viewGroup, false);
         builder.setView(dialogView);
         alertDialog = builder.create();
         WindowManager.LayoutParams wm= new WindowManager.LayoutParams ();
         wm.copyFrom(alertDialog.getWindow ().getAttributes ());
         wm.width=(int)(displayRectangle.width ()*0.9f);
         //wm.width=WindowManager.LayoutParams.MATCH_PARENT;
         wm.height=WindowManager.LayoutParams.WRAP_CONTENT;
         wm.gravity= Gravity.CENTER_HORIZONTAL;
         wm.dimAmount=0.60f;
         final CardView retry=dialogView.findViewById(R.id.btnClickRetry);
         final CardView ok=dialogView.findViewById(R.id.btnClickOk);
         retry.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 retry.startAnimation(clickanim);

                 new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         if (InternetConnection.checkConnection(getApplicationContext())) {
                             alertDialog.dismiss();

                         }else {
                             startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                         }                            }
                 }, 260);



             }
         });
         ok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ok.startAnimation(clickanim);
                 clickanim.setAnimationListener(new Animation.AnimationListener() {
                     @Override
                     public void onAnimationStart(Animation animation) {

                     }

                     @Override
                     public void onAnimationEnd(Animation animation) {

                         alertDialog.dismiss();
                         finishAffinity();
                     }

                     @Override
                     public void onAnimationRepeat(Animation animation) {

                     }
                 });


             }
         });
         alertDialog.show();
         alertDialog.getWindow ().setAttributes (wm);
         alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
         //  alertDialog2.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
         alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         alertDialog.setCancelable(false);
         alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
             @Override
             public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                 if (i == KeyEvent.KEYCODE_BACK) {
                     if (InternetConnection.checkConnection(getApplicationContext())) {
                         alertDialog.dismiss();
                     }else {finishAffinity();}
                     /*alertDialog.dismiss();
                     finishAffinity();
                     Log.e("pressed","true");}
                 }
                 return true;
             }
         });




     }*/
    private void load_next_Leavel(final String stateQuizName,String queryLanguage) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(queryLanguage);
        //sorting object, ordering it by level number
        query.orderByAscending("level_number");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> result, ParseException e) {

                if (e == null) {
                    num_of_levels=result.size();
                    for (int i = 0; i < result.size(); i++) {
                        Practice practice = new Practice();
                        practice.level_number = result.get(i).getNumber("level_number");
                        practice.level_name = result.get(i).getString("level_name");
                        practice.level_color = result.get(i).getString("level_color");
                        //retrieving array of quiz using the state name
                        practice.quiz = result.get(i).getList(stateQuizName);

                        //send result data to adapter->recyclerView
                        practiceList.add(practice);
                    }
                    quizList=practiceList.get(position).getQuiz();
                    loadQuestionAndAnswerscolor();
                    progressBarQuiz.setMax(quizList.size());
                    hideNextQuestionView();
                    updateQuizProgressBar();



                } else {
                    // something went wrong
                }


            }
        });

    }
    private void getQueryLanguage()
    {

        Locale.getDefault().getDisplayLanguage();
        if(Locale.getDefault().getLanguage().equals("en"))
        {
            queryLanguage="Practice_EN";
        }
        if(Locale.getDefault().getLanguage().equals("hi"))
        {
            queryLanguage="Practice_HI";
        }
        else
        {
            queryLanguage="Practice_EN";
        }
    }


  /*  private void continuelevel(){
        Rect displayRectangle = new Rect();
        Window window = TestExam.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(PracticeQuizActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView1 = LayoutInflater.from(PracticeQuizActivity.this).inflate(R.layout.continue_level_dialog, viewGroup, false);
        //  dialogView.setMinimumWidth((int)(displayRectangle.width()*1f ));
        //  dialogView.setMinimumHeight((int)(displayRectangle.height() * 0.7f));


        builder.setView(dialogView1);
        alertDialog1 = builder.create();

        WindowManager.LayoutParams wm= new WindowManager.LayoutParams ();
        wm.copyFrom(alertDialog1.getWindow ().getAttributes ());
        wm.width=(int)(displayRectangle.width ()*0.9f);
        //wm.width=WindowManager.LayoutParams.MATCH_PARENT;
        wm.height=WindowManager.LayoutParams.WRAP_CONTENT;
        wm.gravity= Gravity.CENTER_HORIZONTAL;
        wm.dimAmount=0.60f;
        final CardView yes=dialogView1.findViewById(R.id.btnClickRetry);
        final CardView no=dialogView1.findViewById(R.id.btnClickOk);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.startAnimation(clickanim);
                clickanim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        pbar.setVisibility(View.VISIBLE);
                        yes.startAnimation(clickanim);
                        questionNumber=sharedPreferences.getInt(levelNumber+"questionNumber",0)-1;
                        progressStatus=questionNumber;
                        load_next_Leavel(stateName,queryLanguage);
                        alertDialog1.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no.startAnimation(clickanim);
                clickanim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        no.startAnimation(clickanim);
                        //load_next_Leavel(stateName,queryLanguage);
                        alertDialog1.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


            }
        });




        alertDialog1.show();
        alertDialog1.getWindow ().setAttributes (wm);
        alertDialog1.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //  alertDialog2.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog1.setCancelable(false);

        alertDialog1.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {

                return true;
            }
        });


    }*/

    private void RightAnswer() {
        Rect displayRectangle = new Rect();
        Window window = Prounnounce_exam.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(Prounnounce_exam.this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View contactUsView = LayoutInflater.from(Prounnounce_exam.this).inflate(R.layout.sucsess_dialog, viewGroup, false);

        builder.setView(contactUsView);
        AlertDialog contactUsDialog = builder.create();

        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(contactUsDialog.getWindow().getAttributes());
        wm.width = (int) (displayRectangle.width() * 0.9f);
        wm.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wm.gravity = Gravity.CENTER_HORIZONTAL;
        wm.dimAmount = 0.60f;
        CardView exit=contactUsView.findViewById(R.id.exitexam);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        contactUsDialog.show();
        contactUsDialog.getWindow().setAttributes(wm);
        contactUsDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        contactUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        contactUsDialog.setCancelable(false);
    }
    private void WrongAnswer() {
        Rect displayRectangle = new Rect();
        Window window = Prounnounce_exam.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(Prounnounce_exam.this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View contactUsView1 = LayoutInflater.from(Prounnounce_exam.this).inflate(R.layout.wrong_answer, viewGroup, false);

        builder.setView(contactUsView1);
        AlertDialog contactUsDialog1 = builder.create();

        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(contactUsDialog1.getWindow().getAttributes());
        wm.width = (int) (displayRectangle.width() * 0.9f);
        wm.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wm.gravity = Gravity.CENTER_HORIZONTAL;
        wm.dimAmount = 0.60f;
        CardView exit=contactUsView1.findViewById(R.id.exitexam);
        CardView goToPractice=contactUsView1.findViewById(R.id.goToPractice);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        goToPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Prounnounce_exam.this, CategoryName.class);
                startActivity(intent);
                finish();
            }
        });
        contactUsDialog1.show();
        contactUsDialog1.getWindow().setAttributes(wm);
        contactUsDialog1.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        contactUsDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        contactUsDialog1.setCancelable(false);
    }

    private void WrongAnswer2(int persentage) {
        int t=quizList.size();
        int pers=(persentage*100)/t;
        Rect displayRectangle = new Rect();
        Window window = Prounnounce_exam.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(Prounnounce_exam.this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View contactUsView1 = LayoutInflater.from(Prounnounce_exam.this).inflate(R.layout.wrong_answer, viewGroup, false);

        builder.setView(contactUsView1);
        AlertDialog contactUsDialog1 = builder.create();

        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(contactUsDialog1.getWindow().getAttributes());
        wm.width = (int) (displayRectangle.width() * 0.9f);
        wm.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wm.gravity = Gravity.CENTER_HORIZONTAL;
        wm.dimAmount = 0.60f;
        ImageView smill=contactUsView1.findViewById(R.id.smill);
        TextView textView=contactUsView1.findViewById(R.id.percentage);
        Log.e("persentage",persentage+"");
        if (persentage>=(t*80)/100){
            smill.setImageDrawable(getResources().getDrawable(R.drawable.verygreen));
            Log.e("persentage",pers+"");
            textView.setText(pers+"%");
        }
        else if (persentage>=(t*60)/100){
            smill.setImageDrawable(getResources().getDrawable(R.drawable.littlegreen));
            textView.setText(pers+"%");
        }
        else if (persentage>=(t*50)/100){
            smill.setImageDrawable(getResources().getDrawable(R.drawable.yello));
            textView.setText(pers+"%");
        }
        else if (persentage<(t*50)/100){
            smill.setImageDrawable(getResources().getDrawable(R.drawable.orange));
            textView.setText(pers+"%");
        }
        CardView exit=contactUsView1.findViewById(R.id.exitexam);
        CardView goToPractice=contactUsView1.findViewById(R.id.goToPractice);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        goToPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Prounnounce_exam.this, CategoryName.class);
                startActivity(intent);
                finish();
            }
        });
        contactUsDialog1.show();
        contactUsDialog1.getWindow().setAttributes(wm);
        contactUsDialog1.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        contactUsDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        contactUsDialog1.setCancelable(false);
    }





}

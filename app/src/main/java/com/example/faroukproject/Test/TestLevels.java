package com.example.faroukproject.Test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.faroukproject.Adapters.LevelNameAdapter;
import com.example.faroukproject.Model.Practice;
import com.example.faroukproject.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TestLevels extends AppCompatActivity {
    LevelNameAdapter levelNameAdapter;
    RecyclerView recyclerView;
    final List<Practice> practiceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_levels);
        //////////////////////////////////////////
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        ///////////////////////////////////////////////
        recyclerView=findViewById(R.id.levelsRecy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //recyclerView.setAdapter(levelNameAdapter);
        findexam();
    }
    public void findexam() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ExamLevel");
        //sorting object, ordering it by level number
        query.orderByAscending("level_number");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> result, ParseException e) {
                Log.e("result __",String.valueOf(result.size()));
                if (e == null) {
                    for (int i = 0; i < result.size(); i++) {

                        Practice practice = new Practice();
                        practice.level_number = result.get(i).getNumber("level_number");
                        practice.level_name = result.get(i).getString("level_name");
                        practice.level_color = result.get(i).getString("level_url");
                        //retrieving array of quiz using the state name
                        practice.quiz = result.get(i).getList("level_exam");
                        Log.e("result __", String.valueOf(result.get(i).getString("level_number")));
                       // Log.e("result __",String.valueOf(result.toArray()));
                        //send result data to adapter->recyclerView
                        practiceList.add(practice);
                    }

                } else {
                    // something went wrong
                }
                levelNameAdapter=new LevelNameAdapter(practiceList,TestLevels.this);
                recyclerView.setAdapter(levelNameAdapter);

            }
        });

    }

    public void back(View view) {
        finish();
    }
}

package com.example.faroukproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.faroukproject.Adapters.CategoryAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryName extends AppCompatActivity {
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    ArrayList<String>image=new ArrayList<>();
    ArrayList<String>text=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        //findCategory();
        recyclerView=findViewById(R.id.category);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        categoryAdapter=new CategoryAdapter(image,text,this);
        categoryAdapter.notifyDataSetChanged();
        findCategory();
    }
    public void findCategory() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Category");
        //sorting object, ordering it by level number
        //query.orderByAscending("level_number");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> result, ParseException e) {

                if (e == null) {
                    for (int i =0;i<result.size();i++){
                        image.add(result.get(i).getString("image_url"));
                        text.add(result.get(i).getString("name"));
                        Log.e("practicelist__",String.valueOf(image.get(i)));
                    }
                    Log.e("array item",String.valueOf(image.size()));
                    recyclerView.setAdapter(categoryAdapter);


                } else {
                    Log.e("errhere",e.getMessage());

                }
            }
        });

    }

    public void back(View view) {
        finish();
    }
}

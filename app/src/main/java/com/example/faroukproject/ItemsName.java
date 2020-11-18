package com.example.faroukproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.faroukproject.Adapters.CategoryAdapter;
import com.example.faroukproject.Adapters.ClickedItemAdapter;
import com.example.faroukproject.Adapters.ItemAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ItemsName extends AppCompatActivity implements ItemAdapter.clickListener{
    RecyclerView itemRecy,clickedRecy;
    ClickedItemAdapter clickedItemAdapter;
    ItemAdapter itemAdapter;
    ArrayList<String> image=new ArrayList<>();
    ArrayList<String>text=new ArrayList<>();
    ArrayList<String> imagecopy=new ArrayList<>();
    ArrayList<String>textcopy=new ArrayList<>();
    TextView title;
    int position;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_name);
        ///////////////////////////////////////////////
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            key=extras.getString("key");
        }
        title=findViewById(R.id.title);
        title.setText(key);
        ////////////////////////////////////////////////
        itemRecy=findViewById(R.id.itemrecy);
        itemRecy.setLayoutManager(new GridLayoutManager(this,2));
        itemRecy.setHasFixedSize(true);
        itemAdapter=new ItemAdapter(image,text,this);
        itemAdapter.setClickListener(this);
        findItem();
        itemAdapter.notifyDataSetChanged();
        clickedRecy=findViewById(R.id.clickedItems);
        clickedRecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,true));
        clickedRecy.setHasFixedSize(true);
        clickedItemAdapter=new ClickedItemAdapter(imagecopy,textcopy,this,position);
        clickedItemAdapter.notifyDataSetChanged();
        clickedRecy.setAdapter(clickedItemAdapter);



    }
    public void findItem() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Items");
        //sorting object, ordering it by level number
        //query.orderByAscending("level_number");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> result, ParseException e) {
                if (e == null) {
                    for (int i =0;i<result.size();i++){
                        if (result.get(i).getString(key+"_image")!=null){
                        image.add(result.get(i).getString(key+"_image"));
                        text.add(result.get(i).getString(key+"_name"));
                        Log.e("practicelist__",String.valueOf(image.get(i)));
                        }
                    }
                    Log.e("array item",String.valueOf(image.size()));
                    itemRecy.setAdapter(itemAdapter);
                } else {
                    Log.e("errhere",e.getMessage());
                }
            }
        });
    }
    @Override
    public void onItemClick(View view, int position) {
        imagecopy.add(image.get(position));
        textcopy.add(text.get(position));
        Log.e("position", String.valueOf(position));
        clickedRecy.getLayoutManager().scrollToPosition(imagecopy.size()-1);
        clickedItemAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

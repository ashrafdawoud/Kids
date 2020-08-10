package com.example.faroukproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faroukproject.Model.Practice;
import com.example.faroukproject.R;
import com.example.faroukproject.Test.Color_exam;
import com.example.faroukproject.Test.Prounnounce_exam;
import com.example.faroukproject.Test.TestExam;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LevelNameAdapter extends RecyclerView.Adapter<LevelNameAdapter.ViewHolder> {
    private List<Practice> practiceList;
    Context context;
    public LevelNameAdapter(List<Practice> practiceList,Context context) {
        this.practiceList = practiceList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(practiceList.get(position).getLevel_name());
        Picasso.with(context).load(practiceList.get(position).getLevel_color()).into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return practiceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            icon=itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (practiceList.get(getAdapterPosition()).getLevel_name().equals("color")){
                        Intent intent = new Intent(context, Color_exam.class);
                        intent.putStringArrayListExtra("LEVEL_QUIZ", (ArrayList<String>) practiceList.get(getAdapterPosition()).getQuiz());
                        intent.putExtra("Name", practiceList.get(getAdapterPosition()).getLevel_name());
                        context.startActivity(intent);
                    }else if(practiceList.get(getAdapterPosition()).getLevel_name().equals("pronounce ")){
                        Intent intent = new Intent(context, Prounnounce_exam.class);
                        intent.putStringArrayListExtra("LEVEL_QUIZ", (ArrayList<String>) practiceList.get(getAdapterPosition()).getQuiz());
                        intent.putExtra("Name", practiceList.get(getAdapterPosition()).getLevel_name());
                        context.startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(context, TestExam.class);
                        intent.putStringArrayListExtra("LEVEL_QUIZ", (ArrayList<String>) practiceList.get(getAdapterPosition()).getQuiz());
                        intent.putExtra("Name", practiceList.get(getAdapterPosition()).getLevel_name());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}

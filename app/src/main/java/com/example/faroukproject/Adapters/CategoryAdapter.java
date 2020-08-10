package com.example.faroukproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faroukproject.ItemsName;
import com.example.faroukproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<String>image=new ArrayList<>();
    ArrayList<String>name=new ArrayList<>();
    Context context;
    public CategoryAdapter(ArrayList<String>image, ArrayList<String>name, Context context) {
        this.image=image;
        this.name=name;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            holder.name.setText(name.get(position));
            Picasso.with(context).load(image.get(position)).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {

            }
            @Override
            public void onError() {
            }
        });
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ItemsName.class);
                    intent.putExtra("key",String.valueOf(name.get(position)));
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        CardView container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            container=itemView.findViewById(R.id.container);

        }
    }
}

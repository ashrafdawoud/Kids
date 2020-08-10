package com.example.faroukproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faroukproject.ItemsName;
import com.example.faroukproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class KidsAdapter extends RecyclerView.Adapter<KidsAdapter.ViewHolder> {
    ArrayList<String> image=new ArrayList<>();
    ArrayList<String>name=new ArrayList<>();
    Context context;
    public KidsAdapter(ArrayList<String>image, ArrayList<String>name, Context context) {
        this.image=image;
        this.name=name;
        this.context=context;
    }

    @NonNull
    @Override
    public KidsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent, false);
        return new KidsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KidsAdapter.ViewHolder holder, int position) {
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.zoom);
        holder.imageView.startAnimation(animation);
        holder.name.setText(name.get(position));
        Picasso.with(context).load(image.get(position)).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {

            }
            @Override
            public void onError() {
            }
        });
    }

    @Override
    public int getItemCount() {
        return image.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
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

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.zoom);
        holder.imageView.startAnimation(animation);
    }
}

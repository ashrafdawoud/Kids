package com.example.faroukproject.Adapters;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faroukproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ItemAdapter.clickListener mClickListener;
    TextToSpeech t1;
    ArrayList<String> image=new ArrayList<>();
    ArrayList<String>name=new ArrayList<>();
    Context context;
    public ItemAdapter(ArrayList<String>image, ArrayList<String>name, Context context) {
        this.image=image;
        this.name=name;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        t1=new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent, false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
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
                t1.speak(name.get(position), TextToSpeech.QUEUE_FLUSH, null);
                if (mClickListener != null)
                {mClickListener.onItemClick(view, position);
                    Log.e("position3", String.valueOf(position));

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView name;
        CardView container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            container=itemView.findViewById(R.id.container);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null)
                    {mClickListener.onItemClick(view, getAdapterPosition());
                        Log.e("position2", String.valueOf(getAdapterPosition()));
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
            {mClickListener.onItemClick(view, getAdapterPosition());
                Log.e("position1", String.valueOf(getAdapterPosition()));
            }

        }
    }

    public void setClickListener(ItemAdapter.clickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public  interface clickListener{
         void onItemClick(View view,int position);
    }
    }


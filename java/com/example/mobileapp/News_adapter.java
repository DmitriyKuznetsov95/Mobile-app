package com.example.mobileapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class News_adapter extends RecyclerView.Adapter<News_adapter.NewsViewHolder> {

    ArrayList<News> news;

    public News_adapter(ArrayList<News> n) {
        news = n;
    }

    @NonNull
    @Override
    public News_adapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout, parent, false);
        return new News_adapter.NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull News_adapter.NewsViewHolder holder, final int position) {
        holder.news_header.setText(news.get(position).getHeader());
        holder.news_text.setText(news.get(position).getText());
        Picasso.get().load(news.get(position).getImage()).into(holder.news_image);
        holder.news_date.setText(news.get(position).getDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), New_details.class);
                intent.putExtra("header", news.get(position).getHeader());
                intent.putExtra("text", news.get(position).getText());
                intent.putExtra("date", news.get(position).getDate());
                intent.putExtra("image", news.get(position).getImage());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView news_header, news_text, news_date;
        ImageView news_image;
        CardView cardView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            news_header = itemView.findViewById(R.id.news_header);
            news_text = itemView.findViewById(R.id.news_text);
            news_date = itemView.findViewById(R.id.news_date);
            news_image = itemView.findViewById(R.id.news_image);
            cardView = itemView.findViewById(R.id.card_news);
        }
    }
}

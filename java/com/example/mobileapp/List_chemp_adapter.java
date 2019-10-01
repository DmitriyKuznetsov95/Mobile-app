package com.example.mobileapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class List_chemp_adapter extends RecyclerView.Adapter<List_chemp_adapter.ChempViewHolder> {
    ArrayList<List_chemp> list_chemps;

    public List_chemp_adapter(ArrayList<List_chemp> l_c) {
        list_chemps = l_c;
    }

    @NonNull
    @Override
    public ChempViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chemp_layout,parent,false);
        return new List_chemp_adapter.ChempViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChempViewHolder holder, final int position) {
        holder.chemp_header.setText(list_chemps.get(position).getHeader());
        holder.chemp_date.setText(list_chemps.get(position).getDate());
        Picasso.get().load(list_chemps.get(position).getImage()).into(holder.chemp_image);
        holder.card_list_chemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), List_komp.class);
                intent.putExtra("header", list_chemps.get(position).getHeader());
                intent.putExtra("date", list_chemps.get(position).getDate());
                intent.putExtra("image", list_chemps.get(position).getImage());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_chemps.size();
    }

    public class ChempViewHolder extends RecyclerView.ViewHolder {

        TextView chemp_header;
        TextView chemp_date;
        ImageView chemp_image;
        CardView card_list_chemp;

        public ChempViewHolder(@NonNull View itemView) {
            super(itemView);

            chemp_header = itemView.findViewById(R.id.chemp_header);
            chemp_date = itemView.findViewById(R.id.chemp_date);
            chemp_image = itemView.findViewById(R.id.chemp_image);
            card_list_chemp = itemView.findViewById(R.id.card_list_chemp);
        }
    }
}

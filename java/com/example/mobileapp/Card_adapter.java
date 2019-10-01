package com.example.mobileapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Card_adapter extends RecyclerView.Adapter<Card_adapter.MyViewHolder> {

    //Context context;
    ArrayList<Card_people> cpeople;

    public Card_adapter(ArrayList<Card_people> c_p)
    {
        //context = c;
        cpeople = c_p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_people,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(cpeople.get(position).getName());
        holder.surname.setText(cpeople.get(position).getSurName());
        Picasso.get().load(cpeople.get(position).getImage()).transform(new Circular()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return cpeople.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, surname;
        ImageView pic;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.card_name);
            surname = (TextView) itemView.findViewById(R.id.card_surname);
            pic = (ImageView) itemView.findViewById(R.id.card_image);
        }
    }

}

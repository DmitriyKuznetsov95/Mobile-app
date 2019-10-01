package com.example.mobileapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Gallery_adapter extends RecyclerView.Adapter<Gallery_adapter.GalleryViewHolder> {

    ArrayList<Gallery> gallery;

    public Gallery_adapter(ArrayList<Gallery> g)
    {
        gallery = g;
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        CardView cardView;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image_gallery);
            cardView = itemView.findViewById(R.id.card_gallery);
        }
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.images_gallery, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, final int position) {
        Picasso.get().load(gallery.get(position).getUrl()).into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Gallery_details.class);
                intent.putExtra("image", gallery.get(position).getUrl());
                view.getContext().startActivity(intent);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Gallery_del_image str = new Gallery_del_image(gallery.get(position).getUrl());
                str.del_image();
                /*Intent intent = new Intent(view.getContext(), Gallery_del_image.class);
                intent.putExtra("image", gallery.get(position).getUrl());
                view.getContext().startActivity(intent);*/
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return gallery.size();
    }
}

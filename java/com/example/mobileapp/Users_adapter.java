package com.example.mobileapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Users_adapter extends RecyclerView.Adapter<Users_adapter.UsersViewHolder> {
    ArrayList<Users> users;

    public Users_adapter(ArrayList<Users> u)
    {
        users = u;
    }

    @NonNull
    @Override
    public Users_adapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_layout,parent,false);
        return new Users_adapter.UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Users_adapter.UsersViewHolder holder, int position) {
        Picasso.get().load(users.get(position).getImage()).transform(new Circular()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder
    {
        TextView text;
        ImageView pic;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            pic = (ImageView) itemView.findViewById(R.id.user_image);
            //****************************
            text = (TextView) itemView.findViewById(R.id.user_email);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String email = user.getEmail();
            text.setText(email);
        }
    }
}

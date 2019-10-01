package com.example.mobileapp;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Gallery_del_image extends AppCompatActivity {

    private String strurl = "";

    public Gallery_del_image(String url) {
        //String strurl = getIntent().getStringExtra("image");
        strurl = url;
    }

    public void del_image()
    {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        Query query = myRef.child("Photos").orderByChild("Url").equalTo(strurl);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    dataSnapshot1.getRef().removeValue();
                }

                Main7Activity.list.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

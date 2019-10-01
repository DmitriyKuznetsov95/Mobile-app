package com.example.mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main7Activity extends AppCompatActivity {

    public static RecyclerView recyclerView;
    DatabaseReference mRef;
    public static ArrayList<Gallery> list;
    public static Gallery_adapter adapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery_add, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        setTitle("Галерея");

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);

        recyclerView = findViewById(R.id.list_image);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);

        mRef = FirebaseDatabase.getInstance().getReference().child("Photos");

        list = new ArrayList<Gallery>();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Gallery p = dataSnapshot1.getValue(Gallery.class);
                    list.add(p);
                }
                adapter = new Gallery_adapter(list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main7Activity.this,"uji",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void list_peopleClick(View view) {
        Intent intent = new Intent(Main7Activity.this, Main6Activity.class);
        startActivity(intent);
    }

    public void list_chempClick(View view) {
        Intent intent = new Intent(Main7Activity.this, Main5Activity.class);
        startActivity(intent);
    }

    public void newsClick(View view) {
        Intent intent = new Intent(Main7Activity.this, MainActivity.class);
        startActivity(intent);
    }

    public void AddImageClick(MenuItem item) {
        Intent intent = new Intent(Main7Activity.this, Gallery_add_image.class);
        startActivity(intent);
    }
}

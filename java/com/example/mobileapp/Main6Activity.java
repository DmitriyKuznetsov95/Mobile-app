package com.example.mobileapp;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main6Activity<onTabReselected> extends AppCompatActivity {

    DatabaseReference myRef;
    RecyclerView recyclerView;
    ArrayList<Card_people> list, list1;
    Card_adapter adapter;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        setTitle("Список участников");

        recyclerView = (RecyclerView) findViewById(R.id.list_people);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Card_people>();

        myRef = FirebaseDatabase.getInstance().getReference().child("People").child("Expert");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {

                    Card_people p = dataSnapshot1.getValue(Card_people.class);
                    list.add(p);
                }
                adapter = new Card_adapter(list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main6Activity.this,"uji",Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.expert:
                        expert();
                        break;
                    case R.id.ych:
                        ych();
                        break;
                }
                return true;
            }
        });
    }

    private void expert()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Card_people>();

        myRef = FirebaseDatabase.getInstance().getReference().child("People").child("Expert");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {

                    Card_people p = dataSnapshot1.getValue(Card_people.class);
                    list.add(p);
                }
                adapter = new Card_adapter(list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main6Activity.this,"uji",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ych()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Card_people>();

        myRef = FirebaseDatabase.getInstance().getReference().child("People").child("Ych");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {

                    Card_people p = dataSnapshot1.getValue(Card_people.class);
                    list.add(p);
                }
                adapter = new Card_adapter(list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main6Activity.this,"uji",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void search(String str)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Card_people> searchlist = new ArrayList<>();
        for(Card_people object:list1)
        {
            if(object.getSurName().toLowerCase().contains(str.toLowerCase()) || object.getName().toLowerCase().contains(str.toLowerCase()))
            {
                searchlist.add(object);
            }
        }
        adapter = new Card_adapter(searchlist);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView search1 = (SearchView) searchItem.getActionView();

        list1 = new ArrayList<Card_people>();

        full_list("Expert");
        full_list("Ych");
        search1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s);
                return true;
            }
        });

        return true;
    }

    private void full_list(String str)
    {
        myRef = FirebaseDatabase.getInstance().getReference().child("People").child(str);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {

                    Card_people p = dataSnapshot1.getValue(Card_people.class);
                    list1.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main6Activity.this,"uji",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void list_chempClick(View view) {
        Intent intent = new Intent(this,Main5Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void newsClick(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void galleryClick(View view) {
        Intent intent = new Intent(this,Main7Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

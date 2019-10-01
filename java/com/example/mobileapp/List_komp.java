package com.example.mobileapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class List_komp extends AppCompatActivity {

    TextView l_cheader, l_ctext, l_cdate;
    ImageView l_cimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_komp);

        ActionBar actionBar = getSupportActionBar();

        setTitle("Список компетенций");

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        l_cheader = findViewById(R.id.l_cheader);
        l_cdate = findViewById(R.id.l_cdate);
        l_cimage = findViewById(R.id.l_cimage);

        String header = getIntent().getStringExtra("header");
        l_cheader.setText(header);
        String date = getIntent().getStringExtra("date");
        l_cdate.setText(date);
        String image = getIntent().getStringExtra("image");
        Picasso.get().load(image).into(l_cimage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

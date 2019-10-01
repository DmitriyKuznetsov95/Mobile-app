package com.example.mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import android.Manifest;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Gallery_details extends AppCompatActivity {

    ImageView image;

    Bitmap bitmap;

    String strurl = "";

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_details);

        ActionBar actionBar = getSupportActionBar();

        setTitle("Подробнее");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        image = findViewById(R.id.details_image);

        strurl = getIntent().getStringExtra("image");
        Picasso.get().load(strurl).into(image);

        try {
            bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        } catch (Exception e)
        {
            Toast.makeText(this, strurl + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void saveClick(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            {
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, WRITE_EXTERNAL_STORAGE_CODE);
            }
            else
            {
                saveImage();
            }
        }
        else
        {
            saveImage();
        }
    }

    private void saveImage() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path+"/Firebase/");
        dir.mkdirs();
        String imageName = timeStamp + ".PNG";
        File file = new File(dir, imageName);
        OutputStream out;
        try
        {
            out =  new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(this, imageName+" saved to "+ dir, Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case WRITE_EXTERNAL_STORAGE_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    saveImage();
                }
                else
                {
                    Toast.makeText(this, "1111", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void shareClick(View view) {
        shareImage();
    }

    private void shareImage() {
        try
        {
            String s = "111";
            Uri uri = Uri.parse(strurl);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, s);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Share via"));
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

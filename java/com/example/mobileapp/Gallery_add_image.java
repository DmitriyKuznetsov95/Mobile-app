package com.example.mobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Gallery_add_image extends AppCompatActivity {

    FirebaseUser user;
    StorageReference storageReference;
    DatabaseReference myRef;

    ProgressDialog progressDialog;

    String storagePath = "Photos/";

    Uri filePath;

    ImageView image;
    Button button;

    int IMAGE_REQUEST_CODE = 5;

    String uid ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_add_image);

        ActionBar actionBar = getSupportActionBar();

        setTitle("Добавить картинку");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

        progressDialog = new ProgressDialog(Gallery_add_image.this);
    }

    public void gallery_imageClick(View view) {
        storageReference = FirebaseStorage.getInstance().getReference();

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select"), IMAGE_REQUEST_CODE);
        button = findViewById(R.id.galleryClick);
        if (button.getVisibility() == View.GONE)
        {
            button.setVisibility(View.VISIBLE);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void galleryClick(View view) {
        if (filePath != null)
        {
            progressDialog.setTitle("Загрузка...");
            progressDialog.show();

            StorageReference storageReference2 = storageReference.child(storagePath + System.currentTimeMillis() + "." + getFileExtension(filePath));
            storageReference2.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                    downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            myRef = FirebaseDatabase.getInstance().getReference().child("Photos").push().child("Url");
                            myRef.setValue(url);
                            progressDialog.dismiss();
                            Main7Activity.list.clear();
                            onSupportNavigateUp();
                            //Intent intent = new Intent(Gallery_add_image.this, Main7Activity.class);
                            //startActivity(intent);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(Gallery_add_image.this, "1111", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            image = findViewById(R.id.gallery_image);
            Picasso.get().load(filePath).into(image);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

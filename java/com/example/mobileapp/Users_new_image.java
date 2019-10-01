package com.example.mobileapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

public class Users_new_image extends AppCompatActivity {

    FirebaseUser user;
    StorageReference storageReference;
    DatabaseReference myRef;

    ProgressDialog progressDialog;

    String storagePath = "Users/";

    Uri filePath;

    ImageView image;
    Button imgbtn;

    int IMAGE_REQUEST_CODE = 5;

    String uid ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_new_image);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);
        setTitle("Изменить картинку");

        progressDialog = new ProgressDialog(Users_new_image.this);
    }

    public void new_imageClick(View view) {

        storageReference = FirebaseStorage.getInstance().getReference();

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select"), IMAGE_REQUEST_CODE);
        imgbtn = findViewById(R.id.imageClick);
        if (imgbtn.getVisibility() == View.GONE)
        {
            imgbtn.setVisibility(View.VISIBLE);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void imgClick(View view) {
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
                            myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("1").child("Image");
                            myRef.setValue(url);
                            progressDialog.dismiss();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(Users_new_image.this, "1111", Toast.LENGTH_SHORT).show();
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
            image = findViewById(R.id.new_image);
            Picasso.get().load(filePath).transform(new Circular()).into(image);
        }
    }
}

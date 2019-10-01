package com.example.mobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Users> list;
    Users_adapter adapter;
    FirebaseUser user;
    DatabaseReference myRef;

    EditText oldpass;
    EditText newpass1;
    EditText newpass2;

    LinearLayout layout_pass;

    String uid ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);
        setTitle("Профиль: " + user.getEmail());

        recyclerView = (RecyclerView) findViewById(R.id.user_scroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Users>();

        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        Users p = dataSnapshot1.getValue(Users.class);
                        list.add(p);
                    }
                    adapter = new Users_adapter(list);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main4Activity.this,"uji",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void profileback(View view) {

    }

    public void SingOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void changepass(View view) {

        layout_pass = findViewById(R.id.layout_pass);
        if (layout_pass.getVisibility() == View.GONE)
        {
            layout_pass.setVisibility(View.VISIBLE);
        }
        else {
            user = FirebaseAuth.getInstance().getCurrentUser();
            String email = user.getEmail();

            boolean valid = validate();

            if (valid) {
                final String stroldpass = oldpass.getText().toString();
                final String strnewpass1 = newpass1.getText().toString();

                AuthCredential credential = EmailAuthProvider.getCredential(email, stroldpass);

                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(strnewpass1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(Main4Activity.this, "failed.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Main4Activity.this, "success.",
                                                Toast.LENGTH_SHORT).show();
                                        oldpass.setText("");
                                        newpass1.setText("");
                                        newpass2.setText("");
                                        layout_pass.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            oldpass.setError("Неверный пароль");
                        }
                    }
                });
            }

        }

    }

    public boolean validate()
    {
        oldpass = findViewById(R.id.user_oldpass);
        String stroldpass = oldpass.getText().toString();

        newpass1 = findViewById(R.id.user_newpass1);
        final String strnewpass1 = newpass1.getText().toString();

        newpass2 = findViewById(R.id.user_newpass2);
        String strnewpass2 = newpass2.getText().toString();

        boolean valid_pass = false;

        if (stroldpass.isEmpty()) {
            oldpass.setError("Поле пустое");
        } else if (strnewpass1.isEmpty()) {
            newpass1.setError("Поле пустое");
        } else if (strnewpass2.isEmpty()) {
            newpass2.setError("Поле пустое");
        } else if (!(strnewpass1.equals(strnewpass2))) {
            newpass1.setError("Пароли не совпадают");
            newpass2.setError("Пароли не совпадают");
        } else if (strnewpass1.length() < 6) {
            newpass1.setError("Пароль меньше 6 символов");
        } else if (stroldpass.equals(strnewpass1)) {
            oldpass.setError("Пароли совпадают");
            newpass1.setError("Пароли совпадают");
        } else {
            valid_pass = true;
        }

        if (valid_pass){
            return true;
        } else {
            return false;
        }
    }

    public void changeImgClick(View view) {
        Intent intent = new Intent(Main4Activity.this, Users_new_image.class);
        startActivity(intent);
    }
}

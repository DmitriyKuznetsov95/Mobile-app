package com.example.mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main3Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference myRef;

    Users users;

    private EditText email;
    private EditText pass1;
    private EditText pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);
        setTitle("Регистрация");

        mAuth = FirebaseAuth.getInstance();
    }

    public boolean validate()
    {
        boolean valid_email = false;
        boolean valid_pass = false;

        email = (EditText) findViewById(R.id.email);
        String stremail = email.getText().toString();

        if (stremail.isEmpty()) {
            email.setError("Поле пустое");
        } else if (!(stremail.contains("@"))) {
            email.setError("Некорректный Email");
        } else {
            valid_email = true;
        }

        pass1 = (EditText) findViewById(R.id.pass1);
        String strpass1 = pass1.getText().toString();
        pass2 = (EditText) findViewById(R.id.pass2);
        String strpass2 = pass2.getText().toString();

        if (strpass1.isEmpty()) {
            pass1.setError("Поле пустое");
        } else if (strpass2.isEmpty()) {
            pass2.setError("Поле пустое");
        } else if (!(strpass1.equals(strpass2))) {
            pass2.setError("");
        } else if (strpass1.length() < 6) {
            pass1.setError("Пароль меньше 6 символов");
        } else {
            valid_pass = true;
        }

        if (valid_email && valid_pass){
            return true;
        } else {
            return false;
        }
    }

    public void reg(View view) {
        if (validate()){

            email = (EditText) findViewById(R.id.email);
            pass1 = (EditText) findViewById(R.id.pass1);
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass1.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                //if (user != null) {
                                    String uid = user.getUid();


                                //if (MainActivity.i == 1)
                                //{
                                    myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("1").child("Image");
                                    myRef.setValue("https://firebasestorage.googleapis.com/v0/b/mobileapp-6f9ec.appspot.com/o/Users%2F1.jpg?alt=media&token=5091021e-4615-4be2-830c-aadf2ebae0ae");
                                    //MainActivity.i = 0;
                                //}
                                Intent intent = new Intent(Main3Activity.this,Main4Activity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Main3Activity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}

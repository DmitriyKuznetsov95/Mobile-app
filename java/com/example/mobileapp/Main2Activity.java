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

public class Main2Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText login;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);
        setTitle("Вход");

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            Intent intent = new Intent(Main2Activity.this,Main4Activity.class);
            startActivity(intent);
        }

    }

    /*@Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null)
        {
            mAuth.addAuthStateListener(mAuthListener);
        }
    }*/

    public void enter(View view) {
       if (validate())
        {
            login = (EditText) findViewById(R.id.login);
            pass = (EditText) findViewById(R.id.pass);

            mAuth.signInWithEmailAndPassword(login.getText().toString(), pass.getText().toString())
                   .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                                 FirebaseUser user = mAuth.getCurrentUser();
                                 Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
                                 startActivity(intent);
                           } else {
                                 Toast.makeText(Main2Activity.this, "Authentication failed.",
                                         Toast.LENGTH_SHORT).show();
                           }
                        }
                   });
        }
    }

    public boolean validate()
    {
        boolean valid_email = false;
        boolean valid_pass = false;

        login = (EditText) findViewById(R.id.login);
        String strlogin = login.getText().toString();

        if (strlogin.isEmpty()) {
            login.setError("Поле пустое");
        } else if (!(strlogin.contains("@"))) {
            login.setError("Некорректный Email");
        } else {
            valid_email = true;
        }

        pass = (EditText) findViewById(R.id.pass);
        String strpass1 = pass.getText().toString();

        if (strpass1.isEmpty()) {
            pass.setError("Поле пустое");
        } else if (strpass1.length() < 6) {
            pass.setError("Пароль меньше 6 символов");
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
        Intent intent = new Intent(this,Main3Activity.class);
        startActivity(intent);
    }
}

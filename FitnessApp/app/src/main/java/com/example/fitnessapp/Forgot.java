package com.example.fitnessapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {
    EditText et_forget_email;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        et_forget_email = findViewById(R.id.forget_uname);
        mAuth = FirebaseAuth.getInstance();
    }

    public void resetPassword(View view) {
        String emailId = et_forget_email.getText().toString();
        if(!emailId.isEmpty()) {
            mAuth.sendPasswordResetEmail(emailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Forgot.this,
                                "The password link has been sent to your emali id", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(Forgot.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(Forgot.this, "Please check the email id you have entered", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else
            Toast.makeText(this, "Please Enter your email", Toast.LENGTH_SHORT).show();

    }
}
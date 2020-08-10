package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText et_email, et_pass;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_email = findViewById(R.id.email);
        et_pass = findViewById(R.id.password);

    }

    public void login(View view) {
        String data = et_email.getText().toString();
        String data1 = et_pass.getText().toString();
        if (data.isEmpty() || data1.isEmpty()) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
        }
        else
            {
            String useremail = et_email.getText().toString();
            String userpass = et_pass.getText().toString();
            mAuth.signInWithEmailAndPassword(useremail, userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder dailog = new AlertDialog.Builder(MainActivity.this);
                        dailog.setTitle("Forgot Password");
                        dailog.setMessage("Reset Password");
                        dailog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent a = new Intent(MainActivity.this,Forgot.class);
                                startActivity(a);
                                finish();

                            }
                        });
                        dailog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });
                        dailog.setCancelable(false);
                        dailog.show();
                    }
                }
            });
            Intent i = new Intent(this, Home.class);
            startActivity(i);

        }
    }

    public void register(View view) {
        Intent i = new Intent(this,Register.class);
        startActivity(i);
    }
}




package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import  androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager manager;
    FragmentTransaction transaction;
    ImageView profImg;
    public  static  final int GALLERY_REQUEST = 23;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = findViewById(R.id.toolbar);
        getSupportActionBar();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity2.this,
                drawerLayout, toolbar, 0, 0);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));

        navigationView.setNavigationItemSelectedListener(this);

        View headview = navigationView.getHeaderView(0);
        profImg = (ImageView) headview.findViewById(R.id.uImage);
        profImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              openGallery();
            }
        });


        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        Home home = new Home();
        transaction.replace(R.id.main_body,home);
        transaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        switch (item.getItemId()) {
            case R.id.home:
                Home home = new Home();
                transaction.replace(R.id.main_body, home);
                transaction.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.logout:
                final AlertDialog.Builder dailog = new AlertDialog.Builder(MainActivity2.this);
                dailog.setTitle("Logout");
                dailog.setMessage("Surely want to logout");
                dailog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent a = new Intent(MainActivity2.this,MainActivity.class);
                        startActivity(a);
                        finish();

                    }
                });
                dailog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
                dailog.setCancelable(false);
                dailog.show();
                break;

            case R.id.diet:
                Diet diet = new Diet();
                transaction.replace(R.id.main_body,diet);
                transaction.commit();
                drawerLayout.closeDrawers();
                break;

            case R.id.chest:
                Chest chest = new Chest();
                transaction.replace(R.id.main_body,chest);
                transaction.commit();
                drawerLayout.closeDrawers();
                break;

            case R.id.cardio:
                Cardio cardio = new Cardio();
                transaction.replace(R.id.main_body,cardio);
                transaction.commit();
                drawerLayout.closeDrawers();
                break;

            case R.id.abs:
                Abs abs = new Abs();
                transaction.replace(R.id.main_body,abs);
                transaction.commit();
                drawerLayout.closeDrawers();
                break;
        }
        return false;
    }

    private void openGallery(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,GALLERY_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if(requestCode == GALLERY_REQUEST){

            if(resultCode == RESULT_OK){
                Uri u = data.getData();
                profImg.setImageURI(u);

            }
        }

    }


}
package com.mayur.projectpersonalitydevelopment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Profile extends AppCompatActivity {

    private Button btnExplore,btnAboutUs,btnUsername,btnPassword,btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Views
        init();

        //Intent for About and Profile Activity
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AboutUs.class);
                startActivity(intent);
            }
        });
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        //Username
        btnUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        //password
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }

    //Initialize views
    public void init(){
        btnExplore=findViewById(R.id.btnExplore);
        btnAboutUs=findViewById(R.id.btnAboutUs);
        btnUsername=findViewById(R.id.btnUsername);
        btnPassword=findViewById(R.id.btnPassword);
        btnLogout=findViewById(R.id.btnLogout);
    }
}
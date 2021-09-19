package com.mayur.projectpersonalitydevelopment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutUs extends AppCompatActivity {

    private RecyclerView rvRecyler;

    private Button btnExplore,btnProfile;

    private AboutUsRecylerAdapter aboutUsRecylerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //Views
        init();
        //Adapter class
        aboutUsRecylerAdapter =new AboutUsRecylerAdapter();
        rvRecyler.setAdapter(aboutUsRecylerAdapter);
        rvRecyler.setLayoutManager(new LinearLayoutManager(this));

        //Intent for About and Profile Activity

        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Profile.class);
                startActivity(intent);
            }
        });

    }

    //Initialize views
    public void init(){
        rvRecyler=findViewById(R.id.rvAboutUs);
        btnExplore=findViewById(R.id.btnExplore);
        btnProfile=findViewById(R.id.btnProfile);
    }
}
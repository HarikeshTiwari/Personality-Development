package com.mayur.projectpersonalitydevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    private Button btnExplore,btnAboutUs,btnProfile;

    private TextView tvResult;

    private ImageView ivResult;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Initializing Views
        init();

        //Get marks from shared prefernces
        sharedPreferences = getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int marks=Integer.parseInt(sharedPreferences.getString("marks",null));
        //int key=Integer.parseInt(sharedPreferences.getString("key",null));

        if(marks<=3){
            tvResult.setText("Sorry but you failed this test");
            tvResult.setTextColor(Color.RED);
            ivResult.setImageResource(R.drawable.failed);
        }
        else if (marks>3 && marks<7){
            tvResult.setText("You are of Average Personality");
            tvResult.setTextColor(Color.GREEN);
            ivResult.setImageResource(R.drawable.average);
        }
        else{
            tvResult.setText("Excellent!! You have mindblowing personality");
            tvResult.setTextColor(Color.GREEN);
            ivResult.setImageResource(R.drawable.passed);
        }


        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AboutUs.class);
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
        btnExplore=findViewById(R.id.btnExplore);
        btnAboutUs=findViewById(R.id.btnAboutUs);
        btnProfile=findViewById(R.id.btnProfile);
        tvResult=findViewById(R.id.tvResult);
        ivResult=findViewById(R.id.ivResult);
    }
}
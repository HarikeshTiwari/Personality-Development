package com.mayur.projectpersonalitydevelopment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    private  ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Initialize views
        init();

        ImageView imageView = findViewById(R.id.imageView);

        //spring animation
        springAnimation(imageView);

        //splash screen for 1s
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                }
                catch (Exception ex){

                }
                Intent intent=new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent);
            }
        });
        thread.start();
    }

    //Initialize views
    public void init(){
        imageView=findViewById(R.id.imageView);
    }

    //spring animation
    public void springAnimation(ImageView imageView){
        SpringAnimation springAnim = new SpringAnimation(imageView, SpringAnimation.TRANSLATION_Y);
        SpringForce springForce = new SpringForce();
        springForce.setFinalPosition(-200f);
        springForce.setStiffness(SpringForce.STIFFNESS_LOW);
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        springAnim.setSpring(springForce);
        springAnim.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Initialize views
        imageView=findViewById(R.id.imageView);
        //spring animation
        springAnimation(imageView);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Initialize views
        imageView = findViewById(R.id.imageView);
        //spring animation
        springAnimation(imageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Initialize views
        imageView = findViewById(R.id.imageView);
        //spring animation
        springAnimation(imageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Initialize views
        imageView = findViewById(R.id.imageView);
        //spring animation
        springAnimation(imageView);
    }
}
package com.mayur.projectpersonalitydevelopment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Quiz extends AppCompatActivity implements View.OnClickListener{

    private ImageButton imgForward,imgBack;

    private TextView tvWelcome,tvQuestion,tvOption1,tvOption2,tvOption3,tvOption4,tvMessage,tvSubmit;

    private FirebaseDatabase fd;

    private DatabaseReference dr;

    private ProgressBar progressIndicator;

    private Button btnTime,btnYes,btnNo;

    //incrementing variable for questions
    private int i=1;

    private int point=0;

    private int click=0;

    private int storeSeconds=0;

    CountDownTimer countDownTimer;

    SharedPreferences sharedpreferences;

    SharedPreferences.Editor editor;

    ArrayList aQuestionNo=new ArrayList();

    String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Initializing views

        btnTime=findViewById(R.id.btnTime);
        imgForward=findViewById(R.id.imgForward);
        imgBack=findViewById(R.id.imgBack);
        tvQuestion=findViewById(R.id.tvQuestion);
        tvOption1=findViewById(R.id.tvOption1);
        tvOption2=findViewById(R.id.tvOption2);
        tvOption3=findViewById(R.id.tvOption3);
        tvOption4=findViewById(R.id.tvOption4);

        tvMessage=findViewById(R.id.tvMessage);
        tvSubmit=findViewById(R.id.tvSubmit);
        btnYes=findViewById(R.id.btnYes);
        btnNo=findViewById(R.id.btnNo);
        progressIndicator=findViewById(R.id.progressIndicator);
        //setting listeners
        tvOption1.setOnClickListener(this);
        tvOption2.setOnClickListener(this);
        tvOption3.setOnClickListener(this);
        tvOption4.setOnClickListener(this);
        //set all edittext empty first
        tvMessage.setText(" Wait for seconds we are loading all Questions..");
        tvQuestion.setText("");
        tvOption2.setText("");
        tvOption3.setText("");
        tvOption4.setText("");
        //set all text invisble
        tvQuestion.setVisibility(View.INVISIBLE);
        tvMessage.setVisibility(View.VISIBLE);
        tvOption1.setVisibility(View.INVISIBLE);
        tvOption2.setVisibility(View.INVISIBLE);
        tvOption3.setVisibility(View.INVISIBLE);
        tvOption4.setVisibility(View.INVISIBLE);
        imgBack.setVisibility(View.INVISIBLE);
        imgForward.setVisibility(View.INVISIBLE);
        btnTime.setVisibility(View.INVISIBLE);
        tvSubmit.setVisibility(View.INVISIBLE);
        btnYes.setVisibility(View.INVISIBLE);
        btnNo.setVisibility(View.INVISIBLE);
        tvMessage.setTextColor(Color.RED);

        //Initializing firebase instance
        fd= FirebaseDatabase.getInstance();
        dr=fd.getReference("User/Quiz");

        //Initial Quiz
        getData();

        //forward button clicked load new quiz
        imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting background color white on next or previous question
                if(click==1) {
                    //setting background color white on next or previous question
                    setBackgroundColorAgain();
                    //if i is less than 10
                    if (i <10) {
                        i++;
                    }

                    getData();
                    //Restart countdown timer
                    countDownTimer.start();

                    //if i is equal to 10
                    if(i==10) {
                        showSubmitPage();
                    }
                    //set click variable to 0
                    click=0;

                }

            }
        });
        //backward button clicked load previous quiz
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting background color white on next or previous question
                setBackgroundColorAgain();
                if (i > 1) {
                    i--;
                }
                getData();
            }
        });
        //countdown timer for showing specific time for each questions
        countDownTimer=new CountDownTimer(30000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat f=new DecimalFormat("00");
                long hour=(millisUntilFinished/3600000)%24;
                long min=(millisUntilFinished/60000)%60;
                long sec=(millisUntilFinished/1000)%60;
                btnTime.setText(f.format(hour)+":"+f.format(min)+":"+f.format(sec));
                //store seconds to give marks accordingly
                NumberFormat secFormat=new DecimalFormat("0");
                long newSec=(millisUntilFinished/1000)%60;
                storeSeconds=Integer.parseInt(secFormat.format(newSec));
            }

            @Override
            public void onFinish() {
                storeSeconds=0;
                btnTime.setText("00:00:00");
            }
        };
        //start timer for first question
        countDownTimer.start();

        //shared preferences to store point or score
        sharedpreferences = getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        //handling quiz submission
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashboardIntent = new Intent(getApplicationContext(),Result.class);
                //storing marks in firebase
                storeData();
                //set lock in quiz
                editor.putString("key","set");
                editor.apply();
                //countdown timer for showing specific time for each questions
                CountDownTimer countDownTimer=new CountDownTimer(86400000,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        //reset quiz after 24 hours
                        editor.putString("key","reset");
                        editor.apply();
                    }
                };
                countDownTimer.start();
                startActivity(dashboardIntent);
            }
        });
        //handling quiz submission
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                getData();
                //Below will be visible
                tvSubmit.setVisibility(View.INVISIBLE);
                btnYes.setVisibility(View.INVISIBLE);
                btnNo.setVisibility(View.INVISIBLE);
            }
        });


    }

    //handle onclicking any 4 options of question
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tvOption1){
            //stop timer if option1 clicked
            countDownTimer.cancel();
            //checking whether it is correct or not
            getAnswer(tvOption1);
            //handling click button
            click=1;
        }
        else if(v.getId()==R.id.tvOption2){
            //stop timer if option2 clicked
            countDownTimer.cancel();
            getAnswer(tvOption2);
            //handling click button
            click=1;

        }
        else if(v.getId()==R.id.tvOption3){
            //stop timer if option1 clicked
            countDownTimer.cancel();
            getAnswer(tvOption3);
            //handling click button
            click=1;
        }
        else if(v.getId()==R.id.tvOption4){
            //stop timer if option1 clicked
            countDownTimer.cancel();
            getAnswer(tvOption4);
            //handling click button
            click=1;
        }
    }
    //setting background color white on next or previous question
    public void setBackgroundColorAgain(){
        tvOption1.setBackgroundColor(Color.WHITE);
        tvOption2.setBackgroundColor(Color.WHITE);
        tvOption3.setBackgroundColor(Color.WHITE);
        tvOption4.setBackgroundColor(Color.WHITE);
    }
    //matching if answer is correct or not
    public void getAnswer(TextView etAns){

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(String.valueOf(i)).exists()) {
                    DataSnapshot ds = snapshot.child(String.valueOf(i));
                    DataSnapshot answer = ds.child("Answer");
                    String ans= String.valueOf(answer.getValue());

                    //put i in arraylist for checking whether answer is clicked 1 or more time
                    //check if answer from firebase is equals to answer clicked in editext

                    if(ans.equals(etAns.getText().toString())){
                        etAns.setBackgroundColor(Color.GREEN);

                        if(!aQuestionNo.contains(i) && storeSeconds>0) {
                            point++;
                        }
                        editor.putString("marks", String.valueOf(point));
                        editor.commit();
                    }
                    else{
                        etAns.setBackgroundColor(Color.RED);
                    }
                    aQuestionNo.add(i);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //retrieve data from database
    public void getData(){

        //create firebase instance
        fd= FirebaseDatabase.getInstance();
        DatabaseReference dr=fd.getReference("User/Quiz");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //check if data is available at specific Question number
                if (snapshot.child(String.valueOf(i)).exists()) {
                    DataSnapshot ds = snapshot.child(String.valueOf(i));
                    DataSnapshot question = ds.child("Question");
                    DataSnapshot option1 = ds.child("Option1");
                    DataSnapshot option2 = ds.child("Option2");
                    DataSnapshot option3 = ds.child("Option3");
                    DataSnapshot option4 = ds.child("Option4");

                    //setting value to edittext
                    tvQuestion.setText((String) question.getValue());
                    tvOption1.setText(String.valueOf(option1.getValue()));
                    tvOption2.setText(String.valueOf(option2.getValue()));
                    tvOption3.setText(String.valueOf(option3.getValue()));
                    tvOption4.setText(String.valueOf(option4.getValue()));
                }
                if(i==10){
                    //set all view invisble except tvSubmit,btnYes,btnNo
                    tvMessage.setVisibility(View.INVISIBLE);
                    tvQuestion.setVisibility(View.INVISIBLE);
                    tvOption1.setVisibility(View.INVISIBLE);
                    tvOption2.setVisibility(View.INVISIBLE);
                    tvOption3.setVisibility(View.INVISIBLE);
                    tvOption4.setVisibility(View.INVISIBLE);
                    progressIndicator.setVisibility(View.INVISIBLE);
                    imgBack.setVisibility(View.INVISIBLE);
                    imgForward.setVisibility(View.INVISIBLE);
                    btnTime.setVisibility(View.INVISIBLE);
                    return;
                }
                if(snapshot.getChildrenCount()>0 ){
                    progressIndicator.setVisibility(View.INVISIBLE);
                    //set all text invisble
                    tvMessage.setVisibility(View.INVISIBLE);
                    tvQuestion.setVisibility(View.VISIBLE);
                    tvOption1.setVisibility(View.VISIBLE);
                    tvOption2.setVisibility(View.VISIBLE);
                    tvOption3.setVisibility(View.VISIBLE);
                    tvOption4.setVisibility(View.VISIBLE);
                    imgBack.setVisibility(View.VISIBLE);
                    imgForward.setVisibility(View.VISIBLE);
                    btnTime.setVisibility(View.VISIBLE);

                }
                else{
                    //set all text invisble
                    tvMessage.setVisibility(View.VISIBLE);
                    tvQuestion.setVisibility(View.INVISIBLE);
                    tvOption1.setVisibility(View.INVISIBLE);
                    tvOption2.setVisibility(View.INVISIBLE);
                    tvOption3.setVisibility(View.INVISIBLE);
                    tvOption4.setVisibility(View.INVISIBLE);
                    progressIndicator.setVisibility(View.VISIBLE);
                    imgBack.setVisibility(View.INVISIBLE);
                    imgForward.setVisibility(View.INVISIBLE);
                    btnTime.setVisibility(View.INVISIBLE);
                    tvSubmit.setVisibility(View.INVISIBLE);
                    btnYes.setVisibility(View.INVISIBLE);
                    btnNo.setVisibility(View.INVISIBLE);
                    tvMessage.setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    //store marks and username in firebase
    public void storeData(){
        //getting user password

        //Getting mark from shared preference
        SharedPreferences sharedPreferences=getSharedPreferences("Credentials",0);
        String password=sharedPreferences.getString("user_password",null);

        //Model object
        FirebaseYouTubeData firebaseData=new FirebaseYouTubeData(this);
        //setting marks of user
        firebaseData.setData(password,point);
    }

    //show final page to submit application
    public void showSubmitPage(){
        //Below will be visible
        tvSubmit.setVisibility(View.VISIBLE);
        btnYes.setVisibility(View.VISIBLE);
        btnNo.setVisibility(View.VISIBLE);
        //set all view invisble except tvSubmit,btnYes,btnNo
        tvMessage.setVisibility(View.INVISIBLE);
        tvQuestion.setVisibility(View.INVISIBLE);
        tvOption1.setVisibility(View.INVISIBLE);
        tvOption2.setVisibility(View.INVISIBLE);
        tvOption3.setVisibility(View.INVISIBLE);
        tvOption4.setVisibility(View.INVISIBLE);
        progressIndicator.setVisibility(View.INVISIBLE);
        imgBack.setVisibility(View.INVISIBLE);
        imgForward.setVisibility(View.INVISIBLE);
        btnTime.setVisibility(View.INVISIBLE);
    }
}
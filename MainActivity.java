package com.mayur.projectpersonalitydevelopment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity{

    private TextView tvSuggest;

    private static Button btnContact,btnExplore,btnAboutUs,btnProfile;

    private RecyclerView rvRecyler;

    private int i=1;

    private YouTubeRecyclerAdapter youTubeRecyclerAdapter;

    private QuizRecyclerAdapter quizRecyclerAdapter;

    private FirebaseRecyclerOptions<GetFirebaseData> options;

    private RecyclerView rvTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing Views
        init();

        //Take user password from shared prefernces
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("Credentials",MODE_PRIVATE);
        String password=sharedPreferences.getString("user_password",null);

        //Take user password from firebase databse

        // Get a reference
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("User/SubUser");

            // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot ds = dataSnapshot.child(password);

                String user_name =  ds.child("first_name").getValue().toString();
                tvSuggest.setText(user_name+",Made For You");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                tvSuggest.setText("Made For You"+databaseError);
            }
        });

        //Set dynamic text

         /*
        try {
            options = new FirebaseRecyclerOptions.Builder<GetFirebaseData>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("VideoKey/Key"),GetFirebaseData.class)
                    .build();
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_LONG).show();
        }

          */

       // SharedPreferences sharedPreferences=getSharedPreferences("MySharedPrefernces",0);

        /*
        ArrayList<GetFirebaseData> arrayList=new ArrayList<>();
       //arrayList.add(new GetFirebaseData(sharedPreferences.getString(""+i,null)));
        arrayList.add( new GetFirebaseData( "<iframe width=\"100%\" height=\"100%\" src=\"https://www" +".youtube.com/embed/5CoKwpezDFE\" frameborder=\"0\" allowfullscreen></iframe>") );
        arrayList.add( new GetFirebaseData("<iframe width=\"100%\" height=\"100%\" src=\"https://www" +".youtube.com/embed/v2euEwGnas8\" frameborder=\"0\" allowfullscreen></iframe>") );
        arrayList.add( new GetFirebaseData("<iframe width=\"100%\" height=\"100%\" src=\"https://www" +".youtube.com/embed/y8Rr39jKFKU\" frameborder=\"0\" allowfullscreen></iframe>") );
        arrayList.add( new GetFirebaseData("<iframe width=\"100%\" height=\"100%\" src=\"https://www" +".youtube.com/embed/8Hg1tqIwIfI\" frameborder=\"0\" allowfullscreen></iframe>") );
        arrayList.add( new GetFirebaseData("<iframe width=\"100%\" height=\"100%\" src=\"https://www" +".youtube.com/embed/uhQ7mh_o_cM\" frameborder=\"0\" allowfullscreen></iframe>") );

         */
        /*options = new FirebaseRecyclerOptions.Builder<GetFirebaseData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("YoutubeVideo/keyuur"),GetFirebaseData.class)
                .build();

         */


        //Adapter Class
      //  youTubeRecyclerAdapter=new YouTubeRecyclerAdapter(options,getApplicationContext());

       // rvRecyler.setAdapter(youTubeRecyclerAdapter);
       // rvRecyler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        //Go on contact page after button is clicked
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ContactUs.class);
                startActivity(intent);
            }
        });

        //Adapter Class for showing test and our services section
        quizRecyclerAdapter=new QuizRecyclerAdapter(getApplicationContext());
        rvTest.setAdapter(quizRecyclerAdapter);
        rvTest.setLayoutManager(new LinearLayoutManager(this));

        //Intent for About and Profile Activity

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

        // prepare data for list
        List<GetFirebaseData> youtubeVideos = prepareList();
        youTubeRecyclerAdapter = new YouTubeRecyclerAdapter(youtubeVideos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        rvRecyler.setLayoutManager(mLayoutManager);
        rvRecyler.setItemAnimator(new DefaultItemAnimator());
        rvRecyler.setAdapter(youTubeRecyclerAdapter);

    }

    //Initialize views
    public void init(){
        btnContact=findViewById(R.id.btnContact);
        rvRecyler=findViewById(R.id.rvRecyler);
        rvTest=findViewById(R.id.rvTest);
        btnExplore=findViewById(R.id.btnExplore);
        btnAboutUs=findViewById(R.id.btnAboutUs);
        btnProfile=findViewById(R.id.btnProfile);
        tvSuggest=findViewById(R.id.tvSuggest);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    //Perapare list for youtube videos
    private List<GetFirebaseData> prepareList() {
        ArrayList mYoutubeVideo = new ArrayList();
        // add first item
        GetFirebaseData video1 = new GetFirebaseData();
        video1.setId(1l);
        video1.setImageUrl("https://i.ytimg.com/vi/zI-Pux4uaqM/maxresdefault.jpg");
        video1.setTitle("Thugs Of Hindostan - Official Trailer | Amitabh Bachchan | Aamir Khan | Katrina Kaif | Fatima");
        video1.setVideoId("5CoKwpezDFE");
        mYoutubeVideo.add(video1);

        // add second item
        GetFirebaseData video2 = new GetFirebaseData();
        video2.setId(2l);
        video2.setImageUrl("https://i.ytimg.com/vi/8ZK_S-46KwE/maxresdefault.jpg");
        video2.setTitle("Colors for Children to Learning with Baby Fun Play with Color Balls Dolphin Slider Toy Set Kids Edu");
        video2.setVideoId("prNj2vCk2t8");
        mYoutubeVideo.add(video2);

        // add third item
        GetFirebaseData video3 = new GetFirebaseData();
        video3.setId(3l);
        video3.setImageUrl("https://i.ytimg.com/vi/8czMWUH7vW4/hqdefault.jpg");
        video3.setTitle("Air Hostess Accepts Marriage Proposal Mid-Air, Airline Fires her.");
        video3.setVideoId("EpROxS3Y_Tk");
        mYoutubeVideo.add(video3);

        // add four item
        GetFirebaseData video4 = new GetFirebaseData();
        video4.setId(4l);
        video4.setImageUrl("https://i.ytimg.com/vi/YrQVYEb6hcc/maxresdefault.jpg");
        video4.setTitle("EXPERIMENT Glowing 1000 degree METAL BALL vs Gunpowder (100 grams)");
        video4.setVideoId("v2euEwGnas8");
        mYoutubeVideo.add(video4);

        // add four item
        GetFirebaseData video5 = new GetFirebaseData();
        video5.setId(5l);
        video5.setImageUrl("https://i.ytimg.com/vi/S84Fuo2rGoY/maxresdefault.jpg");
        video5.setTitle("What happened after Jauhar of Padmavati");
        video5.setVideoId("3gqQevdM7xM");
        mYoutubeVideo.add(video5);

        GetFirebaseData video6 = new GetFirebaseData();
        video6.setId(5l);
        video6.setImageUrl("https://i.ytimg.com/vi/S84Fuo2rGoY/maxresdefault.jpg");
        video6.setTitle("What happened after Jauhar of Padmavati");
        video6.setVideoId("hE6I9apUvrk");
        mYoutubeVideo.add(video6);

        GetFirebaseData video7 = new GetFirebaseData();
        video7.setId(5l);
        video7.setImageUrl("https://i.ytimg.com/vi/S84Fuo2rGoY/maxresdefault.jpg");
        video7.setTitle("What happened after Jauhar of Padmavati");
        video7.setVideoId("3gqQevdM7xM");
        mYoutubeVideo.add(video7);

        GetFirebaseData video8 = new GetFirebaseData();
        video8.setId(5l);
        video8.setImageUrl("https://i.ytimg.com/vi/S84Fuo2rGoY/maxresdefault.jpg");
        video8.setTitle("What happened after Jauhar of Padmavati");
        video8.setVideoId("3gqQevdM7xM");
        mYoutubeVideo.add(video8);

        GetFirebaseData video9 = new GetFirebaseData();
        video9.setId(5l);
        video9.setImageUrl("https://i.ytimg.com/vi/S84Fuo2rGoY/maxresdefault.jpg");
        video9.setTitle("What happened after Jauhar of Padmavati");
        video9.setVideoId("3gqQevdM7xM");
        mYoutubeVideo.add(video9);

        GetFirebaseData video10 = new GetFirebaseData();
        video10.setId(5l);
        video10.setImageUrl("https://i.ytimg.com/vi/S84Fuo2rGoY/maxresdefault.jpg");
        video10.setTitle("What happened after Jauhar of Padmavati");
        video10.setVideoId("3gqQevdM7xM");
        mYoutubeVideo.add(video10);

        mYoutubeVideo.add(video1);
        mYoutubeVideo.add(video2);
        mYoutubeVideo.add(video3);
        mYoutubeVideo.add(video4);
        mYoutubeVideo.add(video5);
        mYoutubeVideo.add(video6);
        mYoutubeVideo.add(video7);
        mYoutubeVideo.add(video8);
        mYoutubeVideo.add(video9);
        mYoutubeVideo.add(video10);
        return mYoutubeVideo;
    }

}
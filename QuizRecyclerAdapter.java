package com.mayur.projectpersonalitydevelopment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.MyViewHolder> {

    Context context;

    public QuizRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_aboutus, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        WebView webView;

        String score;

        Button btnTest,btnQuiz;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //Get Services.html from assets folder with webview
            //You have to create web view to run html in your application.
            init();

            webView.loadUrl("file:///android_asset/AboutUs/services.html");

            //Quiz instructions
            btnTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,TestDescription.class);
                    context.startActivity(intent);
                }
            });
        }

        //Initialize views
        public void init(){
            webView =(WebView)itemView.findViewById(R.id.wvAboutUs);
            btnTest=itemView.findViewById(R.id.btnTest);
           btnQuiz=itemView.findViewById(R.id.btnQuiz);
           btnQuiz.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            if(v==btnQuiz){

                Intent quizIntent = new Intent(context, Quiz.class);
                context.startActivity(quizIntent);
                /*
                try {
                    //if marks is already set then reset quiz after 24 hrs
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", 0);
                    score = sharedPreferences.getString("key", null);
                    if (score.equals("set")) {
                        //Toast.makeText(context,"Quiz will reset after 24 hours",Toast.LENGTH_LONG).show();
                        Intent quizIntent = new Intent(context, Quiz.class);
                        context.startActivity(quizIntent);
                    } else {
                        Intent quizIntent = new Intent(context, Quiz.class);
                        context.startActivity(quizIntent);
                    }

                }
                catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }

                 */
            }
        }
    }
}

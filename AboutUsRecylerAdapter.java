package com.mayur.projectpersonalitydevelopment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AboutUsRecylerAdapter extends RecyclerView.Adapter<AboutUsRecylerAdapter.MyViewHolder>{

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aboutus_page, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        WebView webView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //Get Services.html from assets folder with webview
            //You have to create web view to run html in your application.
            webView =(WebView)itemView.findViewById(R.id.wvAboutUs);

            webView.loadUrl("file:///android_asset/AboutUs/services.html");
        }
    }
}

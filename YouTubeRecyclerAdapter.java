package com.mayur.projectpersonalitydevelopment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class YouTubeRecyclerAdapter extends RecyclerView.Adapter<YouTubeRecyclerAdapter.ViewHolder>{

    List<GetFirebaseData> mYoutubeVideos;

    public YouTubeRecyclerAdapter(List<GetFirebaseData> youtubeVideos) {
        mYoutubeVideos = youtubeVideos;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.youtube_videos, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.webView.loadData("<iframe src='https://www.youtube.com/embed/"+
                mYoutubeVideos.get(position).getVideoId()+"' width='100%' height='100%' style='border: none;'></iframe>",
                "text/html", "utf-8");
    }

    @Override
    public int getItemCount() {
        if (mYoutubeVideos != null && mYoutubeVideos.size() > 0) {
            return mYoutubeVideos.size();
        } else {
            return 1;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        WebView webView;

        public ViewHolder(View itemView) {
            super(itemView);
            webView=itemView.findViewById(R.id.webView);

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.setWebChromeClient(new WebChromeClient() {
            });

        }
    }
}

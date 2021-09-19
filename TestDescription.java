package com.mayur.projectpersonalitydevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class TestDescription extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_description);

        //You have to create web view to run html in your application.
        webView =(WebView)findViewById(R.id.wvTest);

        webView.loadUrl("file:///android_asset/Instructions/info.html");
    }
}
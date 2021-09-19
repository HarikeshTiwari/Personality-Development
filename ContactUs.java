package com.mayur.projectpersonalitydevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class ContactUs extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        //You have to create web view to run html in your application.
        webView =(WebView)findViewById(R.id.HomeHtmlPage);

        webView.loadUrl("file:///android_asset/Contact/index.html");
    }
}
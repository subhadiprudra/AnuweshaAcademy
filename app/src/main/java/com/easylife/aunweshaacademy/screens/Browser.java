package com.easylife.aunweshaacademy.screens;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.easylife.aunweshaacademy.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class Browser extends AppCompatActivity {

    Toolbar toolbar;
    WebView web;
    String url;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();

        url= getIntent().getStringExtra("url");

        web = (WebView)  findViewById(R.id.browser);
        web.setWebViewClient(new MyBrowser());
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.loadUrl(url);



    }



    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }



        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressDialog.dismiss();

        }


    }







    @Override
    public void onBackPressed() {
        if(web.canGoBack()){
            web.goBack();
        }
        else {
            finish();
        } }


}

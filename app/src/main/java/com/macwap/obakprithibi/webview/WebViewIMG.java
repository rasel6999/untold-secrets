package com.macwap.obakprithibi.webview;

/**
 * Created by DELL on 4/5/2017.
 */

import com.macwap.obakprithibi.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewIMG  extends Activity {

    private WebView webView;

    @SuppressLint("NewApi")



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.web_view_img);


        webView = (WebView) findViewById(R.id.myWebView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setSupportZoom(true);



        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
         webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());




        Intent intent = getIntent();

        String bd = intent.getStringExtra("img");


        webView.loadUrl(bd);


         Button btnClose;




        btnClose = (Button) findViewById(R.id.btnClose);


        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WebViewIMG.this.finish();
            }
        });



    }


}
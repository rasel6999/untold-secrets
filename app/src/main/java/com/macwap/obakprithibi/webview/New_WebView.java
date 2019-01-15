package com.macwap.obakprithibi.webview;


        import com.macwap.obakprithibi.R;

        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.webkit.WebSettings;
        import android.webkit.WebViewClient;
        import android.widget.Button;


public class New_WebView   extends Activity {

    private android.webkit.WebView webView;

    @SuppressLint("NewApi")



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.new_webview);


        webView = (android.webkit.WebView) findViewById(R.id.myWebView);

        webView.getSettings().setJavaScriptEnabled(true);

     //   webView.getSettings().setSupportZoom(true);



       // webView.getSettings().setBuiltInZoomControls(true);
       // webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());




        Intent intent = getIntent();

        String bd = intent.getStringExtra("url");

        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
         webView.loadUrl(bd);


        Button btnClose;




        btnClose = (Button) findViewById(R.id.btnClose);


        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                New_WebView.this.finish();
            }
        });



    }


}
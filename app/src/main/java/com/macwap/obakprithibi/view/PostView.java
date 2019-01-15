package com.macwap.obakprithibi.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.macwap.obakprithibi.DatabaseHelper;
import com.macwap.obakprithibi.Fragments.v1.Main2Activity;
import com.macwap.obakprithibi.Macwap_DB;
import com.macwap.obakprithibi.MainActivity;
import com.macwap.obakprithibi.MyApplication;
import com.macwap.obakprithibi.product.v1.Product;
import com.macwap.obakprithibi.product.v1.ProductAdapter;
import com.macwap.obakprithibi.R;
import com.macwap.obakprithibi.untils.FullScreenImage;
import com.macwap.obakprithibi.webview.New_WebView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import android.content.ClipboardManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.jsoup.Jsoup;

import java.util.List;

public class PostView extends AppCompatActivity {
    DatabaseHelper myDB;
    TextView main,title_text,clock,cat,footer,more_articles;
    ImageView img,fav;
    String str;
    String gt_value;
    String defult;
    Spanned gt_message;
    String gt_title;
    String sid_id,bbcode;
    static FloatingActionButton fab;
    static String  BBcode,webData,Category,fulltext,title,sorttext,gt_sid,post_url;
    private WebView webView;
    private static long back_pressed;
    private Button soundbtn;
    private Menu menu;
    MenuItem favorite_button;
    static ProductAdapter mAdapter;
    static RecyclerView productView;
    static Activity activity;
    InterstitialAd mInterstitialAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
/////////////////*

        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_postview));

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


/////////////////////
         str =  Macwap_DB.getString(this,"url_image").replaceAll("&amp;", "&");
        defult =  Macwap_DB.getString(this,"url_default_image");




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
        more_articles      =(TextView)     findViewById(R.id.more_articale);
        footer      =(TextView)     findViewById(R.id.footer);
        cat         =(TextView)     findViewById(R.id.cat);
        clock       =(TextView)     findViewById(R.id.clock);
        title_text  =(TextView)     findViewById(R.id.title_text);
        img         =(ImageView)    findViewById(R.id.image);
        fav         =(ImageView)    findViewById(R.id.fav);
        main        =(TextView)     findViewById(R.id.main);
        myDB        =new DatabaseHelper(this);
        soundbtn    =(Button)     findViewById(R.id.clickIMG);












        fab = (FloatingActionButton) findViewById(R.id.fab);






        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null) {






            String getsid = (String) bd.get("sid");
            sid_id =getsid;







            Cursor res = myDB.getsingleData(sid_id);
            if (res.getCount() == 0) {


                main.setText(getString(R.string.error_main));
                setTitle(getString(R.string.error_title));

                toast(getString(R.string.error_main),"error");

                onBackPressed();


                return;
            } else {


                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext()) {

                    webData =bbcode(res.getString(2));










                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                     gt_message =Html.fromHtml(bbcode(res.getString(2)), Html.FROM_HTML_MODE_COMPACT);

                    }else{
                      gt_message =Html.fromHtml(bbcode(res.getString(2)));


                    }


                    if(res.getString(9).equals("")) {gt_title= res.getString(2);}else{gt_title= res.getString(9);}


                    gt_title= html2text(gt_title);

                    if(gt_title.length() > 120) {

                        gt_title = gt_title.substring(0, 100);
                    }else{
                        gt_title= gt_title;
                    }



                    if(res.getString(8).equals("1"))
                    {
                    fab.setImageResource(R.drawable.red_favorite);
                    fab.setTag("1");
                      }else{
                        fab.setImageResource(R.drawable.green_favorite);
                        fab.setTag("0");

                    }








                    main.setText(gt_message);
                    setTitle(gt_title);
                    title_text.setText(gt_title);
                    clock.setText(" "+res.getString(5));
                    cat.setText(" "+res.getString(10));


                    Category    =   res.getString(10);
                    title       =   gt_title;
                    fulltext    =   String.valueOf(gt_message);
                    gt_sid      =   res.getString(1);
                    post_url    =   getString(R.string.post_url)+""+gt_sid;



                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                    footer.setText(getString(R.string.footer)+" "+post_url);
                    footer.setVisibility(View.VISIBLE);
                    more_articles.setVisibility(View.VISIBLE);
    RecyclerView    productView = (RecyclerView) findViewById(R.id.my_recycler_view);
                    productView.setVisibility(View.VISIBLE);
                        }
                    }, 3000);

                    if(res.getString(3).equals("")){}else {
                        String[] items = res.getString(3).split(",");

                        gt_value  = str+""+ items[0];

                        ImageLoader.getInstance().displayImage(gt_value, img);

                    }


                    if(gt_message.length() > 1500) {

                        sorttext = String.valueOf(gt_message).substring(0, 1495)+"  ...."+getString(R.string.read_more)+""+post_url;
                    }else{
                        sorttext = String.valueOf(gt_message)+""+getString(R.string.read_more)+""+post_url;

                    }





                //  buffer.append("Id: " + res.getString(0) + "\n");

                  //  buffer.append("SID: " + res.getString(1) + "\n");

                  //  buffer.append("MESSAGE: " + res.getString(2) + "\n \n");

                   // buffer.append("value: " + res.getString(3) + "\n \n");

                   // buffer.append("type: " + res.getString(4) + "\n \n");
                   // buffer.append("time: " + res.getString(5) + "\n \n");
                   // buffer.append("by: " + res.getString(6) + "\n \n");
                    //buffer.append("likes: " + res.getString(7) + "\n \n");
                    //buffer.append("favarate: " + res.getString(8) + "\n \n");
                    //buffer.append("Title: " + res.getString(9) + "\n \n");
                   // buffer.append("Category: " + res.getString(10) + "\n \n");

                }

            }

        }else{


        }


        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostView.this, FullScreenImage.class);

               //  img.buildDrawingCache();
                 //Bitmap image= img.getDrawingCache();

              // Bundle extras = new Bundle();
              // extras.putParcelable("imagebitmap", image);

                intent.putExtra("img",gt_value);

                startActivity(intent);

            }
        });







        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if  (fab.getTag().equals("1"))
                {

                    fab.setImageResource(R.drawable.green_favorite);
                    myDB.AddFav(sid_id,0);
                    fab.setTag("0");
                    toast(getString(R.string.remove_to_favorite),"green_favorite");

                }else{

                    myDB.AddFav(sid_id,1);
                    fab.setImageResource(R.drawable.red_favorite);
                    fab.setTag("1");

                    toast(getString(R.string.add_to_favorite),"red_favorite");

                }


              Main2Activity.updateInformation();

            }
        });




   main.setTextIsSelectable(true);
///////////////////webvie

        webView = (WebView) findViewById(R.id.myWebView);
        webView.addJavascriptInterface(new Object()
        {
            @JavascriptInterface           // For API 17+
            public void performClick(String strl)
            {

                Intent intent = new Intent(PostView.this, FullScreenImage.class);
                intent.putExtra("img",strl);
                startActivity(intent);

            }
        }, "ok");


        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL("file:///android_asset/", loadDataCss(webData,""), "text/html", "utf-8", null);
        webView.setWebViewClient(new WebViewClient());



       /////////////////random
        productView = (RecyclerView) findViewById(R.id.my_recycler_view);
        productView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);
         final List<Product> allProducts = myDB.listRandom();



        if(allProducts.size() > 0){
            productView.setVisibility(View.VISIBLE);
            mAdapter = new ProductAdapter(this, allProducts);
            mAdapter.notifyDataSetChanged();
            productView.setAdapter(mAdapter);
        }




    }
    private void requestNewInterstitial() {
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_post_view, menu);
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_post_view, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings)
            {
            return true;
            }
            else
            if (id == R.id.action_fb_msg)
            {
                shareFBmsg(sorttext,title);

            }
            else
            if (id == R.id.action_fb)
            {
                shareFacebook(sorttext,title);

            }
            else
            if (id == R.id.action_copy)
            {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(main.getText());
                toast(getString(R.string.copy_to_clipboard),"copy");

            }
            else
            if (id == R.id.action_whatsapp)
            {
                shareWhatsapp(sorttext,title);

            }
            else
            if (id == R.id.action_share)
            {
                sharetext(sorttext,title);
            }
            else
            if (id == R.id.action_web)
            {
                toast(getString(R.string.open_url_tost),"ic_web");
                Intent intent = new Intent(PostView.this, New_WebView.class);
                intent.putExtra("url",post_url);
                startActivity(intent);
            }

            else
            if (id == R.id.action_remove)
            {
                if( myDB.RemoveData(sid_id,1)==true)
               {
                   toast(getString(R.string.remove_from_list),"remove");
                   Main2Activity.updateInformation();

                   //TabFragment3.loadData("",1);
                    onBackPressed();}
            }
            else
            if (id == R.id.action_fvt)
            {

                fab.performClick();


             }

        return super.onOptionsItemSelected(item);
    }


    public static String bbcode(String html) {


         BBcode = html.replaceAll("\\n", "&#x3C;br/&#x3E;");
         BBcode = BBcode.replaceAll("\\n", "<br/>");
         BBcode = BBcode.replaceAll("&lt;em&gt;", "");
         BBcode = BBcode.replaceAll("&lt;/em&gt;", "");
        BBcode = BBcode.replaceAll("&lt;p", "&lt;div");
        BBcode = BBcode.replaceAll("&lt;/p&gt;", "&lt;/div&gt;");
         BBcode = BBcode.replaceAll("&lt;blockquote&gt;", "");
         BBcode = BBcode.replaceAll("&lt;/blockquote&gt;", "");
         BBcode = BBcode.replaceAll("width", "widt-h");
         BBcode = BBcode.replaceAll("height", "heigh-t");
         BBcode = BBcode.replaceAll("style", "styl-e");
         BBcode = BBcode.replaceAll("https://", "http://");
         BBcode = BBcode.replaceAll("&lt;a", "&lt;p");
         BBcode = BBcode.replaceAll("&lt;/a&gt;", "&lt;/p&gt;");
         BBcode = html2text(BBcode);
        BBcode = BBcode.replaceAll("<img", "<img onclick=\"ok.performClick(this.src);\" ");


        return html;



       // return BBcode;
    }


    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    private void toast (String title,String url ) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.cred_menu_like_popup, (ViewGroup) findViewById(R.id.like_popup_layout));


        ImageView imageView = (ImageView) layout.findViewById(R.id.like_popup_iv);
        int src =  getResources().getIdentifier("com.macwap.obakprithibi:drawable/"+url, null, null);
        imageView.setImageResource(src);
        TextView text = (TextView) layout.findViewById(R.id.like_popup_tv);
        text.setText(title);

        Toast toast = new Toast( getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 300);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        toast.show();


    }

    static String  loadDataCss (String realdata,String url ) {

      String data= "  <link href=\"style.css\" type=\"text/css\" rel=\"stylesheet\"/> "+realdata+" ";


        return data;


    }



    public   void sound_function(View v ) {


            toast("sound test","copy");


    }











    public   void open_cat_function(View v ) {



        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("tab","3");
        intent.putExtra("category",Category);
        this.startActivity(intent);

    }


    public   void ready_function(View v ) {

         String row = String.valueOf(v.getTag());

        if(row.equals("1"))
        {
            shareFBmsg(sorttext,title);

        }
        else
            if(row.equals("2"))
        {
            shareFacebook(sorttext,title);

        }
        else
            if(row.equals("3"))
        {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(main.getText());
            toast(getString(R.string.copy_to_clipboard),"copy");

        }
        else
            if(row.equals("4"))
            {
                shareWhatsapp(sorttext,title);
            }
        else
            if(row.equals("5"))
            {
            sharetext(sorttext,title);
         }
         else
             if(row.equals("6"))
             {
                 toast(getString(R.string.open_url_tost),"ic_web");
                 Intent intent = new Intent(PostView.this, New_WebView.class);
                 intent.putExtra("url",post_url);
                 startActivity(intent);
             }




    }


    private void shareWhatsapp(String rdx,String title) {


        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, rdx);
        try {
            this.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            toast("Whatsapp have not been installed.","error");

        }


    }


    private void shareFacebook(String rdx,String title) {


        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.facebook.katana");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, rdx);
        try {
            this.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            toast("Facebook have not been installed.","error");

        }


    }


    private void shareFBmsg(String rdx,String title) {


        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.facebook.orca");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, rdx);
        try {
            this.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            toast("Please Install Facebook Messenger.","error");

        }


    }





    private void sharetext(String rdx,String title) {


        String shareBody = rdx;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, ""));



    }
    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
    }





}

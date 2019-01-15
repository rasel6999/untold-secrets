package com.macwap.obakprithibi.untils;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.macwap.obakprithibi.R;


public class ProfileScreenXMLUIDesign extends AppCompatActivity {
ImageView add_friend,drop_down_option_menu,user_profile_photo;
    TextView email,phone,website;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.material_design_profile_screen_xml_ui_design);
















        email                   =(TextView)   findViewById(R.id.email);
        phone                   =(TextView)   findViewById(R.id.phone);
        website                   =(TextView)   findViewById(R.id.website);
        drop_down_option_menu   = (ImageView) findViewById(R.id.drop_down_option_menu);
        add_friend              = (ImageView) findViewById(R.id.add_friend);
        user_profile_photo      = (ImageView) findViewById(R.id.user_profile_photo);
        user_profile_photo.bringToFront();



        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {






                toast(getString(R.string.open_url_tost),"loading");

                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setType("plain/text");
                sendIntent.setData(Uri.parse(getString(R.string.dev_email)));
                sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.dev_email)});
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                sendIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(sendIntent);

            }});

        phone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toast(getString(R.string.open_url_tost),"loading");

                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", getString(R.string.whatsapp_number), null)));

            }});

        website.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                toast(getString(R.string.open_url_tost),"loading");

                Uri uri = Uri.parse(getString(R.string.dev_link));
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(goToMarket);
            }});





                add_friend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
                if (isWhatsappInstalled) {
                    toast(getString(R.string.open_url_tost),"loading");

                    String whatsapp_number = getResources().getString(R.string.whatsapp_number);
                    Uri uri = Uri.parse("smsto:" + whatsapp_number);
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);


                } else {


                    toast(getString(R.string.whatsapp_not_install),"error");

                    Uri uri = Uri.parse("market://details?id=com.whatsapp");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goToMarket);

            }}
        });

        drop_down_option_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                boolean isWhatsappInstalled = whatsappInstalledOrNot("com.facebook.orca");
                if (isWhatsappInstalled) {
                    Uri uri = Uri.parse("fb-messenger://user/");
                    uri = ContentUris.withAppendedId(uri, Long.parseLong("100007081955368"));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    toast(getString(R.string.open_url_tost),"loading");


                } else {
                    toast(getString(R.string.messenger_not_install),"error");

                    Uri uri = Uri.parse("market://details?id=com.facebook.orca");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goToMarket);

                }







            }
        });

    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
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




}
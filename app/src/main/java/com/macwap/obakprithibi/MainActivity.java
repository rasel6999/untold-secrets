package com.macwap.obakprithibi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
 import com.google.firebase.messaging.FirebaseMessaging;

import com.macwap.obakprithibi.Adapter.PagerAdapter;
import com.macwap.obakprithibi.Fragments.v1.TabFragment3;
import com.macwap.obakprithibi.Service.MyService;
import com.macwap.obakprithibi.untils.ProfileScreenXMLUIDesign;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    InterstitialAd mInterstitialAd;
    Spanned notice;
    static DatabaseHelper myDB;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    TextView notice_update;
    boolean AdsShow=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, MyService.class));
         myDB = new DatabaseHelper(this);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


      final   View hView =  navigationView.getHeaderView(0);







       // subscribeToPushService();




        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);


        TextView newTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        newTab.setText(getString(R.string.recent));
       // newTab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.recent, 0, 0, 0);

        final TextView newTab2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        newTab2.setText(getString(R.string.favorite));
      //   newTab2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.favarite, 0, 0, 0);

        final TextView newTab3 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        newTab3.setText(getString(R.string.category));
      //  newTab3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.recent, 0, 0, 0);





        tabLayout.addTab(tabLayout.newTab().setCustomView(newTab));
        tabLayout.addTab(tabLayout.newTab().setCustomView(newTab2));
        tabLayout.addTab(tabLayout.newTab().setCustomView(newTab3));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
             }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
             }
        });




        final Cursor res = myDB.getAllCat();

        if (res.getCount() == 0) {

        } else {


            StringBuffer buffer = new StringBuffer();

            while (res.moveToNext()) {
                String id=  "1";

                Menu menu2 = navigationView.getMenu();
                MenuItem item = menu2.add(R.id.nmain, Integer.parseInt(id), Menu.NONE, res.getString(10));
                item.setIcon(R.drawable.new_folder).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //  startActivity(new Intent(MainActivity.this, PostView.class));
                        String ox= String.valueOf(item.getTitle());


                        int size = navigationView.getMenu().size();
                        for (int i = 0; i < size; i++) {
                            navigationView.getMenu().getItem(i).setChecked(false);
                        }




                        item.setChecked(true);
                        viewPager.setCurrentItem(3);
                        TabFragment3.loadData(ox,0);
                        return false;
                    }
                });

            }

        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

        Intent intent = getIntent();

       String bd = intent.getStringExtra("tab");

        if(bd != null) {
            final String category = intent.getStringExtra("category");
          if(bd.equals(0)|| bd.equals("")||bd==null){}else
          {

                     viewPager.setCurrentItem(Integer.parseInt(bd));
                    TabFragment3.loadData(category,0);






          }

        }

            }
        }, 100);

        notice_update = (TextView)hView.findViewById(R.id.notice);




        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notice =Html.fromHtml(Macwap_DB.getString(MainActivity.this,"name"), Html.FROM_HTML_MODE_COMPACT);

        }else{
            notice =Html.fromHtml(Macwap_DB.getString(MainActivity.this,"name"));
        }

     notice_update.setText(notice);

    mFirebaseInstance = FirebaseDatabase.getInstance() ;

        mFirebaseInstance.getReference("notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appTitle = dataSnapshot.getValue().toString();
                Log.e("Hey", appTitle);
                Macwap_DB.setString(MainActivity.this,"name",(String) appTitle);




                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    notice =Html.fromHtml(appTitle, Html.FROM_HTML_MODE_COMPACT);

                }else{
                    notice =Html.fromHtml(appTitle);
                }



                notice_update.setText(notice);







            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Hey", "Failed to read app title value.", error.toException());
            }
        });





/////////////////






        mFirebaseInstance.getReference("banner_home").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String show = dataSnapshot.getValue().toString();

            if(show.equals("true")) {

                MobileAds.initialize(getApplicationContext(), getString(R.string.banner_postview));
                final AdView mAdView = (AdView) findViewById(R.id.adView);
                final AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);

            }
            }



            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Hey", "Failed to read app title value.", error.toException());
            }
        });


       if(Macwap_DB.getString(MainActivity.this,"banner_native").equals("true"))
        {
            mInterstitialAd = new InterstitialAd(MainActivity.this);
            mInterstitialAd.setAdUnitId("ca-app-pub-5768679076619555/9586602221");
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                }
            });

            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                requestNewInterstitial();
            }
            AdsShow=true;
        }





        mFirebaseInstance.getReference("banner_native").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String show = dataSnapshot.getValue().toString();

                if(show.equals("true")) {


                    Macwap_DB.setString(MainActivity.this,"banner_native",(String) "true");

                }else  {


                    Macwap_DB.setString(MainActivity.this,"banner_native",(String) "false");

                }


            }



            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Hey", "Failed to read app title value.", error.toException());
            }
        });








/////////////////////





     }

////////////////////////////////////////



    /////////////////////////
    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("a-1");

    }

    private void requestNewInterstitial() {
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
    }






    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = super.getTheme();
            // theme.applyStyle(R.style.AppTheme, true);

        // you could also use a switch if you have many themes that could apply
        return theme;
    }


    @Override
    public void onBackPressed() {
        //try to get show ads from balloon and db

       if(AdsShow==true)
       {if(Macwap_DB.getString(MainActivity.this,"banner_native").equals("true"))
        {
            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                requestNewInterstitial();
            }

        }}






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
         }
        else if (id == R.id.action_share) {



            sharetext(getString(R.string.share_text)+"http://play.google.com/store/apps/details?id="+getPackageName());


        }
        else if (id == R.id.action_exit) {

            finish();
            System.exit(0);

        }
        else if (id == R.id.action_more_app) {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://search?q=pub:macwap.com"));
            startActivity(intent);

        }
        else if(id==R.id.action_rate)
        {  Uri uri = Uri.parse("market://details?id="+getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" +getPackageName())));
            }}
        return super.onOptionsItemSelected(item);
    }



    private void sharetext(String rdx) {


        String shareBody = rdx;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));



    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


       // Toast.makeText(this,id, Toast.LENGTH_LONG).show();


          if (id == R.id.nav_share) {
              sharetext(getString(R.string.share_text)+"http://play.google.com/store/apps/details?id="+getPackageName());

        } else if (id == R.id.nav_send) {
              Intent intent = new Intent(this, ProfileScreenXMLUIDesign.class);
               startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

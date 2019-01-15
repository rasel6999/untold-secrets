package com.macwap.obakprithibi.Fragments.v1;



import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
 import com.google.firebase.messaging.FirebaseMessaging;
import com.macwap.obakprithibi.DatabaseHelper;
import com.macwap.obakprithibi.Fragments.v2.FourFragemnt;
import com.macwap.obakprithibi.Fragments.v2.OneFragment;
import com.macwap.obakprithibi.Fragments.v2.ThreeFragment;
import com.macwap.obakprithibi.Fragments.v2.TwoFragment;
import com.macwap.obakprithibi.Macwap_DB;
import com.macwap.obakprithibi.MyApplication;
import com.macwap.obakprithibi.R;
import com.macwap.obakprithibi.Service.MyService;


public class Main2Activity extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    Spanned notice;
    static DatabaseHelper myDB;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    TextView notice_update;
    boolean AdsShow=false;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons2= {
            R.drawable.main_tab_user,
            R.drawable.main_tab_reader,
            R.drawable.main_tab_fab,
            R.drawable.main_tab_global
    };




    private int[] tabIcons = {
            R.drawable.ic_user_circle_white_32dp,
            R.drawable.ic_reader_white_32dp,
            R.drawable.ic_star_white_32dp,
            R.drawable.ic_globe_white_32dp
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mFirebaseInstance = FirebaseDatabase.getInstance() ;
        setDataFirebase();

        startService(new Intent(this, MyService.class));
        myDB = new DatabaseHelper(this);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        viewPager.setCurrentItem(1);


         mFirebaseInstance.getReference("banner_home").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String show = dataSnapshot.getValue().toString();

               if(show.equals("true")) {
/*
                   MobileAds.initialize(getApplicationContext(), getString(R.string.banner_postview));
                    final AdView mAdView = (AdView) findViewById(R.id.adView);
                    final AdRequest adRequest = new AdRequest.Builder().build();
                   mAdView.loadAd(adRequest);
*/

            }}



            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Hey", "Failed to read app title value.", error.toException());
            }
        });

/*



        if(Macwap_DB.getString(this,"banner_native").equals("true")) {
            nativebanner();

        }

        mFirebaseInstance.getReference("banner_native").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String show = dataSnapshot.getValue().toString();

                if(show.equals("true")) {


                    Macwap_DB.setString(Main2Activity.this,"banner_native",(String) "true");
                        nativebanner();

                }else  {


                    Macwap_DB.setString(Main2Activity.this,"banner_native",(String) "false");

                }


            }



            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Hey", "Failed to read app title value.", error.toException());
            }
        });

*/


    }


    private void nativebanner (){

        mInterstitialAd = new InterstitialAd(Main2Activity.this);
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




    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "ONE");
        adapter.addFragment(new TwoFragment(), "TWO");
        adapter.addFragment(new ThreeFragment(), "THREE");
        adapter.addFragment(new FourFragemnt(), "Four");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);





    }


    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("var4");

    }

    private void requestNewInterstitial() {
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
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


    private void setupTabIcons() {

/*
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("ONE");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_tab_reader, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("TWO");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.favarite, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("THREE");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.favarite, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Four");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.favarite, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

*/


        if (Build.VERSION.SDK_INT >= 21) {

            tabLayout.getTabAt(0).setIcon(tabIcons2[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons2[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons2[2]);
            tabLayout.getTabAt(3).setIcon(tabIcons2[3]);
        }else {

            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);
            tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        }
    }



    private void setDataFirebase() {




         mFirebaseInstance.getReference("url_image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue().toString();

                Macwap_DB.setString(Main2Activity.this,"url_image",(String) url);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Firebase", "Failed to read url_image from firebase", error.toException());
            }
        });

        mFirebaseInstance.getReference("url_site").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue().toString();

                Macwap_DB.setString(Main2Activity.this,"url_site",(String) url);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Firebase", "Failed to read url_site from firebase", error.toException());
            }
        });

        mFirebaseInstance.getReference("url_ping_singleid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue().toString();

                Macwap_DB.setString(Main2Activity.this,"url_ping_singleid",(String) url);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Firebase", "Failed to url_ping_singleid from firebase", error.toException());
            }
        });

        mFirebaseInstance.getReference("url_ping_allid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue().toString();

                Macwap_DB.setString(Main2Activity.this,"url_ping_allid",(String) url);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Firebase", "Failed to url_ping_allid from firebase", error.toException());
            }
        });

        mFirebaseInstance.getReference("url_default_image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue().toString();

                Macwap_DB.setString(Main2Activity.this,"url_default_image",(String) url);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Firebase", "Failed to url_default_image from firebase", error.toException());
            }
        });
    }




    public static void updateInformation() {


        if(MyApplication.isActivityVisible()==true)
        {
            // OneFragment.uupdateData();

            TwoFragment.uupdateData();
            ThreeFragment.uupdateData();

            //   TabFragment3.loadData("",1);
        }
    }

/////////////////////////////////////////////////next classs

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0 : return new OneFragment();
                case 1 : return new TwoFragment();
                case 2 : return new ThreeFragment();
                case 3 : return new FourFragemnt();
            }



            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return null;
       // return mFragmentTitleList.get(position);
        }

    }


}

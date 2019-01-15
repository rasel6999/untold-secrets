package com.macwap.obakprithibi.Fragments.v1;

/**
 * Created by DELL on 3/30/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.macwap.obakprithibi.DatabaseHelper;
import com.macwap.obakprithibi.product.v1.Product;
import com.macwap.obakprithibi.product.v1.ProductAdapter;
import com.macwap.obakprithibi.R;

import java.util.List;
import java.util.Timer;


public class TabFragment3 extends Fragment {

    static DatabaseHelper myDB;
    static TextView tvData,title_pad;
    TextView tvTitle;
    Timer timer;
    static ProductAdapter mAdapter;
    static RecyclerView productView;
    static Activity activity;
    static LinearLayout Category;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab_fragment_3, container, false);



        title_pad = (TextView) rootView.findViewById(R.id.title_pad);
        productView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        productView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);
        myDB = new DatabaseHelper(getActivity());
        activity= getActivity();
        Category =(LinearLayout) rootView.findViewById(R.id.my_new_view);


/*

        final Cursor res = myDB.getAllCat();

        if (res.getCount() == 0) {


        } else {


            StringBuffer buffer = new StringBuffer();

            while (res.moveToNext()){
                buffer.append("Category: " + res.getString(10) + "\n \n");



                TextView ttextView = new TextView(getActivity());
                ttextView.setTag(res.getString(10));
                ttextView.setTextSize(18);
                ttextView.setTextColor(Color.BLACK);
                ttextView.setClickable(true);
                ttextView.setPadding(20, 20, 20, 20);
                ttextView.setClickable(true);
                ttextView.setText(res.getString(10));
                ttextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.folder, 0, 0, 0);
                ttextView.setBackgroundResource(R.drawable.textview_backgrund);
                ttextView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String  r = String.valueOf(v.getTag());

                        loadData(r,0);
                        title_pad.setTag(r);
                    }
                });


                Category.addView(ttextView);

            }




           // textView.setText(buffer.toString());



        }
/////////////


    //    final Button loginButton = (Button) view.findViewById(R.id.loginButton);
        title_pad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                title_pad.setVisibility(View.GONE);
                productView.setVisibility(View.GONE);
                Category.setVisibility(View.VISIBLE);
            }
        });

*/

     //   setHasOptionsMenu(true);



        //  return  rootView;


        return null;

    }




    public static void loadData(String id,int type){


       if(type==1)
       {
           if(title_pad.getVisibility()==View.VISIBLE)
           {


               title_pad.setVisibility(View.VISIBLE);
               Category.setVisibility(View.GONE);
               title_pad.setVisibility(View.VISIBLE);
               title_pad.setText(String.valueOf(title_pad.getTag()));

               final List<Product> allProducts = myDB.listCategory(String.valueOf(title_pad.getTag()));
               mAdapter = new ProductAdapter(activity,allProducts);
               productView.setAdapter(mAdapter);





                }



       }   else {


           title_pad.setVisibility(View.VISIBLE);
           Category.setVisibility(View.GONE);
           title_pad.setVisibility(View.VISIBLE);
           title_pad.setText(id);

           final List<Product> allProducts = myDB.listCategory(id);

           if (allProducts.size() > 0) {
               productView.setVisibility(View.VISIBLE);


               mAdapter = new ProductAdapter(activity, allProducts);

               mAdapter.notifyDataSetChanged();

               productView.setAdapter(mAdapter);


           }
       }

    }






    public static void uupdateData(){

        final List<Product> allProducts = myDB.listFavorite();
        mAdapter = new ProductAdapter(activity,allProducts);
        productView.setAdapter(mAdapter);
    }


/*

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        }*/

    @Override
    public void onResume() {
        super.onResume();




            if (getView() == null) {
                return;
            }

            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                        if(productView.getVisibility()==View.VISIBLE) {
                            productView.setVisibility(View.GONE);
                            Category.setVisibility(View.VISIBLE);
                            title_pad.setVisibility(View.GONE);
                            return true;
                        }else {

                            // handle back button's click listener
                            // return true;
                            return false;
                        }
                    }
                  return false;
                }
            });
        }







}
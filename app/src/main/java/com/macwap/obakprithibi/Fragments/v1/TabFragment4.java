package com.macwap.obakprithibi.Fragments.v1;

/**
 * Created by DELL on 3/30/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.macwap.obakprithibi.DatabaseHelper;
import com.macwap.obakprithibi.product.v1.Product;
import com.macwap.obakprithibi.product.v1.ProductAdapter;
import com.macwap.obakprithibi.R;

import java.util.List;
import java.util.Timer;


public class TabFragment4 extends Fragment {

    static DatabaseHelper myDB;
    TextView tvData;
    TextView tvTitle;
    Timer timer;
    static ProductAdapter mAdapter;
    static RecyclerView productView;
    static Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab_fragment_4, container, false);





        productView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        productView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);
        myDB = new DatabaseHelper(getActivity());
        final List<Product> allProducts = myDB.listFavorite();

        activity= getActivity();





        if(allProducts.size() > 0){
            productView.setVisibility(View.VISIBLE);




            mAdapter = new ProductAdapter(getActivity(), allProducts);

            mAdapter.notifyDataSetChanged();

            productView.setAdapter(mAdapter);

            //productView.scrollBy(0, 0);

        }else {
            //productView.setVisibility(View.GONE);
            //Toast.makeText(getActivity(), getString(R.string.error_not_favarite), Toast.LENGTH_LONG).show();
        }



















        //  return  rootView;


        return null;

    }




    public static void uupdateData(){

        final List<Product> allProducts = myDB.listFavorite();
        mAdapter = new ProductAdapter(activity,allProducts);
        productView.setAdapter(mAdapter);
    }












}
package com.macwap.obakprithibi.Fragments.v2;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.macwap.obakprithibi.DatabaseHelper;
import com.macwap.obakprithibi.product.v1.Product;
import com.macwap.obakprithibi.product.v1.ProductAdapter;
import com.macwap.obakprithibi.R;

import java.util.List;
import java.util.Timer;


public class TwoFragment extends Fragment{


    static DatabaseHelper myDB;
    TextView tvData;
    TextView tvTitle;
    Timer timer;
    static ProductAdapter mAdapter;
    static RecyclerView productView;
    static Activity activity;




    public  TwoFragment() {
     }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_two, container, false);




        productView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        productView.setNestedScrollingEnabled(false);




        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);
        myDB = new DatabaseHelper(getActivity());
        final List<Product> allProducts = myDB.listProducts("",3,5);





        activity= getActivity();





        if(allProducts.size() > 0){
            productView.setVisibility(View.VISIBLE);




            mAdapter = new ProductAdapter(getActivity(), allProducts);

            mAdapter.notifyDataSetChanged();

            productView.setAdapter(mAdapter);


        }else {
            productView.setVisibility(View.GONE);

            Toast.makeText(getActivity(), getString(R.string.error_not_data), Toast.LENGTH_LONG).show();


        }










        setHasOptionsMenu(true);




        return rootView;
    }





    public static void uupdateData(){

        final List<Product> newItemsx = myDB.listProducts("0",20,10);

      /* int position = newItems.size() + 1;
        newItems.addAll(newItems);
        mAdapter.notifyItemChanged(position, newItems);

*/



     //   productView.setAdapter(mAdapter);
        mAdapter.loadMoreMessages("xx");
        mAdapter.notifyItemInserted(newItemsx.size() - 1);


    }


}
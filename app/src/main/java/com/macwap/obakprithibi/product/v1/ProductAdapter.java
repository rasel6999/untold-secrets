package com.macwap.obakprithibi.product.v1;



/**
 * Created by DELL on 3/30/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.macwap.obakprithibi.DatabaseHelper;
import com.macwap.obakprithibi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{
    private Context context;
    private List<Product> listProducts;
    DatabaseHelper myDB;

    public ProductAdapter(Context context, List<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
        myDB = new DatabaseHelper(context);


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);




    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout2, parent, false);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {


        ////   if(position == 10 - 1){
      //  loadMoreMessages();
        ////  }



        final Product singleProduct = listProducts.get(position);
       // holder.name.setText(singleProduct.getTitle());

         holder.name.setText(singleProduct.getTitle());





        holder.message.setText(singleProduct.getMessage());
        holder.time.setText(singleProduct.getTime());
        holder.category.setText(singleProduct.getCategory());
        holder.card.setTag(singleProduct.getSid());


        ImageLoader.getInstance().displayImage(singleProduct.getValue(), holder.image, new ImageLoadingListener() {


            @Override
            public void onLoadingStarted(String s, View view) {

                holder.loading_image.setVisibility(View.VISIBLE);
                holder.loading_image_tr.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                holder.loading_image.setVisibility(View.GONE);
                holder.loading_image_tr.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });







    }

    public void loadMoreMessages(String x) {
        Toast.makeText(context, "x"+x, Toast.LENGTH_LONG).show();

    }


    @Override
    public int getItemCount() {
        return listProducts.size();
    }

}
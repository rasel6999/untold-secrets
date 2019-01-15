package com.macwap.obakprithibi.product.v1;

/**
 * Created by DELL on 3/30/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.macwap.obakprithibi.R;
import com.macwap.obakprithibi.view.PostView;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public TextView name,message,time,loading_image_tr,category;
    public ImageView image;
    public ProgressBar loading_image;
    private Context context;
    public CardView card;


    public ProductViewHolder(final View itemView) {
        super(itemView);
        context = itemView.getContext();

        name = (TextView)itemView.findViewById(R.id.textViewName);
        message = (TextView)itemView.findViewById(R.id.message);
        time = (TextView)itemView.findViewById(R.id.time);
        category = (TextView)itemView.findViewById(R.id.category);
        image = (ImageView) itemView.findViewById(R.id.imageView);
        loading_image_tr = (TextView)itemView.findViewById(R.id.loading_image_tr);
        loading_image = (ProgressBar) itemView.findViewById(R.id.loading_image);
        card = (CardView) itemView.findViewById(R.id.card_view);



        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

             Intent intent = new Intent(context, PostView.class);

                intent.putExtra("sid",(String)v.getTag());
                context.startActivity(intent);





            }
        });
    }
}






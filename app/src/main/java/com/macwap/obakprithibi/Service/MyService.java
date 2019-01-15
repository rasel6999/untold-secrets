package com.macwap.obakprithibi.Service;



import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.macwap.obakprithibi.AllstoryID;
import com.macwap.obakprithibi.DatabaseHelper;
import com.macwap.obakprithibi.product.v1.Post;
import com.macwap.obakprithibi.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by DELL on 4/5/2017.
 */

public class MyService extends Service {

    public static final int notify = 1000* 60*5;  //interval between two services(Here Service run every 5 Minute)
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling
    String  s_message,s_title,s_id,s_by,s_time,s_value,s_type,s_likes,s_category,s_url,s_remove,message, imageUri, TrueOrFlase, u_url,title,update,dmid;

    Gson gson = new Gson();
    List<AllstoryID> allstoryIDs;
    List<Post> posts;
    DatabaseHelper myDB;


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        if (mTimer != null) // Cancel if already existed
            mTimer.cancel();
        else
            mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);   //Schedule task
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();    //For Cancel Timer
        Toast.makeText(this, "Service is Destroyed", Toast.LENGTH_SHORT).show();
    }

    //class TimeDisplay for handling task
    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String url = getResources().getString(R.string.site_all_api);

                    newvolleyStringRequest(url);


                }
            });
        }
    }


    ///////////////////////////


    private void newvolleyStringRequest(String url){








        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Result", response);


                        allstoryIDs = gson.fromJson(response, new TypeToken<List<AllstoryID >>(){}.getType());




                        for (AllstoryID  allstoryID : allstoryIDs){

                            s_id=allstoryID.getId();
                            myDB = new DatabaseHelper(MyService.this);

                            Cursor res = myDB.getexit(s_id);
                            if (res.getCount() == 0) {


                                String url = getResources().getString(R.string.site_singel_api);

                                volleyStringRequest(url+"&id="+s_id);
                            }







                        }






                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {



                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void volleyStringRequest(String url){


        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Result", response);


                        posts = gson.fromJson(response, new TypeToken<List<Post>>(){}.getType());



                        for (Post post : posts){

                            s_message=post.getMessage();
                            s_id=post.getId();
                            s_by=post.getBy();
                            s_type=post.getType();
                            s_time=post.getTime();
                            s_value=post.getValue();
                            s_likes=post.getLikes();
                            s_title=post.getTitle();
                            s_category=post.getCategory();

                            myDB = new DatabaseHelper(MyService.this);

                            boolean isInserted =  myDB.insertData(s_id, s_message,s_by,s_type,s_value,s_time,s_likes,s_title,s_category,s_url,s_remove);





                            if(isInserted == true) {



                            }



                        }






                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {



                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    ///////////////////////////////

}
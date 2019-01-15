package com.macwap.obakprithibi.Firebase;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.macwap.obakprithibi.DatabaseHelper;
import com.macwap.obakprithibi.Macwap_DB;
import com.macwap.obakprithibi.Fragments.v1.Main2Activity;
import com.macwap.obakprithibi.MainActivity;
import com.macwap.obakprithibi.product.v1.Post;
import com.macwap.obakprithibi.product.v1.ProductAdapter;
import com.macwap.obakprithibi.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessageService";
    Bitmap bitmap;
    Bitmap iconbitmap;
    private String mtitle,story_id;
    private String mid;
    ProductAdapter mAdapter;
    RecyclerView productView;
    String  url_ping_allid,url_ping_singleid;


    String  s_message,s_title,s_id,s_by,s_time,s_value,s_type,s_likes,s_category,s_url,s_remove,message, imageUri, TrueOrFlase, u_url,title,update,dmid;
    Gson gson = new Gson();
    List<Post> posts;

    DatabaseHelper myDB;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        url_ping_allid =  Macwap_DB.getString(getApplicationContext(),"url_ping_allid").replaceAll("&amp;", "&");
        url_ping_singleid=  Macwap_DB.getString(getApplicationContext(),"url_ping_singleid").replaceAll("&amp;", "&");

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        //The message which i send will have keys named [message, image, AnotherActivity] and corresponding values.
        //You can change as per the requirement.

        //message will contain the Push Message
        message = remoteMessage.getData().get("message");
        imageUri = remoteMessage.getData().get("image");
        story_id = remoteMessage.getData().get("story_id");







        TrueOrFlase = remoteMessage.getData().get("AnotherActivity");
        u_url = remoteMessage.getData().get("url");
        dmid = remoteMessage.getData().get("mid");
        title = remoteMessage.getData().get("title");
        update = remoteMessage.getData().get("update");
        bitmap = getBitmapfromUrl(imageUri);
        iconbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);


        if(dmid  !=null)
        {
            mid= dmid;
        }else {
            mid = "0";

        }

        if(title  !=null)
        {
         mtitle= title;
        }else {
            mtitle = getResources().getString(R.string.app_name);

        }



        if (story_id !=null)
        {

          volleyStringRequest(url_ping_singleid+"&id="+story_id);

        }

else if(imageUri !=null)
{
    sendNotification(message, bitmap, TrueOrFlase, u_url, iconbitmap, mid ,mtitle,update);
}else {
    sendNotification2(message, bitmap, TrueOrFlase, u_url, iconbitmap, mid ,mtitle,update);
}







    }





    /**
     * Create and show a simple notification containing the received FCM message.
     */

    private void sendNotification(String messageBody, Bitmap image, String TrueOrFalse, String u_url,  Bitmap icons, String mid, String app_name, String update) {

        int umid= Integer.parseInt(mid);




        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("AnotherActivity", TrueOrFalse);
        intent.putExtra("url", u_url);
        intent.putExtra("update", update);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, umid /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(icons)/*Notification icon image*/
                .setSmallIcon(R.drawable.book)
                .setContentTitle(app_name  )
                .setContentText(messageBody)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image))/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(umid /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotification2(String messageBody, Bitmap image, String TrueOrFalse, String u_url,  Bitmap icons, String mid,String app_name, String update) {



        int umid= Integer.parseInt(mid);


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("AnotherActivity", TrueOrFalse);
        intent.putExtra("url", u_url);
        intent.putExtra("update", update);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, umid /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(icons)/*Notification icon image*/
                .setSmallIcon(R.drawable.book)
                .setContentTitle(app_name  )
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify( umid/* ID of notification */, notificationBuilder.build());
    }



    /*
    *To get a Bitmap image from the URL received
    * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }



  ///////////////

/*
    public void updateData(){
        myDB = new DatabaseHelper(MyFirebaseMessagingService.this);

        final List<Product> allProducts = myDB.listProducts();
        mAdapter = new ProductAdapter(this,allProducts);
        productView.setAdapter(mAdapter);
    }

*/










    private void volleyStringRequest(String url){



        //boolean isInserted =  myDB.insertData(story_id, s_message,s_by,s_type,s_value,s_time,s_likes,s_title,s_category);


       // if(isInserted == true) {

       /// }






        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Result", response);


                        // JSON to Java Object
                        posts = gson.fromJson(response, new TypeToken<List<Post>>(){}.getType());

                        //   printTitle();



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

                            myDB = new DatabaseHelper(MyFirebaseMessagingService.this);

                            boolean isInserted =  myDB.insertData(s_id, s_message,s_by,s_type,s_value,s_time,s_likes,s_title,s_category,s_url,s_remove);






                            if(isInserted == true) {

                                message =s_message;


                        sendNotification2(message, bitmap, TrueOrFlase, u_url, iconbitmap, mid ,mtitle,update);



                Main2Activity.updateInformation();

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





}
package com.macwap.obakprithibi;

/**
 * Created by DELL on 3/24/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateUtils;

import com.macwap.obakprithibi.product.v1.Product;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import org.jsoup.Jsoup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "macwap.db";
    public static final String TABLE_NAME = "story_table";
    String str,gt_value,defult,gt_message,gt_title;

    public static final String COL_1 = "ID", COL_2 = "SID", COL_3 = "MESSAGE", COL_4 ="VALUE", COL_5 ="TYPE", COL_6 ="TIME",
            COL_7 ="BY", COL_8 = "LIKES", COL_9= "FAVOURITE", COL_10= "TITLE", COL_11= "CATEGORY", COL_12= "URL", COL_13= "REMOVE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null , 1);
        SQLiteDatabase db = this.getWritableDatabase();




         str =  Macwap_DB.getString(context,"url_image").replaceAll("&amp;", "&");

         defult =  Macwap_DB.getString(context,"url_default_image");












    }


/*
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " ( " + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2  + " TEXT, " + COL_3 + " TEXT,"+ COL_4  + " TEXT,"+ COL_5  + " TEXT,"+ COL_6  + " TEXT,"
                + COL_7  + " TEXT,"+ COL_8  + " TEXT," + COL_9  + " TEXT,"+ COL_10  + " TEXT,"+ COL_11  + " TEXT,"+ COL_12  + " TEXT,"+ COL_13  + " TEXT)");

    }
*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXIST" + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String sid, String message,String by,String type,String value,String time,String likes,String title,String category,String url,String remove){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, sid);
        contentValues.put(COL_3, message);
        contentValues.put(COL_4, value);
        contentValues.put(COL_5, type);
        contentValues.put(COL_6, time);
        contentValues.put(COL_7, by);
        contentValues.put(COL_8, likes);
        contentValues.put(COL_9, 0);
        contentValues.put(COL_10, title);
        contentValues.put(COL_11, category);
        contentValues.put(COL_12, url);
        contentValues.put(COL_13, 0);




        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result== -1)
        {
            return false;
        }
        else {






          //  TabFragment1.uupdateData();




            return true;
        }
    }




    public Cursor getsingleData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_NAME+ " where " + COL_2 + "='" + id + "' AND "+COL_13+"='0' " , null);
        return res;

    }

    public boolean RemoveData (String id, int val){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_13, val);
        db.update(TABLE_NAME, contentValues, "SID = ?", new String[] {id});
        return true;

    }

    public boolean AddFav (String id, int val){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_9, val);
        db.update(TABLE_NAME, contentValues, "SID = ?", new String[] {id});
        return true;

    }





    public Cursor getSingle(String sid){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " +TABLE_NAME+ " where " + COL_2 + "='" + sid + "'  " , null);
        return res;     

    }


    public Cursor getRecentData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_NAME + " where "+COL_13+"='0' AS result   ORDER BY "+ COL_6+" DESC LIMIT 0,20", null);
        return res;

    }





    public List<Product> listCategory(String cat){
        String sql = "select * from " + TABLE_NAME+" where "+COL_13+"='0' and "+COL_11+" ='"+cat+"' ORDER BY "+ COL_2+" DESC LIMIT 0,20";
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();




        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{




                String id =  cursor.getString(0);
                String sid =  cursor.getString(1);
                String message =  html2text(cursor.getString(2));
                String ogt_message = message.replaceAll("\\n", " ");

                if(ogt_message.length() > 325) {

                    gt_message = ogt_message.substring(0, 320)+"  <...>";
                }else{
                    gt_message = ogt_message;

                }


                String value =  cursor.getString(3);

                String type =  cursor.getString(4);
                long timep =  getDateInMillis(cursor.getString(5));

                String  xd = (String) DateUtils.getRelativeTimeSpanString(timep, System.currentTimeMillis(),DateUtils.MINUTE_IN_MILLIS);


                String time= xd;


                String title = cursor.getString(9);
                String category = cursor.getString(10);

                if(title.equals(""))
                {
                    gt_title=gt_message;
                }else{
                    gt_title= title;
                }


                if(value.equals("")) {
                    gt_value  = defult;


                }else{

                    String[] items = value.split(",");
                    gt_value  = str+""+ items[0];

                }



                storeProducts.add(new Product(id,gt_title,gt_message,time,gt_value,sid,type,category ));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }




    public List<Product> listFavorite(){
        String sql = "select * from " + TABLE_NAME+" where "+COL_13+"='0' and "+COL_9+" ='1' ORDER BY "+ COL_2+" DESC LIMIT 0,20";
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();




        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{




                String id =  cursor.getString(0);
                String sid =  cursor.getString(1);
                String message =  html2text(cursor.getString(2));
                String ogt_message = message.replaceAll("\\n", " ");

                if(ogt_message.length() > 325) {

                    gt_message = ogt_message.substring(0, 320)+"  <...>";
                }else{
                    gt_message = ogt_message;

                }


                String value =  cursor.getString(3);

                String type =  cursor.getString(4);
                long timep =  getDateInMillis(cursor.getString(5));

                String  xd = (String) DateUtils.getRelativeTimeSpanString(timep, System.currentTimeMillis(),DateUtils.MINUTE_IN_MILLIS);


                String time= xd;


                String title = cursor.getString(9);
                String category = cursor.getString(10);

                if(title.equals(""))
                {
                    gt_title=gt_message;
                }else{
                    gt_title= title;
                }


                if(value.equals("")) {
                    gt_value  = defult;


                }else{

                    String[] items = value.split(",");
                    gt_value  = str+""+ items[0];

                }



                storeProducts.add(new Product(id,gt_title,gt_message,time,gt_value,sid,type,category ));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }























    public List<Product> listProducts(String unknown,int dataFrom,int dataTo){
        String sql = "select * from " + TABLE_NAME+" where "+COL_13+"='0'  ORDER BY "+ COL_6+" DESC LIMIT "+dataFrom+","+dataTo+"";
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();




        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{




                String id =  cursor.getString(0);
                String sid =  cursor.getString(1);
                String message =  html2text(cursor.getString(2));
                String ogt_message = message.replaceAll("\\n", " ");

                if(ogt_message.length() > 325) {

                    gt_message = ogt_message.substring(0,320)+"  <...>";
                }else{
                    gt_message = ogt_message;

                }


                String value =  cursor.getString(3);

                String type =  cursor.getString(4);
                long timep =  getDateInMillis(cursor.getString(5));

                String  xd = (String) DateUtils.getRelativeTimeSpanString(timep, System.currentTimeMillis(),DateUtils.MINUTE_IN_MILLIS);


                String time= xd;


                String title = cursor.getString(9);
                String category = cursor.getString(10);

                if(title.equals(""))
                {
                    gt_title=gt_message;
                }else{
                    gt_title= title;
                }


                if(value.equals("")) {
                    gt_value  = defult;


                }else{

                    String[] items = value.split(",");
                    gt_value  = str+""+ items[0];

                }

                storeProducts.add(new Product(id,gt_title,gt_message,time,gt_value,sid,type,category ));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }




    public List<Product> listRandom(){
        String sql = "select * from " + TABLE_NAME+" where "+COL_13+"='0'  ORDER BY RANDOM() LIMIT 0,5";
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();




        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{




                String id =  cursor.getString(0);
                String sid =  cursor.getString(1);
                String message =  html2text(cursor.getString(2));
                String ogt_message = message.replaceAll("\\n", " ");

                if(ogt_message.length() > 325) {

                    gt_message = ogt_message.substring(0, 320)+"  <...>";
                }else{
                    gt_message = ogt_message;

                }


                String value =  cursor.getString(3);

                String type =  cursor.getString(4);
                long timep =  getDateInMillis(cursor.getString(5));

                String  xd = (String) DateUtils.getRelativeTimeSpanString(timep, System.currentTimeMillis(),DateUtils.MINUTE_IN_MILLIS);


                String time= xd;


                String title = cursor.getString(9);
                String category = cursor.getString(10);

                if(title.equals(""))
                {
                    gt_title=gt_message;
                }else{
                    gt_title= title;
                }


                if(value.equals("")) {
                    gt_value  = defult;


                }else{

                    String[] items = value.split(",");
                    gt_value  = str+""+ items[0];

                }


                storeProducts.add(new Product(id,gt_title,gt_message,time,gt_value,sid,type,category ));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }











    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_NAME+" where "+COL_13+"='0' GROUP BY "+COL_11, null);
        return res;

    }

    public Cursor getexit(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_NAME+" where "+COL_2+" ='"+id+"' ", null);
        return res;

    }



    public Cursor getAllCat(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_NAME+" where "+COL_13+"='0' GROUP BY "+COL_11, null);
        return res;

    }


    public boolean updateData(String id, String name, String email){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, email);

        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;

    }

    public Integer deleteData (String id){

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID  = ?", new String[] {id});

    }
//Y-m-d H:i:s
    public static long getDateInMillis(String srcDate) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat(
                "yyyy-MM-d hh:mm:ss");

        long dateInMillis = 0;
        try {
            Date date = desiredFormat.parse(srcDate);
            dateInMillis = date.getTime();
            return dateInMillis;
        } catch (ParseException e) {
           // Log.d("Exception while parsing date. " + e.getMessage());
         //   e.printStackTrace();
        }

        return 0;
    }
    public static String html2text(String html) {

       // return html.replaceAll("\\<[^>]*>","");

        return Jsoup.parse(html).text();
    }







}
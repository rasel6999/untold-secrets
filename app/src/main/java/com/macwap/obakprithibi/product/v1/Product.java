package com.macwap.obakprithibi.product.v1;

/**
 * Created by DELL on 3/30/2017.
 */
public class Product {

    private    String title,message,id,time,value,sid,type,category;






    public Product(String title, String message) {
        this.title = title;
        this.message = message;
    }
    public Product(String id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    public Product(String id, String title, String message,String time ,String value,String sid,String type,String category) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.time= time;
        this.value = value;
        this.sid = sid;
        this.type = type;
        this.category = category;
    }
    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String  getMessage() {
        return message;
    }
    public void setMessage(String  message) {
        this.message = message;
    }


    public String  getTime() {
        return time;
    }
    public void setTime(String  time) {
        this.time = time;
    }

    public String  getValue() {
        return value;
    }
    public void setValue(String  value) {
        this.value = value;
    }

    public String  getCategory() {
        return category;
    }
    public void setCategory(String  category) {
        this.category = category;
    }

}

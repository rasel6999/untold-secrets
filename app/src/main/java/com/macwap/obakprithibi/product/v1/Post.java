package com.macwap.obakprithibi.product.v1;

/**
 * Created by DELL on 3/26/2017.
 */

public class Post {

    String excerpt;
    String id,message,time,by,type,value,likes,title,category;




    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    ////////////////////


    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }



    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +

                ", id='" + id + '\'' +
                ", by='" + by + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", time='" + time + '\'' +
                ", likes='" + likes + '\'' +
                ", message='" + message + '\'' +
                ", excerpt='" + excerpt + '\'' +
                '}';
    }
}
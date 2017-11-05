package com.example.karanc.testapp.Db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by karanc on 02-11-2017.
 */
@Table(name = "posts")
public class Posts extends Model {

 @Column(name = "postsid")
 @SerializedName("id")
 public int postsid;

    @Column(name = "title")
    @SerializedName("title")
    public String title;

   @Column(name = "body")
   @SerializedName("body")
    public String body;

    public Posts() {
    }

    public int getPostsid() {
        return postsid;
    }

    public void setPostsid(int postsid) {
        this.postsid = postsid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Posts(int postsid, String title, String body) {
        this.postsid = postsid;
        this.title = title;
        this.body = body;
    }
}

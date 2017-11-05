package com.example.karanc.testapp.Db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by karanc on 02-11-2017.
 */
@Table(name = "todos")
public class Todos extends Model{

    @Column(name = "userid")
    @SerializedName("userId")
    public int userid;

    @Column(name = "title")
    @SerializedName("title")
    public String title;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Column(name = "completed")
    @SerializedName("completed")

    public boolean completed;

    public Todos() {
    }

    public Todos(int userid, String title, boolean completed) {
        this.userid = userid;
        this.title = title;
        this.completed = completed;
    }
}

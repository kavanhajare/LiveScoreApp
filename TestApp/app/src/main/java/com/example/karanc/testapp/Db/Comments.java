package com.example.karanc.testapp.Db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by karanc on 02-11-2017.
 */
@Table(name = "Comments")

public class Comments extends Model {

    @Column(name = "postId")
@SerializedName("postId")
    public int postId;

@Column(name = "name")
@SerializedName("name")
public String name;

@Column(name = "email")
@SerializedName("email")
public String email;

@Column(name = "body")
@SerializedName("body")
public String body;

    public Comments() {
    }

    public Comments(int postId, String name, String email, String body) {
        this.postId = postId;
        this.name = name;
        this.email = email;
        this.body = body;
    }
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}

package com.example.karanc.testapp.Util;

import com.example.karanc.testapp.Db.Comments;
import com.example.karanc.testapp.Db.Photos;
import com.example.karanc.testapp.Db.Posts;
import com.example.karanc.testapp.Db.Todos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by karanc on 04-11-2017.
 */

public interface APInterface {
    @GET("comments")
    Call<List<Comments>> getCommentsJSON();

    @GET("photos")
    Call<List<Photos>> getPhotosJSON();

    @GET("todos")
    Call<List<Todos>> getTodosJSON();

    @GET("posts")
    Call<List<Posts>> getPostsJSON();


}

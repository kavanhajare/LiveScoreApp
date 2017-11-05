package com.example.karanc.testapp;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.example.karanc.testapp.Db.Comments;
import com.example.karanc.testapp.Db.Photos;
import com.example.karanc.testapp.Db.Posts;
import com.example.karanc.testapp.Db.Todos;

/**
 * Created by karanc on 02-11-2017.
 */

public class MyApplication extends com.activeandroid.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Configuration config = new Configuration.Builder(this)
                          .setDatabaseName("Test")
                          .addModelClass(Comments.class)
                          .addModelClass(Photos.class)
                          .addModelClass(Todos.class)
                          .addModelClass(Posts.class)
                           .create();
        ActiveAndroid.initialize(config);
    }
}

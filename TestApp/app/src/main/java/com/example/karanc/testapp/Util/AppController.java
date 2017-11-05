package com.example.karanc.testapp.Util;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by karanc on 03-11-2017.
 */

public class AppController extends Application{
    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static AppController mInst;

    @Override
    public void onCreate() {
        super.onCreate();
        // Init AppController
        mInst = this;
    }

        // getter method
    public static synchronized AppController getInst(){
        return mInst;
    }

    // Init RequestQueue
    public RequestQueue initrequestqueue(){
        if(mRequestQueue==null) mRequestQueue = Volley.newRequestQueue(getApplicationContext());
     return mRequestQueue;
    }

    // Add to requestqueue with the given tag, if any
    public <T> void addtorequestqueue(Request<T> req, String tag){
     req.setTag(TextUtils.isEmpty(tag)? TAG: tag);
        initrequestqueue().add(req);
    }

    // Method overloading, kinda redundant
    public <T> void addtorequestqueue(Request<T> req){
        req.setTag(TAG);
        initrequestqueue().add(req);
    }

    // Cancelling any pending requests with any tag
    public void cancelPendingRequest(Object Tag){
        if(mRequestQueue !=null) mRequestQueue.cancelAll(Tag);
    }

}

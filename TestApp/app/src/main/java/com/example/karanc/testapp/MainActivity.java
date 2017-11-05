package com.example.karanc.testapp;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.karanc.testapp.Db.Comments;
import com.example.karanc.testapp.Db.Photos;
import com.example.karanc.testapp.Db.Posts;
import com.example.karanc.testapp.Db.Todos;
import com.example.karanc.testapp.Util.APInterface;
import com.example.karanc.testapp.Util.APIs;
import com.example.karanc.testapp.Util.AppController;
import com.example.karanc.testapp.Util.Retrofit_APIClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    public String TAG = MainActivity.class.getSimpleName();
    private long timeduration = 5L;
    public ProgressDialog mDialog;
    Comments commentsObj;
    public Photos photosObj;
    public Todos todosObj;
    public Posts postsObj;
    public Handler mhandler;
    public String start, end, startSave, endSave;
    EditText ed0,ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8,ed9,ed10,ed11,ed12,ed13,ed14,ed15;
    APInterface apinterface;
    Button btn1,btn2,btn3,btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Loading..");
        mDialog.setCancelable(false);
        apinterface = Retrofit_APIClient.getClient().create(APInterface.class);
        commentsObj = new Comments();
        photosObj = new Photos();
        todosObj = new Todos();
        postsObj = new Posts();
        mhandler = new Handler();
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                makeJSONArrayReq();
            }
        },timeduration);

      ed0=(EditText)findViewById(R.id.value_start_comment_l1);
        ed1=(EditText)findViewById(R.id.value_end_comment_l1);
        ed2=(EditText)findViewById(R.id.value_start_save_comment_l1);
        ed3=(EditText)findViewById(R.id.value_end_save_comment_l1);

        ed4=(EditText)findViewById(R.id.value_start_comment_l2);
        ed5=(EditText)findViewById(R.id.value_end_comment_l2);
        ed6=(EditText)findViewById(R.id.value_start_save_comment_l2);
        ed7=(EditText)findViewById(R.id.value_end_save_comment_l2);

        ed8=(EditText)findViewById(R.id.value_start_comment_r1);
        ed9=(EditText)findViewById(R.id.value_end_comment_r1);
        ed10=(EditText)findViewById(R.id.value_start_save_comment_r1);
        ed11=(EditText)findViewById(R.id.value_end_save_comment_r1);

        ed12=(EditText)findViewById(R.id.value_start_comment_r2);
        ed13=(EditText)findViewById(R.id.value_end_comment_r2);
        ed14=(EditText)findViewById(R.id.value_start_save_comment_r2);
        ed15=(EditText)findViewById(R.id.value_end_save_comment_r2);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4= (Button) findViewById(R.id.btn4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCommentsJSONReq();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhotosJSONreq();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePostsJSONreq();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTodosJSONreq();
            }
        });
    }

    private void showDialog() {
        if (!mDialog.isShowing()) mDialog.show();
    }

    private void hideDialog() {
        if (mDialog.isShowing()) mDialog.dismiss();
    }

    private void makeJSONArrayReq() {
        Log.d(TAG, "callURL()");
        showDialog();
        makeCommentsJSONReq();
        makePhotosJSONreq();
        makePostsJSONreq();
        makeTodosJSONreq();
    }

    private void makeCommentsJSONReq() {
        Call<List<Comments>> call_comments = apinterface.getCommentsJSON();
        start = setTs();
        ed0.setText(start);


        call_comments.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, retrofit2.Response<List<Comments>> response) {
                end = setTs();
                ed1.setText(end);
                Log.d(TAG, response.toString());

                try {
                    List<Comments> comment_list = response.body();
                    //  JSONObject comm = (JSONObject) response.get(i);
                    startSave = setTs();
                    ed2.setText(startSave);
                    for (int i = 0; i < comment_list.size(); i++) {
                        commentsObj.postId = comment_list.get(i).getPostId();
                        commentsObj.name = comment_list.get(i).getName();
                        commentsObj.email = comment_list.get(i).getEmail();
                        commentsObj.body = comment_list.get(i).getBody();
                    }
                    endSave = setTs();
                    ed3.setText(endSave);

                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
                hideDialog();

            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void makePhotosJSONreq() {
        Call<List<Photos>> call_photos = apinterface.getPhotosJSON();
        start = setTs();
        ed4.setText(start);

        call_photos.enqueue(new Callback<List<Photos>>() {
            @Override
            public void onResponse(Call<List<Photos>> call, retrofit2.Response<List<Photos>> response) {
                end = setTs();
                ed5.setText(end);
                Log.d(TAG, response.toString());

                try {
                    List<Photos> photos_list = response.body();
                    //  JSONObject comm = (JSONObject) response.get(i);
                    startSave = setTs();
                    ed6.setText(startSave);
                    for (int i = 0; i < photos_list.size(); i++) {
                        photosObj.albumId = photos_list.get(i).getAlbumId();
                        photosObj.title = photos_list.get(i).getTitle();
                        photosObj.url = photos_list.get(i).getUrl();
                        photosObj.thumbnailUrl = photos_list.get(i).getThumbnailUrl();
                    }
                    endSave = setTs();
                    ed7.setText(endSave);

                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
                hideDialog();

            }

            @Override
            public void onFailure(Call<List<Photos>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void makePostsJSONreq() {
        Call<List<Posts>> call_posts = apinterface.getPostsJSON();
        start = setTs();
        ed8.setText(start);

        call_posts.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, retrofit2.Response<List<Posts>> response) {
                end = setTs();
                ed9.setText(end);
                Log.d(TAG, response.toString());

                try {
                    List<Posts> posts_list = response.body();
                    //  JSONObject comm = (JSONObject) response.get(i);
                    startSave = setTs();
                    ed10.setText(startSave);
                    for (int i = 0; i < posts_list.size(); i++) {
                        postsObj.postsid = posts_list.get(i).getPostsid();
                        postsObj.title = posts_list.get(i).getTitle();
                        postsObj.body = posts_list.get(i).getBody();
                    }
                    endSave = setTs();
                    ed11.setText(endSave);

                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
                hideDialog();

            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void makeTodosJSONreq(){
        Call<List<Todos>> call_todos = apinterface.getTodosJSON();
        start = setTs();
        ed12.setText(start);

        call_todos.enqueue(new Callback<List<Todos>>() {
            @Override
            public void onResponse(Call<List<Todos>> call, retrofit2.Response<List<Todos>> response) {
                end = setTs();
                ed13.setText(end);
                Log.d(TAG, response.toString());

                try {
                    List<Todos> todos_list = response.body();
                    //  JSONObject comm = (JSONObject) response.get(i);
                    startSave = setTs();
                    ed14.setText(startSave);
                    for(int i=0;i<todos_list.size();i++) {
                        todosObj.userid = todos_list.get(i).getUserid();
                        todosObj.title = todos_list.get(i).getTitle();
                        todosObj.completed = todos_list.get(i).isCompleted();
                    }
                    endSave = setTs();
                    ed15.setText(endSave);

                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
                hideDialog();

            }

            @Override
            public void onFailure(Call<List<Todos>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });

    }

    private String setTs() {
        Long ts = System.currentTimeMillis()/1000;
        String Ts = ts.toString();
        return Ts;
    }

    }



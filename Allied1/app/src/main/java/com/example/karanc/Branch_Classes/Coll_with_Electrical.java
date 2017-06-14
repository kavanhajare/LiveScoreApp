package com.example.karanc.Branch_Classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.karanc.Others.CustomAdapater_BranchWise;
import com.example.karanc.Others.ListModel_CollByBranch;
import com.example.karanc.admission.IndividualCollView;
import com.example.karanc.allied1.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by karanc on 13-06-2016.
 */
public class Coll_with_Electrical extends Activity {
    InputStream is = null;
    String result = null;
    String line = null;
    BufferedReader reader = null;
    StringBuilder sb = null;
    int code = 0;
    JSONObject jsonobject;
    JSONArray jsonarray;
    CustomAdapater_BranchWise adapter;
    ProgressDialog mProgressDialog;
    ArrayList<ListModel_CollByBranch> mData;
    ListView lv;
    ImageLoader imageLoader;
    CircleImageView civ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colleges);

        civ = (CircleImageView) findViewById(R.id.coll_image);
        String imageUri = "http://192.168.96.101/college/images/";
        imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(imageUri, civ);


        imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // Do whatever you want with Bitmap
            }
        });

// Load image, decode it to Bitmap and return Bitmap synchronously
        Bitmap bmp = imageLoader.loadImageSync(imageUri);
        civ.setImageBitmap(bmp);


        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), IndividualCollView.class);
                startActivity(intent);
            }
        });


        new LoadData().execute();

    }

    private class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(getApplicationContext());
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            mData = new ArrayList<ListModel_CollByBranch>();


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.196.101/college/get_college_by_branch.php");
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

                Log.e("pass 1", "connection success " + is);
            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
            }
            try {
                reader = new BufferedReader
                        (new InputStreamReader(is, "iso-8859-1"), 8);
                sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                Log.e("pass 2", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 2", e.toString());
            }
            try {
                jsonobject = new JSONObject(result);
                jsonarray = jsonobject.getJSONArray("collegenamebybranch");

                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    ListModel_CollByBranch mydata = new ListModel_CollByBranch();

                    mydata.setCollegeName(jsonobject.getString("CollegeName"));
                    mydata.setCollegeSeats(jsonobject.getString("CollegeFees"));
                    mydata.setColl_Image(jsonobject.getString("Image"));
                    mData.add(mydata);

                }
            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            adapter = new CustomAdapater_BranchWise(getApplicationContext(), mData);
            lv.setAdapter((ListAdapter) adapter);
            mProgressDialog.dismiss();
        }

    }

}

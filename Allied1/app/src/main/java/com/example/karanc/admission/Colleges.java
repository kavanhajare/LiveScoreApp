package com.example.karanc.admission;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.karanc.Others.ListModel;
import com.example.karanc.allied1.MainActivity;
import com.example.karanc.allied1.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.karanc.Others.CustomAdapter;
import com.example.karanc.allied1.SUserprofile;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.karanc.allied1.R.layout.*;

/**
 * Created by karanc on 26-05-2016.
 */
public class Colleges extends Fragment {

    InputStream is=null;
    String result=null;
    String line=null;
    BufferedReader reader=null;
    StringBuilder sb=null;
    int code=0;
    JSONObject jsonobject;
    JSONArray jsonarray;
    CustomAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<ListModel> mData;
    ListView lv;
    ImageLoader imageLoader ;
    CircleImageView civ;
    String cid;
    public static String collegesnames;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
 View v = inflater.inflate(R.layout.activity_colleges,container,false);

        civ= (CircleImageView) v.findViewById(R.id.coll_image);
        String imageUri="http://karan.16mb.com/images/gecbharuch.jpg";
/*
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
*/


        lv = (ListView) v.findViewById(R.id.listView);


        new LoadData().execute();

        return v;

    }

    private class LoadData extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params)
        {

            mData = new ArrayList<ListModel>();


            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://karan.16mb.com/list.php");
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

                Log.e("pass 1", "connection success "+is);
            }
            catch(Exception e)
            {
                Log.e("Fail 1", e.toString());
            }
            try
            {
                reader = new BufferedReader
                        (new InputStreamReader(is,"iso-8859-1"),8);
                sb = new StringBuilder();
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                Log.e("pass 2", "connection success ");
            }
            catch(Exception e)
            {
                Log.e("Fail 2", e.toString());
            }
            try
            {
                jsonobject = new JSONObject(result);
                jsonarray = jsonobject.getJSONArray("collegename");

                for (int i = 0; i < jsonarray.length(); i++)
                {
                    jsonobject = jsonarray.getJSONObject(i);
                  cid = jsonobject.getString("ImageId");
                    collegesnames=jsonobject.getString("CollegeName");
                    ListModel mydata=new ListModel();

                    mydata.setCollegeName(jsonobject.getString("CollegeName"));
                    mydata.setFees(jsonobject.getString("CollegeFees"));
                    mydata.setColl_Image(jsonobject.getString("Image"));
                    mydata.setImageId(jsonobject.getString("ImageId"));

                    mData.add(mydata);

                }
            }
            catch(Exception e)
            {
                Log.e("Fail 3", e.toString());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            adapter = new CustomAdapter(getActivity(),mData);

            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(getActivity(),IndividualCollView.class);
                    intent.putExtra("CollegeId",cid);
                    startActivity(intent);
                }
            });

            mProgressDialog.dismiss();
        }

    }
}

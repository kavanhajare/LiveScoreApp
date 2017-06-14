package com.example.karanc.Others;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karanc.allied1.R;

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

public class Reviews extends Fragment {
    InputStream is=null;
    String result=null;
    String line=null;
    BufferedReader reader=null;
    StringBuilder sb=null;
    int code=0;
    JSONObject jsonobject;
    JSONArray jsonarray;
    CustomAdapter_Reviews adapter;
    ProgressDialog mProgressDialog;
    ArrayList<ListModel_Reviews> mData;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_reviews,container,false);
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

            mData = new ArrayList<ListModel_Reviews>();


            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://karan.16mb.com/get_review_data.php");
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
                jsonarray = jsonobject.getJSONArray("reviewcollection");

                for (int i = 0; i < jsonarray.length(); i++)
                {
                    jsonobject = jsonarray.getJSONObject(i);

                    ListModel_Reviews mydata=new ListModel_Reviews();

                    mydata.setCollegeName(jsonobject.getString("college"));
                    mydata.setDesc(jsonobject.getString("descr"));
                    mydata.setStars(jsonobject.getString("stars"));
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
            adapter = new CustomAdapter_Reviews(getActivity(),mData);
            lv.setAdapter(adapter);
            mProgressDialog.dismiss();
        }

    }
}


package com.example.karanc.Others;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karanc.admission.Colleges;
import com.example.karanc.allied1.R;
import com.example.karanc.allied1.SUserprofile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReviewsSnA extends Fragment {
    JSONParser jsonParser;
    String CollegeName;
    String url_create_product;
    RatingBar mRatingBar;
    ProgressDialog mProgressDialog;
    String description;
    String stars;
    private static final String TAG_SUCCESS = "success";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_reviews_sn, container, false);

        EditText CollegeNameed = (EditText) v.findViewById(R.id.collegetv);
        CollegeName = CollegeNameed.getText().toString();
        mRatingBar= (RatingBar) v.findViewById(R.id.ratingBar);
        EditText Review_desc= (EditText) v.findViewById(R.id.editTextdesc);

        url_create_product="http://karan.16mb.com/insert_into_reviewtb.php";
        stars = String.valueOf(mRatingBar.getNumStars());
        description = Review_desc.getText().toString();

        getFragmentManager().findFragmentById(R.id.nav_colleges).getActivity();
        JSONParser jsonParser= new JSONParser();

        FloatingActionButton fab= (FloatingActionButton) v.findViewById(R.id.donebtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CollegeName==null){
                    Toast.makeText(getActivity(),"Please Enter Proper CollegeName",Toast.LENGTH_SHORT).show();
                }
                else if(mRatingBar.getNumStars()==0){
                    Toast.makeText(getActivity(),"Please Rate!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(),"Done!",Toast.LENGTH_SHORT).show();
                    new LoadData().execute();

                }
            }
        });


        return v;
    }

    private class LoadData extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }


        @Override
        protected Void doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("descr", description));
            params.add(new BasicNameValuePair("college", CollegeName));
            params.add(new BasicNameValuePair("stars", stars));


            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            Log.d("Create Response", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Toast.makeText(getActivity(),"Review Added Succesfully",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(), SUserprofile.class);
                    startActivity(intent);

                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            mProgressDialog.dismiss();
        }

    }
}
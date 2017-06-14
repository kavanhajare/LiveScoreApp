package com.example.karanc.admission;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karanc.Others.*;
import com.example.karanc.Others.CustomAdapter;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class IndividualCollView extends AppCompatActivity {
    InputStream is = null;
    String result = null;
    String line = null;
    BufferedReader reader = null;
    StringBuilder sb = null;
    int code = 0;
    JSONObject jsonobject;
    JSONArray jsonarray;
    com.example.karanc.Others.CustomAdapter_IndividualView adapter;
    ProgressDialog mProgressDialog;
    ArrayList<ListModel_IndividualView> mData;
    TableLayout tblayout;
    String value;
    String cid;
    TextView CollegeName;
    TextView CollegeAddress;
    public TextView CollegeFees;
    public TextView CollegePhNo;
    public TextView CollegeMailAddress;
    public TextView  CollegeWebsite;
    public TextView CollegeType;
    public TextView CollegeBranch;
    public TextView CollegeBranchSeats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_coll_view);

         CollegeName=(TextView) findViewById(R.id.Individ_Coll_Name);
     cid=getIntent().getExtras().getString("CollegeId");

        ImageView Location_ImageView = (ImageView) findViewById(R.id.coll_iv_location);
        ImageView Call_ImageView = (ImageView) findViewById(R.id.coll_iv_call);
        ImageView Mail_ImageView = (ImageView) findViewById(R.id.coll_iv_mail);
        ImageView Web_ImageView = (ImageView) findViewById(R.id.coll_iv_web);

        final TextView Location_Textview = (TextView) findViewById(R.id.coll_tv_location);
        TextView Call_Textview = (TextView) findViewById(R.id.coll_tv_call);
        TextView Mail_Textview = (TextView) findViewById(R.id.coll_tv_mail);
        final TextView Web_Textview = (TextView) findViewById(R.id.coll_tv_web);
        TextView Fees_Textview = (TextView) findViewById(R.id.coll_tv_fee);

        Web_Textview.setMovementMethod(LinkMovementMethod.getInstance());

        if (Call_Textview != null) {
            Call_Textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    startActivity(intent);
                }
            });
        }


        if (Mail_Textview != null) {
            Mail_Textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    startActivity(Intent.createChooser(intent, "Send Email"));
                }
            });
        }


        Web_Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = Web_Textview.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(URL));
                startActivity(intent);
            }
        });



        new LoadData().execute();

    }

    class LoadData extends AsyncTask<Void, Void, Void> {
int flag=1;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(IndividualCollView.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            mData = new ArrayList<ListModel_IndividualView>();


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://karan.16mb.com/get_college_by_id.php?id="+cid);
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
                JSONObject jsonarray = jsonobject.getJSONObject("collegedata");

                value=jsonarray.getString("CollegeName");
                /*int flag=1;

                    for (int i = 0; i < jsonarray.length(); i++) {
                        //jsonobject = jsonarray.getJSONObject(i);

                        ListModel_IndividualView mydata = new ListModel_IndividualView();

                        mydata.setCollegeName(jsonobject.getString("CollegeName"));
                        mydata.setCollegeAddress(jsonobject.getString("CollegeAddress"));
                        mydata.setCollegeFees(jsonobject.getString("CollegeFees"));
                        mydata.setCollegePhNo(jsonobject.getString("CollegePhNo"));
                        mydata.setCollegeMailAddress(jsonobject.getString("CollegeMailAddress"));
                        mydata.setCollegeWebsite(jsonobject.getString("CollegeWebsite"));
                        mydata.setCollegeType(jsonobject.getString("CollegeType"));
                        mydata.setCollegeBranch(jsonobject.getString("CollegeBranch"));
                        mydata.setCollegeBranchSeats(jsonobject.getString("CollegeBranchSeats"));

                        mData.add(mydata);
                    }*/


            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub

            CollegeName.setText(value);

            mProgressDialog.dismiss();
        }

    }
}

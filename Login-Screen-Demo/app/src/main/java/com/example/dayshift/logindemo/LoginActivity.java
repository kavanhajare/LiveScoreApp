package com.example.dayshift.logindemo;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText musername,mpass;
    Button msigninbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        musername = (EditText) findViewById(R.id.username);
        mpass = (EditText) findViewById(R.id.password);
        msigninbtn = (Button) findViewById(R.id.sign_in_btn);
        Typeface tf = Typeface.createFromAsset(getAssets(),"Merriweather-Regular.ttf");
        musername.setTypeface(tf);
        mpass.setTypeface(tf);
        msigninbtn.setTypeface(tf);
       }
}

package com.cricketbuzz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cricketbuzz.R;
import com.gmail.jorgegilcavazos.ballislife.features.intro.IntroActivity;

/**
 * Created by karanc on 16-11-2016.
 */

public class Home extends AppCompatActivity {
    FloatingActionButton cricket,football,basketball;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cricket = (FloatingActionButton) findViewById(R.id.cricket);
        football = (FloatingActionButton) findViewById(R.id.football);
        basketball = (FloatingActionButton) findViewById(R.id.basketball);
        cricket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(Home.this,MainActivity.class);
                startActivity(intent);
            }
        });
        basketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, IntroActivity.class);
                startActivity(intent);
            }
        });
         }
}

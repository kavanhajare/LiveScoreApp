package com.example.karanc.allied1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by karanc on 25-05-2016.
 */
public class home_screen extends Activity {
    ImageView ibtnnew,ibtnstud,ibtnalum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    ibtnnew= (ImageView) findViewById(R.id.newuser);
        ibtnstud= (ImageView) findViewById(R.id.Student);
        ibtnalum= (ImageView) findViewById(R.id.Alumni);

        ibtnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_screen.this,MainActivity.class);
                startActivity(intent);
            }
        });

        ibtnstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(home_screen.this,MainSActivity.class);
                startActivity(intent);
            }
        });

        ibtnalum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(home_screen.this,MainAlumActivity.class);
                startActivity(intent);
            }

        });
    }
}
package com.example.karanc.admission;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.karanc.Branch_Classes.Coll_with_Mech;
import com.example.karanc.allied1.R;

public class Mechanical extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanical);

        Button coll_mechbtn = (Button) findViewById(R.id.Coll_mech);
       coll_mechbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve list of colleges with Mechanical Stream
                Intent intent = new Intent(Mechanical.this,Coll_with_Mech.class);
                startActivity(intent);

            }
        });
    }
    }


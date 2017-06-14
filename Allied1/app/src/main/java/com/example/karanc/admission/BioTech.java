package com.example.karanc.admission;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.karanc.Branch_Classes.Coll_with_BioTech;
import com.example.karanc.Branch_Classes.Coll_with_Civil;
import com.example.karanc.allied1.R;

public class BioTech extends AppCompatActivity {
    Button collbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_tech);
        collbtn= (Button) findViewById(R.id.Coll_civil);
        collbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Coll_with_BioTech.class);
                startActivity(intent);
            }
        });

    }
}

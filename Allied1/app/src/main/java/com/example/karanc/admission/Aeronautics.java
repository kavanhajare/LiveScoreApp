package com.example.karanc.admission;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.karanc.Branch_Classes.Coll_with_Aeronautics;
import com.example.karanc.allied1.R;

public class Aeronautics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeronautics);
         Button CollBtn = (Button) findViewById(R.id.Coll_aeronautics);
        CollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Aeronautics.this,Coll_with_Aeronautics.class);
                startActivity(intent);

            }
        });
    }
}

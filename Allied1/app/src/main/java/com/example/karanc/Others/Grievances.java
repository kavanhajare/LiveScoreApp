package com.example.karanc.Others;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karanc.allied1.R;

public class Grievances extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_grievances,container,false);
        EditText emailtext = (EditText) v.findViewById(R.id.editTextemail);
        final String email=emailtext.getText().toString();
     final EditText desctext= (EditText) v.findViewById(R.id.editTextdesc);
        final String desc=desctext.getText().toString();
        FloatingActionButton sendbtn= (FloatingActionButton) v.findViewById(R.id.sendbtn);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(getContext(), "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"alliedinfosoft1@gmail.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT, "");
                    email.putExtra(Intent.EXTRA_TEXT,desc);
                    email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email, "Choose an Email client :"));                 }
            }
        });
        return v;
    }
}

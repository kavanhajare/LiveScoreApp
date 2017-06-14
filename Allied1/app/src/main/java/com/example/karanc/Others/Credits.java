package com.example.karanc.Others;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karanc.allied1.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Credits extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_credits,container,false);
        CircleImageView devfb,devg,g1fb,g1g,g2fb,g2g,g3fb,g3g;

        devfb= (CircleImageView) v.findViewById(R.id.dev_fb);
        devg= (CircleImageView) v.findViewById(R.id.dev_gplus);
        g1fb= (CircleImageView) v.findViewById(R.id.g1_fb);
        g1g= (CircleImageView) v.findViewById(R.id.g1_gplus);
        g2fb= (CircleImageView) v.findViewById(R.id.g2_fb);
        g2g= (CircleImageView) v.findViewById(R.id.g2_gplus);
        g3fb= (CircleImageView) v.findViewById(R.id.g3_fb);
        g3g= (CircleImageView) v.findViewById(R.id.g3_gplus);

        devfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/karan.chapaneri"));
                startActivity(browserIntent);
            }
        });

        devg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/116505976848746961899"));
                startActivity(browserIntent);

            }
        });

        g1fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/sunny.bongiri.3"));
                startActivity(browserIntent);

            }
        });
        g1g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/sunnybongiri"));
                startActivity(browserIntent);

            }
        });

        g2fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/chirag1092"));
                startActivity(browserIntent);

            }
        });

        g2g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                startActivity(browserIntent);



            }
        });

        g3fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
                startActivity(browserIntent);

            }
        });

        g3g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                startActivity(browserIntent);



            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

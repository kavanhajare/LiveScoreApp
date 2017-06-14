package com.example.karanc.Others;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.karanc.allied1.R;
import java.util.ArrayList;

public class News extends Fragment {
    GridView gv;
    Context context;
    ArrayList prgmName;
    public static String [] DateList={"MAY 19","MAY 20","JUN 02","JUN 08","JUN 08","JUN 08","JUN 22","JUN 29"};
    public int imgList[]={R.mipmap.calendar,R.mipmap.calendar,
            R.mipmap.calendar,R.mipmap.calendar,
            R.mipmap.calendar,R.mipmap.calendar,
            R.mipmap.calendar,R.mipmap.calendar};
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_news,container,false);
        gv=(GridView) v.findViewById(R.id.gridView1);
        final RelativeLayout base= (RelativeLayout) v.findViewById(R.id.base_news);
        gv.setAdapter(new Adapter_News(getContext(), DateList,imgList));
        final FragmentManager fm = getFragmentManager();
              //  base.setVisibility(View.VISIBLE);
       gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               base.setVisibility(View.INVISIBLE);

               Intent myIntent = new Intent(getActivity(), News_Webview.class);
               getActivity().startActivity(myIntent);

           }
       }); return v;
    }

}

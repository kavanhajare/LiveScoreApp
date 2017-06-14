package com.example.karanc.admission;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karanc.Others.CAT;
import com.example.karanc.Others.CustomAdapter_Exams;
import com.example.karanc.Others.DataModel_Exam;
import com.example.karanc.Others.GATE;
import com.example.karanc.Others.GRE;
import com.example.karanc.Others.IELTS;
import com.example.karanc.Others.MyData_Exam;
import com.example.karanc.Others.PSU;
import com.example.karanc.Others.TOEFL;
import com.example.karanc.Others.UPSC;
import com.example.karanc.allied1.R;

import java.util.ArrayList;

public class Exams extends Fragment {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel_Exam> data;
    public static View.OnClickListener myOnClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_exams,container,false);
        myOnClickListener = new MyOnClickListener(getActivity());
        recyclerView = (RecyclerView) v.findViewById(R.id.myrecyclerview);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        data = new ArrayList<DataModel_Exam>();
        for (int i = 0; i < MyData_Exam.examname.length; i++) {
            data.add(new DataModel_Exam(
                    MyData_Exam.examname[i]
            ));
        }

        adapter = new CustomAdapter_Exams(data);
        recyclerView.setAdapter(adapter);


        return v;
    }
        private class MyOnClickListener implements View.OnClickListener {
            private final Context context;

            private MyOnClickListener(Context context) {
                this.context = context;
            }

            @Override
            public void onClick(View v) {
            int itemPos=recyclerView.getChildLayoutPosition(v);

                if(itemPos==0){

                    Intent intent= new Intent(getContext(),GRE.class);
                    startActivity(intent);
                }
                if(itemPos==1){
                    Intent intent= new Intent(getContext(),GATE.class);
                    startActivity(intent);
                }
                if(itemPos==2){
                    Intent intent= new Intent(getContext(),CAT.class);
                    startActivity(intent);
                }
                if(itemPos==3){
                    Intent intent= new Intent(getContext(),TOEFL.class);
                    startActivity(intent);
                }
                if(itemPos==4) {
                    Intent intent = new Intent(getContext(), IELTS.class);
                    startActivity(intent);
                }
                if(itemPos==5){
                    Intent intent= new Intent(getContext(),UPSC.class);
                    startActivity(intent);
                }
                if(itemPos==6){
                    Intent intent= new Intent(getContext(),PSU.class);
                    startActivity(intent);
                }

            }
        }

  }


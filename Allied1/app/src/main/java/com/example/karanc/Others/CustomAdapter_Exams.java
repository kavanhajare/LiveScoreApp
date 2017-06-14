package com.example.karanc.Others;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karanc.admission.Exams;
import com.example.karanc.allied1.R;

import java.util.ArrayList;

/**
 * Created by karanc on 14-06-2016.
 */
public class CustomAdapter_Exams extends RecyclerView.Adapter<CustomAdapter_Exams.MyViewHolder>{
    private ArrayList<DataModel_Exam> dataSet;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewExamName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewExamName= (TextView) itemView.findViewById(R.id.examtv);
        }
    }
    public CustomAdapter_Exams(ArrayList<DataModel_Exam> data) {
        this.dataSet = data;
    }

    @Override
    public CustomAdapter_Exams.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardlayout, parent, false);

        view.setOnClickListener(Exams.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter_Exams.MyViewHolder holder, int position) {
        TextView textViewName = holder.textViewExamName;
        textViewName.setText(dataSet.get(position).getExamName());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

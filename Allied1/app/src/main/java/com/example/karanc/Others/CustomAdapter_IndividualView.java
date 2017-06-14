package com.example.karanc.Others;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.karanc.allied1.R;

import java.util.ArrayList;
import com.example.karanc.Others.ListModel_IndividualView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by karanc on 11-06-2016.
 */
public class CustomAdapter_IndividualView extends BaseAdapter{
    Context context;
    ArrayList<ListModel_IndividualView> data;
    Resources res;
    public CustomAdapter_IndividualView(Context context,ArrayList<ListModel_IndividualView> arraylist) {
        this.context = context;
        data = arraylist;
    }
    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public ListModel_IndividualView getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder {
        public TextView CollegeName;
        public TextView CollegeAddress;
        public TextView CollegeFees;
        public TextView CollegePhNo;
        public TextView CollegeMailAddress;
        public TextView  CollegeWebsite;
        public TextView CollegeType;
        public TextView CollegeBranch;
        public TextView CollegeBranchSeats;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.activity_individual_coll_view, parent, false);
            holder = new ViewHolder();
            holder.CollegeName = (TextView) v.findViewById(R.id.Individ_Coll_Name);
            holder.CollegeFees = (TextView) v.findViewById(R.id.coll_tv_fee);
            holder.CollegeAddress = (TextView) v.findViewById(R.id.coll_tv_location);
            holder.CollegePhNo = (TextView) v.findViewById(R.id.coll_tv_call);
            holder.CollegeMailAddress = (TextView) v.findViewById(R.id.coll_tv_mail);
            holder.CollegeWebsite = (TextView) v.findViewById(R.id.coll_tv_web);
            holder.CollegeType = (TextView) v.findViewById(R.id.coll_tv_type);
            holder.CollegeAddress = (TextView) v.findViewById(R.id.coll_tv_fee);
            holder.CollegeBranch= (TextView) v.findViewById(R.id.collbranchtv);
            holder.CollegeBranchSeats= (TextView) v.findViewById(R.id.collseatstv);
            v.setTag( holder );


        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        ListModel_IndividualView item=getItem(position);

        String CollegeName=item.getCollegeName();
        if(CollegeName!=null) {
            holder.CollegeName.setText(CollegeName);
        }

        String CollegeFees=item.getCollegeFees();
        if(CollegeFees!=null) {
            holder.CollegeFees.setText(CollegeFees);
        }
        String CollegeAddress=item.getCollegeAddress();
        if(CollegeAddress!=null) {
            holder.CollegeAddress.setText(CollegeAddress);
        }
        String CollegePhNo=item.getCollegePhNo();
        if(CollegePhNo!=null) {
            holder.CollegePhNo.setText(CollegePhNo);
        }
        String CollegeMailAddress=item.getCollegeMailAddress();
        if(CollegeMailAddress!=null) {
            holder.CollegeFees.setText(CollegeFees);
        }
        String CollegeWebsite=item.getCollegeWebsite();
        if(CollegeWebsite!=null) {
            holder.CollegeWebsite.setText(CollegeWebsite);
        }
        String CollegeType=item.getCollegeType();
        if(CollegeType!=null) {
            holder.CollegeType.setText(CollegeType);
        }
        String CollegeBranch=item.getCollegeBranch();
        if(CollegeBranch!=null) {
            holder.CollegeBranch.setText(CollegeBranch);
        }
        String CollegeBranchSeats=item.getCollegeBranchSeats();
        if(CollegeBranchSeats!=null) {
            holder.CollegeBranchSeats.setText(CollegeBranchSeats);
        }

        return v;
    }

}

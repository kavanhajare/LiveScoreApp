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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by karanc on 13-06-2016.
 */
public class CustomAdapater_BranchWise extends BaseAdapter {
    Context context;
    ArrayList<ListModel_CollByBranch> data;
    Resources res;
    public CustomAdapater_BranchWise(Context context, ArrayList<ListModel_CollByBranch> arraylist) {
        this.context = context;
        data = arraylist;
    }

    public int getCount() {

        return data.size();
    }

    public ListModel_CollByBranch getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder {
        public TextView coll_name_text;
        public TextView coll_branch_seats;
        public CircleImageView coll_image;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.customlayoutbranch, parent, false);
            holder = new ViewHolder();
            holder.coll_name_text = (TextView) v.findViewById(R.id.coll_name);
            holder.coll_branch_seats = (TextView) v.findViewById(R.id.coll_seats);
            holder.coll_image= (CircleImageView) v.findViewById(R.id.coll_image);
            v.setTag( holder );


        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        ListModel_CollByBranch item=getItem(position);

        String CollegeName=item.getCollegeName();
        if(CollegeName!=null) {
            holder.coll_name_text.setText(CollegeName);
        }

        String CollegeBranchSeats=item.getCollegeSeats();
        if(CollegeBranchSeats!=null) {
            holder.coll_branch_seats.setText(CollegeBranchSeats);
        }
        String Coll_Image=item.getColl_Image();
       /* if(Coll_Image!=null){
            holder.coll_image.setImageResource(res.getIdentifier("localhost/college/images/"+item.getColl_Image(),null,null));
        }
*/
        return v;
    }

}

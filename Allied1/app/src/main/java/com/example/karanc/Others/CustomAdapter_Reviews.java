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
 * Created by karanc on 20-06-2016.
 */
public class CustomAdapter_Reviews extends BaseAdapter{
    Context context;
    ArrayList<ListModel_Reviews> data;
    Resources res;
    public CustomAdapter_Reviews(Context context, ArrayList<ListModel_Reviews> arraylist) {
        this.context = context;
        data = arraylist;
    }

    public int getCount() {

        return data.size();
    }

    public ListModel_Reviews getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder {
        public TextView coll_name_text;
        public TextView coll_desc;
        public TextView coll_stars;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.customlayout_reviews, parent, false);
            holder = new ViewHolder();
            holder.coll_name_text = (TextView) v.findViewById(R.id.Coll_name);
            holder.coll_desc = (TextView) v.findViewById(R.id.coll_desc);
            holder.coll_stars= (TextView) v.findViewById(R.id.coll_rating);
            v.setTag( holder );


        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        ListModel_Reviews item=getItem(position);

        String CollegeName=item.getCollegeName();

        if(CollegeName!=null) {
            holder.coll_name_text.setText(CollegeName);
        }

        String CollegeDesc=item.getDesc();
        if(CollegeDesc!=null) {
            holder.coll_desc.setText(CollegeDesc);
        }
        String Coll_Stars=item.getStars();
        if(Coll_Stars!=null) {
            holder.coll_stars.setText(Coll_Stars);
        }
       /* if(Coll_Image!=null){
            holder.coll_image.setImageResource(res.getIdentifier("localhost/college/images/"+item.getColl_Image(),null,null));
        }
*/
        return v;
    }

}

package com.example.karanc.Others;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karanc.admission.Colleges;
import com.example.karanc.allied1.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by karanc on 04-06-2016.
 */
public class CustomAdapter extends BaseAdapter  {
    Context context;
    ArrayList<ListModel> data;
    Resources res;

    public CustomAdapter(Context context, ArrayList<ListModel> arraylist) {
        this.context = context;
        data = arraylist;
     }
    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public ListModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder {
        public TextView coll_name_text;
        public TextView coll_fees;
        public CircleImageView coll_image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.customlayout, parent, false);
            holder = new ViewHolder();
            holder.coll_name_text = (TextView) v.findViewById(R.id.coll_name);
            holder.coll_fees = (TextView) v.findViewById(R.id.coll_fees);
            holder.coll_image= (CircleImageView) v.findViewById(R.id.coll_image);
            v.setTag( holder );


        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        ListModel item=getItem(position);

        String CollegeName=item.getCollegeName();
        if(CollegeName!=null) {
            holder.coll_name_text.setText(CollegeName);
        }

        String Fees=item.getFees();
        if(Fees!=null) {
            holder.coll_fees.setText(Fees);
        }
        String Coll_Image=item.getColl_Image();
       /* if(Coll_Image!=null){
            holder.coll_image.setImageResource(res.getIdentifier("localhost/college/images/"+item.getColl_Image(),null,null));
        }
*/
        return v;
    }

}
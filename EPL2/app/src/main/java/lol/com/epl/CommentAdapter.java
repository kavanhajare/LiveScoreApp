package lol.com.epl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * Created by arjun on 29/9/15.
 */
public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private Comment[] mComments;

    public CommentAdapter(Context context, Comment[] comment) {
        mContext = context;
        mComments= comment;
    }



    @Override
    public int getCount() {
        return mComments.length;
    }

    @Override
    public Object getItem(int position) {
        return mComments[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_com, null);
            holder = new ViewHolder();
            holder.local= (TextView) convertView.findViewById(R.id.local);
            holder.visitor= (TextView) convertView.findViewById(R.id.visitor);
            holder.loc_score= (TextView) convertView.findViewById(R.id.loc_score);
            holder.vis_score= (TextView) convertView.findViewById(R.id.vis_score);
            holder.date=(TextView) convertView.findViewById(R.id.date);
            holder.half=(TextView) convertView.findViewById(R.id.half);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Comment com = mComments[position];


        holder.local.setText(com.getLocal());
        holder.visitor.setText(com.getVisitor());
        holder.loc_score.setText(com.getLoc_score());
        holder.vis_score.setText(com.getVis_score());
        holder.date.setText(com.getDate()+"");
        holder.half.setText(com.getHalf());

        return convertView;
    }



        private static class ViewHolder{
        TextView local;
        TextView visitor;
        TextView loc_score;
        TextView vis_score;
        TextView date;
        TextView half;


    }


}

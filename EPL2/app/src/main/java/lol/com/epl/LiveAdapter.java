package lol.com.epl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by arjun on 26/9/15.
 */
public class LiveAdapter extends BaseAdapter {
    private Context mContext;
    private Live[] mLives;
    ListView list;
    private LiveAdapter liveAdapter;
    public LiveAdapter(Context context, Live[] lives) {
        mContext = context;
        mLives = lives;
    }


    @Override
    public int getCount() {
        return mLives.length;
    }

    @Override
    public Object getItem(int position) {
        return mLives[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_live, null);
            holder = new ViewHolder();
            holder.local= (TextView) convertView.findViewById(R.id.local);
            holder.visitor= (TextView) convertView.findViewById(R.id.visitor);
            holder.loc_score= (TextView) convertView.findViewById(R.id.loc_score);
            holder.vis_score= (TextView) convertView.findViewById(R.id.vis_score);
            holder.timer=(TextView) convertView.findViewById(R.id.timer);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Live liv = mLives[position];

        String s;
        s=liv.getLoc_score();
        holder.local.setText(liv.getLocal());
        holder.visitor.setText(liv.getVisitor());
        if(s=="-1")
        {

                    s="??";
        }
        holder.loc_score.setText(s);
        holder.vis_score.setText(liv.getVis_score());
        holder.timer.setText(liv.getTimer());




        return convertView;
    }

    private static class ViewHolder{
        TextView local;
        TextView visitor;
        TextView loc_score;
        TextView vis_score;
        TextView timer;


    }


}

package lol.com.epl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by arjun on 9/11/15.
 */
public class FixTeamAdapter extends BaseAdapter {
    private Context mContext;
    private FixTeam[] mFixTeams;


    public FixTeamAdapter(Context context,FixTeam[] stands){
        mContext=context;
        mFixTeams=stands;
    }

    @Override
    public int getCount() {
        return mFixTeams.length;
    }

    @Override
    public Object getItem(int position) {
        return mFixTeams[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_fix_team, null);
            holder = new ViewHolder();
            holder.local= (TextView) convertView.findViewById(R.id.local);
            holder.visitor= (TextView) convertView.findViewById(R.id.visitor);
            holder.loc_score= (TextView) convertView.findViewById(R.id.loc_score);
            holder.vis_score= (TextView) convertView.findViewById(R.id.vis_score);
            holder.date=(TextView) convertView.findViewById(R.id.date);
            holder.remind=(Button)convertView.findViewById(R.id.remind);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

      FixTeam fit =  mFixTeams[position];


        holder.local.setText(fit.getLocal());
        holder.visitor.setText(fit.getVisitor());
        holder.loc_score.setText(fit.getLoc_score());
        holder.vis_score.setText(fit.getVis_score());
        holder.date.setText(fit.getDate());





        return convertView;
    }

    private static class ViewHolder{
        TextView local;
        TextView visitor;
        TextView loc_score;
        TextView vis_score;
        TextView date;
        Button remind;


    }

}

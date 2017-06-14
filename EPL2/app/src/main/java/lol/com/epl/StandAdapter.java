package lol.com.epl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by arjun on 25/9/15.
 */
public class StandAdapter extends BaseAdapter {
    private Context mContext;
    private Stand[] mStands;
    ListView list;
    private StandAdapter standAdapter;
    public StandAdapter(Context context,Stand[] stands){
        mContext=context;
        mStands=stands;
    }
    @Override
    public int getCount() {
        return mStands.length;
    }

    @Override
    public Object getItem(int position) {
        return mStands[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_stand, null);
            holder = new ViewHolder();
            holder.team= (TextView) convertView.findViewById(R.id.player);
            holder.pos= (TextView) convertView.findViewById(R.id.pos);
            holder.win= (TextView) convertView.findViewById(R.id.win);
            holder.lose= (TextView) convertView.findViewById(R.id.lose);

            holder.icon=(ImageView) convertView.findViewById(R.id.icon);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Stand std = mStands[position];


        holder.team.setText(std.getTeam());
        holder.win.setText(std.getWin());
        holder.lose.setText(std.getLose());
        holder.pos.setText(std.getPos());

        holder.icon.setImageResource(std.getIconId(std.getIcon().toString()));




        return convertView;
    }

    private static class ViewHolder{
        TextView team;
        TextView pos;
        TextView win;
        TextView lose;

        ImageView icon;


    }

}

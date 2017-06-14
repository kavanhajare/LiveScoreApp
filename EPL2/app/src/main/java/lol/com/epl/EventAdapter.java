package lol.com.epl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by arjun on 29/9/15.
 */
public class EventAdapter extends BaseAdapter {
    private Context mContext;
    private Event[] mEvents;
    public EventAdapter(Context context,Event[] events){
        mContext=context;
        mEvents=events;
    }


    @Override
    public int getCount() {
        return mEvents.length;
    }

    @Override
    public Object getItem(int position) {
        return mEvents[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_event, null);
            holder = new ViewHolder();
            holder.team= (TextView) convertView.findViewById(R.id.team);
            holder.min= (TextView) convertView.findViewById(R.id.min);
            holder.result= (TextView) convertView.findViewById(R.id.result);
            holder.player= (TextView) convertView.findViewById(R.id.player);
            holder.type=(ImageView) convertView.findViewById(R.id.type);



            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

       Event eve = mEvents[position];


        holder.team.setText(eve.getTeam());
        holder.type.setImageResource(eve.getTypeId(eve.getType().toString()));
        holder.min.setText(eve.getMin()+"'");
        holder.result.setText(eve.getResult());
        holder.player.setText(eve.getPlayer());




        return convertView;
    }



    private static class ViewHolder{
        TextView team;
        ImageView type;
        TextView min;
        TextView result;
        TextView player;


    }






}

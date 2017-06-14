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
 * Created by arjun on 5/10/15.
 */
public class PlayerAdapter extends BaseAdapter {
    private Context mContext;
    private Player[] mPlayers;
    ListView list;
    public PlayerAdapter(Context context,Player[] players){
        mContext=context;
        mPlayers=players;
    }

    @Override
    public int getCount() {
        return mPlayers.length;
    }

    @Override
    public Object getItem(int position) {
        return mPlayers[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_players, null);
            holder = new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.pos= (TextView) convertView.findViewById(R.id.position);
            holder.nationality= (TextView) convertView.findViewById(R.id.nationality);
            holder.birth= (TextView) convertView.findViewById(R.id.birth);
            holder.jersey= (TextView) convertView.findViewById(R.id.jersey);
            holder.market= (TextView) convertView.findViewById(R.id.market);
            holder.contract= (TextView) convertView.findViewById(R.id.contract);

         //   holder.icon=(ImageView) convertView.findViewById(R.id.icon);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Player pla = mPlayers[position];


        holder.name.setText(pla.getName());

        holder.pos.setText(pla.getPosition());
        holder.nationality.setText(pla.getNationality());
        holder.jersey.setText(pla.getJersey() + "");
        holder.birth.setText(pla.getBirth());
        holder.market.setText(pla.getMarket());
        holder.contract.setText(pla.getContract());





        return convertView;
    }




    private static class ViewHolder{
        TextView name;
        TextView pos;
        TextView nationality;
        TextView jersey;
        TextView birth;
        TextView contract;
        TextView market;




    }






}

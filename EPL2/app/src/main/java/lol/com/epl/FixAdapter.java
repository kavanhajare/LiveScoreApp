package lol.com.epl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ParseException;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by arjun on 25/9/15.
 */
public class FixAdapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater inflater;
    //private ScheduleClient scheduleClient;
    private Fixture[] mFixture;
    private List<Fixture> worldpopulationlist = null;
    private ArrayList<Fixture> arraylist;
    ListView list;
    private FixAdapter fixAdapter;
    private ArrayList<Integer> visiblePositions;

    public FixAdapter(Context context, List<Fixture> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Fixture>();
        this.arraylist.addAll(worldpopulationlist);
        visiblePositions = new ArrayList<Integer>();
    }

    public FixAdapter(Context context, Fixture[] fixture) {
        mContext = context;
        mFixture = fixture;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        }
        else
        {
            for (Fixture wp : arraylist)
            {
                if (wp.getLocal().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public Object getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_fix, null);
            holder = new ViewHolder();
            holder.local= (TextView) convertView.findViewById(R.id.local);
            holder.visitor= (TextView) convertView.findViewById(R.id.visitor);
            holder.loc_score= (TextView) convertView.findViewById(R.id.loc_score);
            holder.vis_score= (TextView) convertView.findViewById(R.id.vis_score);
            holder.date=(TextView) convertView.findViewById(R.id.date);
            holder.image=(ImageView) convertView.findViewById(R.id.imageView2);
         //   holder.remind=(Button)convertView.findViewById(R.id.remind);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

     //   Fixture fix = mFixture[position];
        adjustImageVisibility(holder.image, visiblePositions.contains(position));

        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onViewClicked(holder, position);
            }
        });


        holder.local.setText(worldpopulationlist.get(position).getLocal());
        holder.visitor.setText(worldpopulationlist.get(position).getVisitor());
        holder.loc_score.setText(worldpopulationlist.get(position).getLoc_score());
        holder.vis_score.setText(worldpopulationlist.get(position).getVis_score());
        holder.date.setText(worldpopulationlist.get(position).getDate() + "");
      //  holder.image.setVisibility(View.INVISIBLE);
     /*   holder.remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.remind.getText().toString().toLowerCase().equals("remind"))
                {


                  //  holder.remind.setText("dont remind");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    try {
                        Date date = format.parse(worldpopulationlist.get(position).getDate());
                       // System.out.println(date);
                     /* scheduleClient = new ScheduleClient(mContext);
                        scheduleClient.doBindService();

                        scheduleClient.setAlarmForNotification(cal);
                        if(mContext==null)*//*
                        Calendar cal=Calendar.getInstance();
                        cal.setTime(date);
                        long dateInLong = date.getTime();
                        AlarmManager alarms = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);

                        Receiver receiver = new Receiver();
                        IntentFilter filter = new IntentFilter("ALARM_ACTION");
                        mContext.registerReceiver(receiver, filter);

                        Intent intent = new Intent("ALARM_ACTION");
                        intent.putExtra("param", "My scheduled action");
                        PendingIntent operation = PendingIntent.getBroadcast(mContext, 0, intent, 0);
                        // I choose 3s after the launch of my application
                        long diff = dateInLong - Calendar.getInstance().get(Calendar.MILLISECOND);
                        alarms.set(AlarmManager.RTC_WAKEUP, diff, operation) ;


                        holder.remind.setText(dateInLong+"");
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    holder.remind.setText("remind");
                }
            }
        });
*/




        return convertView;
    }
    public void onViewClicked(ViewHolder viewHolder, Integer position) {
        if (visiblePositions.contains(position)) {
            adjustImageVisibility(viewHolder.image, false);
            visiblePositions.remove(position);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                Date date = format.parse(worldpopulationlist.get(position).getDate());
                long dateInLong = date.getTime();
                AlarmManager alarms = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);

                Receiver receiver = new Receiver();
                IntentFilter filter = new IntentFilter("ALARM_ACTION");
                mContext.registerReceiver(receiver, filter);

                Intent intent = new Intent("ALARM_ACTION");
                intent.putExtra("param", "My scheduled action");
                PendingIntent operation = PendingIntent.getBroadcast(mContext, 0, intent, 0);
                // I choose 3s after the launch of my application
                long diff = dateInLong - Calendar.getInstance().get(Calendar.MILLISECOND);
                alarms.set(AlarmManager.RTC_WAKEUP, diff, operation) ;
                Toast.makeText(mContext,"reminder set",Toast.LENGTH_SHORT).show();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }


        } else {
            adjustImageVisibility(viewHolder.image, true);
            visiblePositions.add(position);
        }
    }

    public void adjustImageVisibility(ImageView imageView, boolean visible) {
        if (visible) imageView.setVisibility(View.VISIBLE);
        else imageView.setVisibility(View.INVISIBLE);
    }



    private static class ViewHolder{
        TextView local;
        TextView visitor;
        TextView loc_score;
        TextView vis_score;
        TextView date;
        ImageView image;
       // Button remind;

    }


}

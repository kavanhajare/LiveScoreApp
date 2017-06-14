package lol.com.epl;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by arjun on 10/11/15.
 */
public class Alert extends DialogFragment {
    public static final String TAG = MainActivity.class.getSimpleName();
    private String str;
    private String lin;
    Fixture[] fix;
    public Alert(String s,String link)
    {
      str=s;
        lin=link;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        final Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Do you want us to remind you??")
                .setMessage("All matches of "+str+" will be reminded.");


// Setting Negative "NO" Btn
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });





        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        updateDisplay(fix);
                        Toast.makeText(context.getApplicationContext(), "You clicked yes", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void updateDisplay(Fixture[] fix) {
        String str[]=new String[fix.length];
        for(int i=0;i<fix.length;i++){
            str[i]=fix[i].getDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                Date date = format.parse(str[i]);
                long dateInLong = date.getTime();
                AlarmManager alarms = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);

                Receiver receiver = new Receiver();
                IntentFilter filter = new IntentFilter("ALARM_ACTION");
                getActivity().registerReceiver(receiver, filter);

                Intent intent = new Intent("ALARM_ACTION");
                intent.putExtra("param", "My scheduled action");
                PendingIntent operation = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                // I choose 3s after the launch of my application
                alarms.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000, operation) ;

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


    }

    private Fixture[] getDetails(String json)throws JSONException{
        JSONObject jObj = new JSONObject(json);
        JSONArray jArr = jObj.getJSONArray("fixtures");
        Fixture[] fix=new Fixture[jArr.length()];
        for (int i = 0; i < jArr.length(); i++){
            JSONObject jsonStand = jArr.getJSONObject(i);
            Fixture fi = new Fixture();


            fi.setDate(getString("date", jsonStand));
            fix[i]=fi;
            // arraylist.add(fi);



        }




        return fix;
    }
    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static long getLong(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getLong(tagName);
    }


}
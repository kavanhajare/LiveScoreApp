package lol.com.epl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by arjun on 10/11/15.
 */
public class Receiver extends BroadcastReceiver {

    int notificationID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("param"), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, HomeFragment.class);

        i.putExtra("notificationID", notificationID);

        //---PendingIntent to launch activity if the user selects this notification---
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);

        // Set the details of Notification
        Notification.Builder mBuilder=new Notification.Builder(context);
        mBuilder.setSmallIcon(android.R.drawable.ic_lock_power_off);
        mBuilder.setContentText("Just Football");
        mBuilder.setContentTitle("Your match has started");
        mBuilder.setContentIntent(pendingIntent);

        // Set the notification in the notification Manager----
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nm.notify(notificationID, mBuilder.build() );


    }
}
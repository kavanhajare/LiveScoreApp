package lol.com.epl;

/**
 * Created by arjun on 8/11/15.
 */
import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.app.NotificationManager;
import android.app.Notification.Builder;
//import com.blundell.tut.ui.phone.SecondActivity;

/**
 * This service is started when an Alarm has been raised
 *
 * We pop a notification into the status bar for the user to click on
 * When the user clicks the notification a new activity is opened
 *
 * @author paul.blundell
 */
public class NotifyService extends Service {
    int notificationID = 1;
    /**
     * Class for clients to access
     */
    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    // Unique id to identify the notification.
    private static final int NOTIFICATION = 123;
    // Name of an intent extra we can use to identify if this service was started to create a notification
    public static final String INTENT_NOTIFY = "com.blundell.tut.service.INTENT_NOTIFY";
    // The system notification manager
    private NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // If this service was started by out AlarmTask intent then we want to show our notification
        if(intent.getBooleanExtra(INTENT_NOTIFY, false))
            showNotification();

        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();

    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private void showNotification() {
        // This is the 'title' of the notification
        Intent i = new Intent(this, NotificationView.class);
        i.putExtra("notificationID", notificationID);

        //---PendingIntent to launch activity if the user selects this notification---
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);

        // Set the details of Notification
        Notification.Builder mBuilder=new Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_lock_power_off);
        mBuilder.setContentText("Hello World");
        mBuilder.setContentTitle("My Notification");
        mBuilder.setContentIntent(pendingIntent);

        // Set the notification in the notification Manager----
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(notificationID, mBuilder.build() );

        // Stop the service when we are finished
        stopSelf();
    }
}
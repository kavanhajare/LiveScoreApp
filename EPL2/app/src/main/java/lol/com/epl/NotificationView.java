package lol.com.epl;

/**
 * Created by arjun on 9/11/15.
 */

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

public class NotificationView extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        //---look up the notification manager service.  NM is a service run by android system---
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //---cancel the notification that we started
        nm.cancel(getIntent().getExtras().getInt("notificationID"));
    }
}

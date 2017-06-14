package com.example.karanc.chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.karanc.allied1.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * SendBird Prebuilt UI
 */
public class MainActivity extends Fragment {
    private static final int REQUEST_SENDBIRD_CHAT_ACTIVITY = 100;
    private static final int REQUEST_SENDBIRD_CHANNEL_LIST_ACTIVITY = 101;
    private static final int REQUEST_SENDBIRD_MESSAGING_ACTIVITY = 200;
    private static final int REQUEST_SENDBIRD_MESSAGING_CHANNEL_LIST_ACTIVITY = 201;
    private static final int REQUEST_SENDBIRD_USER_LIST_ACTIVITY = 300;
    public static final String MyPrefs="MyPrefs";
    SharedPreferences sharedPreferences;
    Context context;
    Boolean isInternetPresent = false;

    // Connection detector class
    public static String VERSION = "2.2.8.0";

    @Nullable
 
    /**
        To test push notifications with your own appId, you should replace google-services.json with yours.
        Also you need to set Server API Token and Sender ID in SendBird dashboard.
        Please carefully read "Push notifications" section in SendBird Android documentation
    */ 
    final String appId = "CF089689-E2D8-490D-82ED-0360D374C98D"; /* Sample SendBird Application */
    String userId = SendBirdChatActivity.Helper.generateDeviceUUID(getActivity()); /* Generate Device UUID */
    String userName = "User-" + userId.substring(0, 5); /* Generate User Nickname */
String uname;
    String pusername;
    String enteredname;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v= inflater.inflate(R.layout.activity_chat_main,container,false);
      

       final RelativeLayout rel= (RelativeLayout) v.findViewById(R.id.relativelayout);


        /**
         * Start GCM Service.
         */
      /*  Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
        getActivity().startService(intent);

*/
        ((EditText)v.findViewById(R.id.etxt_nickname)).setText(userName);


        v.findViewById(R.id.btn_start_open_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChannelList();
            }
        });

       // v.findViewById(R.id.main_container).setVisibility(View.VISIBLE);
     //  v.findViewById(R.id.messaging_container).setVisibility(View.INVISIBLE);
        v.findViewById(R.id.btn_start_messaging).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              v.findViewById(R.id.main_container).setVisibility(View.INVISIBLE);
               v.findViewById(R.id.messaging_container).setVisibility(View.VISIBLE);
            }
        });

        v.findViewById(R.id.btn_messaging_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.findViewById(R.id.main_container).setVisibility(View.VISIBLE);
       //         v.findViewById(R.id.messaging_container).setVisibility(View.INVISIBLE);
            }
        });

        v.findViewById(R.id.btn_select_member).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUserList();
            }
        });

        v.findViewById(R.id.btn_start_messaging_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMessagingChannelList();
            }
        });

        return v;
    }
    
    



    private void startChat(String channelUrl) {
        Intent intent = new Intent(getActivity(), SendBirdChatActivity.class);
        Bundle args = SendBirdChatActivity.makeSendBirdArgs(appId, userId, uname, channelUrl);

        intent.putExtras(args);

        startActivityForResult(intent, REQUEST_SENDBIRD_CHAT_ACTIVITY);
    }

    private void startChannelList() {
        Intent intent = new Intent(getActivity(), SendBirdChannelListActivity.class);
        Bundle args = SendBirdChannelListActivity.makeSendBirdArgs(appId, userId, uname);

        intent.putExtras(args);

        startActivityForResult(intent, REQUEST_SENDBIRD_CHANNEL_LIST_ACTIVITY);
    }

    private void startUserList() {
        Intent intent = new Intent(getActivity(), SendBirdUserListActivity.class);
        Bundle args = SendBirdUserListActivity.makeSendBirdArgs(appId, userId, uname);
        intent.putExtras(args);

        startActivityForResult(intent, REQUEST_SENDBIRD_USER_LIST_ACTIVITY);
    }

    private void startMessaging(String [] targetUserIds) {
        Intent intent = new Intent(getActivity(), SendBirdMessagingActivity.class);
        Bundle args = SendBirdMessagingActivity.makeMessagingStartArgs(appId, userId, uname, targetUserIds);
        intent.putExtras(args);

        startActivityForResult(intent, REQUEST_SENDBIRD_MESSAGING_ACTIVITY);
    }

    private void joinMessaging(String channelUrl) {
        Intent intent = new Intent(getActivity(), SendBirdMessagingActivity.class);
        Bundle args = SendBirdMessagingActivity.makeMessagingJoinArgs(appId, userId, uname, channelUrl);
        intent.putExtras(args);

        startActivityForResult(intent, REQUEST_SENDBIRD_MESSAGING_ACTIVITY);
    }

    private void startMessagingChannelList() {
        Intent intent = new Intent(getActivity(), SendBirdMessagingChannelListActivity.class);
        Bundle args = SendBirdMessagingChannelListActivity.makeSendBirdArgs(appId, userId, uname);
        intent.putExtras(args);

    startActivityForResult(intent, REQUEST_SENDBIRD_MESSAGING_CHANNEL_LIST_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_SENDBIRD_MESSAGING_CHANNEL_LIST_ACTIVITY && data != null) {
            joinMessaging(data.getStringExtra("channelUrl"));
        }

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_SENDBIRD_USER_LIST_ACTIVITY && data != null) {
            startMessaging(data.getStringArrayExtra("userIds"));
        }

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_SENDBIRD_CHAT_ACTIVITY && data != null) {
            startMessaging(data.getStringArrayExtra("userIds"));
        }

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_SENDBIRD_CHANNEL_LIST_ACTIVITY && data != null) {
            startChat(data.getStringExtra("channelUrl"));
        }
    }
}


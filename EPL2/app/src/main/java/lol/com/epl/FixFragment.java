package lol.com.epl;

import android.app.Activity;
import android.app.AlarmManager;

import java.text.ParseException;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FixFragment extends Fragment {
    TextView to;
    TextView next;
    private PendingIntent pendingIntent;
    public static final String TAG = MainActivity.class.getSimpleName();
    Fixture[] fix;
    Button b;
    ImageView im;
    ArrayList<Fixture> arraylist = new ArrayList<Fixture>();
   // private DatePicker picker;
   // int day = picker.getDayOfMonth();
   // int month = picker.getMonth();
  //  int year = picker.getYear();
  //  private ScheduleClient scheduleClient;

    ListView list;
    Spinner sp;
    List<String> city;
    EditText editsearch;
    String url;
    private FixAdapter fixAdapter;
    public FixFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fix, container, false);
        list=(ListView)view.findViewById(R.id.list);
    //    scheduleClient = new ScheduleClient(getContext());
        //scheduleClient.doBindService();
      //  Date date = new Date();
     //   Calendar c = Calendar.getInstance();
    //    b = (Button)view.findViewById(R.id.button4);
        Calendar calendar = Calendar.getInstance();


        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND));
        calendar.set(Calendar.AM_PM,Calendar.PM);
        im = (ImageView)view.findViewById(R.id.imageView2);
     //   im.setVisibility(View.INVISIBLE);

    /*    Intent myIntent = new Intent(getActivity(), MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(getContext().ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);*/
    //  startActivity(myIntent);



      //c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE));
     //   b.setText("");

      //  c.set(year, month, day);
        //c.set();

        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
       //scheduleClient.setAlarmForNotification(c);





    /*    to = (TextView)view.findViewById(R.id.textView3);

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR,-7);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String todayAsString = dateFormat.format(today);
        String tomorrowAsString = dateFormat.format(tomorrow);
         to.setText(todayAsString.toString());
        next.setText(tomorrowAsString.toString());*/
        next = (TextView)view.findViewById(R.id.textView12);
         url = "http://api.football-data.org/alpha/soccerseasons/398/fixtures";
        editsearch = (EditText)view.findViewById(R.id.inputSearch);


                city = new ArrayList<String>();
        city.add("EPL");
        city.add("Premiera Division");
        city.add("Bundesliga");
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item , city);
        sp = (Spinner)view.findViewById(R.id.spinner);
        sp.setAdapter(aa);

        call();

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                if (pos == 0) {
                    url = "http://api.football-data.org/alpha/soccerseasons/398/fixtures";
                    call();
                } else if (pos == 1) {
                    url = "http://api.football-data.org/alpha/soccerseasons/399/fixtures";
                    call();
                } else if (pos == 2) {
                    url = "http://api.football-data.org/alpha/soccerseasons/394/fixtures";
                    call();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       // b.setText(fix.length+"");










        // Inflate the list_com for this fragment
        return view;
    }

    private void call() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    //Response response = call.execute();
                    String json = response.body().string();

                    //Response response = call.execute();
                    if (response.isSuccessful()) {
                        Log.v(TAG, json);
                        fix = getDetails(json);
                      //  fix = arraylist;


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDisplay(fix);
                            }
                        });





                    } else {
                        Log.v(TAG, json);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "no net Exception");
                } catch (JSONException e){
                    Log.e(TAG, "no json Exception");

                }
            }



        });



    }

    private void updateDisplay(Fixture[] fix) {
     //  fixAdapter = new FixAdapter(getActivity().getApplicationContext() ,fix);
        for(int i=0;i<fix.length;i++)
        {
            arraylist.add(fix[i]);
        }
      fixAdapter = new FixAdapter(getActivity(), arraylist);

        list.setAdapter(fixAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ItemClicked item = fixAdapter.getItem(position);

             //   Intent intent = new Intent(getActivity(), destinationActivity.class);
//based on item add info to intent
            //    startActivity(intent);
                Fixture item = (Fixture)parent.getItemAtPosition(position);

               String s= item.getDate();
                Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
                im.setVisibility(View.VISIBLE);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                try {
                    Date date = format.parse(s);
                    long dateInLong = date.getTime();
                    AlarmManager alarms = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

                    Receiver receiver = new Receiver();
                    IntentFilter filter = new IntentFilter("ALARM_ACTION");
                   getActivity().registerReceiver(receiver, filter);

                    Intent intent = new Intent("ALARM_ACTION");
                    intent.putExtra("param", "My scheduled action");
                    PendingIntent operation = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
                    // I choose 3s after the launch of my application
                    long diff = dateInLong - Calendar.getInstance().get(Calendar.MILLISECOND);
                    alarms.set(AlarmManager.RTC_WAKEUP, diff, operation) ;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }




        });


        // b.setText("nnn");
        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                fixAdapter.filter(text);

            }
        });


    }


    private Fixture[] getDetails(String json)throws JSONException{

        JSONObject jObj = new JSONObject(json);
        JSONArray jArr = jObj.getJSONArray("fixtures");
        Fixture[] fix=new Fixture[jArr.length()];
        for (int i = 0; i < jArr.length(); i++){
            JSONObject jsonStand = jArr.getJSONObject(i);
            Fixture fi = new Fixture();

            fi.setLocal(getString("homeTeamName", jsonStand));
            fi.setVisitor(getString("awayTeamName", jsonStand));

            JSONObject res = jsonStand.getJSONObject("result");
            fi.setLoc_score(getString("goalsHomeTeam", res));
            fi.setVis_score(getString("goalsAwayTeam", res));
            fi.setDate(getString("date", jsonStand));
            fix[i]=fi;
           // arraylist.add(fi);
            ParseUser currentUser = ParseUser.getCurrentUser();
           String s= (String)currentUser.get("team");
  if(fix[i].getLocal()==s)
  {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
      try {
          Date date = format.parse(fix[i].getDate());
          long dateInLong = date.getTime();
          AlarmManager alarms = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

          Receiver receiver = new Receiver();
          IntentFilter filter = new IntentFilter("ALARM_ACTION");
          getActivity().registerReceiver(receiver, filter);

          Intent intent = new Intent("ALARM_ACTION");
          intent.putExtra("param", "My scheduled action");
          PendingIntent operation = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
          // I choose 3s after the launch of my application
          long diff = dateInLong - Calendar.getInstance().get(Calendar.MILLISECOND);
          alarms.set(AlarmManager.RTC_WAKEUP, diff, operation) ;



      } catch (ParseException e) {
          e.printStackTrace();
      }
  }

        }




        return fix;
    }



    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

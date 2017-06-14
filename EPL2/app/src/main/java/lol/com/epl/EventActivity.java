package lol.com.epl;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Calendar;
import java.util.Date;


public class EventActivity extends ActionBarActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    ListView list;
    private EventAdapter eventAdapter;
String pos;
    Event[] eve;
    int t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        list=(ListView)findViewById(R.id.list);



        final TextView t = (TextView)findViewById(R.id.textView3);
        t.setText(pos);
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR,-7);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String to = dateFormat.format(today);
        String tom = dateFormat.format(tomorrow);

        String url = "http://football-api.com/api/?Action=fixtures&APIKey=50010039-4dc3-b883-dab596512041&comp_id=1204&&from_date="+tom+"&&to_date="+to;


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
                        eve= getDetails(json);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDisplay();
                            }
                        });







                    } else {
                        Log.v(TAG, json);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "net Exception");
                } catch(JSONException e){
                    Log.e(TAG, "json Exception");
                }

            }


        });










    }

    private void updateDisplay() {
        eventAdapter = new EventAdapter(this,eve);

        list.setAdapter(eventAdapter);

    }

    private Event[] getDetails(String json)throws JSONException{
        Intent in = getIntent();
        pos =  in.getStringExtra("pos");
        t = Integer.parseInt(pos);

        JSONObject jObj = new JSONObject(json);
        JSONArray jArr = jObj.getJSONArray("matches");
        JSONObject JSONFoot = jArr.getJSONObject(t);
        JSONArray res = JSONFoot.getJSONArray("match_events");
        Event[] eve = new Event[res.length()];
        for (int i = 0; i < res.length(); i++) {
            JSONObject jsonStand = res.getJSONObject(i);
            Event ev = new Event();
            ev.setTeam(getString("event_team", jsonStand));
            ev.setType(getString("event_type", jsonStand));
            ev.setResult(getString("event_result", jsonStand));
            ev.setMin(getString("event_minute", jsonStand));
            ev.setPlayer(getString("event_player", jsonStand));
            eve[i]=ev;

        }


        return eve;
    }
    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

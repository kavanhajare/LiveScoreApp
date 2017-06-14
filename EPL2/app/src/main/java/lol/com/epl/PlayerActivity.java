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


public class PlayerActivity extends ActionBarActivity {
TextView tx;
    Player[] pla;
    ListView list;
    private PlayerAdapter playerAdapter;
    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        list =(ListView)findViewById(R.id.list);
     //   tx =(TextView)findViewById(R.id.text);
        Intent in = getIntent();
       String str= in.getStringExtra("lin")+"/players";
     //   tx.setText(str);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(str)
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
                        pla = getDetails(json);

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
                }  catch (JSONException e) {
                    Log.e(TAG, "json Exception");
                }
            }


        });





    }

    private void updateDisplay() {
        playerAdapter = new PlayerAdapter(getApplicationContext() ,pla);


        list.setAdapter(playerAdapter);
    }

    private Player[] getDetails(String json)throws JSONException{
        JSONObject jObj = new JSONObject(json);
        JSONArray jArr = jObj.getJSONArray("players");
        Player[] pla = new Player[jArr.length()];
        for (int i = 0; i < jArr.length(); i++) {
            JSONObject jsonStand = jArr.getJSONObject(i);
            Player pl = new Player();
            pl.setName(getString("name", jsonStand));
            pl.setNationality(getString("nationality", jsonStand));
            pl.setMarket(getString("marketValue", jsonStand));
            pl.setContract(getString("contractUntil", jsonStand));
            pl.setBirth(getString("dateOfBirth", jsonStand));
            pl.setJersey(getInt("jerseyNumber", jsonStand));
            pl.setPosition(getString("position", jsonStand));

            pla[i]=pl;
        }

        return pla;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
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

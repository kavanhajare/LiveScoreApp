package lol.com.epl;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.List;


public class FixTeamActivity extends ActionBarActivity {
    TextView tx;
    private FixTeam[] fit;
    private FixTeamAdapter fixTeamAdapter;
    ListView list;
    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_team);
       // tx =(TextView)findViewById(R.id.text);
        Intent in = getIntent();
        String str= in.getStringExtra("ft")+"/fixtures";
       // tx.setText(str);
        list=(ListView)findViewById(R.id.list);






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
                        fit = getDetail(json);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDisplay();
                            }
                        });


                    } else {
                        Log.v(TAG, "error");
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception");
                } catch (JSONException e) {
                    Log.e(TAG, "Exception");
                }
            }



        });










    }

    private void updateDisplay() {
        fixTeamAdapter = new FixTeamAdapter(this,fit);

        list.setAdapter(fixTeamAdapter);
    }

    private FixTeam[] getDetail(String json) throws JSONException{

        JSONObject jObj = new JSONObject(json);
        JSONArray jArr = jObj.getJSONArray("fixtures");
        FixTeam[] fix=new FixTeam[jArr.length()];
        for (int i = 0; i < jArr.length(); i++){
            JSONObject jsonStand = jArr.getJSONObject(i);
            FixTeam fi = new FixTeam();

            fi.setLocal(getString("homeTeamName", jsonStand));
            fi.setVisitor(getString("awayTeamName", jsonStand));
            JSONObject res = jsonStand.getJSONObject("result");
            fi.setLoc_score(getString("goalsHomeTeam", res));
            fi.setVis_score(getString("goalsAwayTeam", res));
            fi.setDate(getString("date", jsonStand));
            fix[i]=fi;
            // arraylist.add(fi);



        }




        return fix;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fix_team, menu);
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

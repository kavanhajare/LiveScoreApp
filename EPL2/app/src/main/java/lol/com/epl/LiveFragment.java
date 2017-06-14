package lol.com.epl;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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



public class LiveFragment extends Fragment {
    ListView list;
    private LiveAdapter liveAdapter;
     TextView tx;
    public static final String TAG = MainActivity.class.getSimpleName();
    Live[] liv;


    public LiveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the list_com for this fragment
        View view = inflater.inflate(R.layout.fragment_live, container, false);
          String url = "http://football-api.com/api/?Action=today&APIKey=50010039-4dc3-b883-dab596512041&comp_id=1204";
tx=(TextView)view.findViewById(R.id.textView5);
        list=(ListView)view.findViewById(R.id.list);
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
                        liv = getDetails(json);


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDisplay();
                            }
                        });


                    } else {
                        Log.v(TAG, json);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception");
                } catch (JSONException e) {
                    Log.e(TAG, "Exception");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    tx.setText("No matches today");
                        }
                    });
                }
            }


        });



        return  view;




    }

    private void updateDisplay() {
        liveAdapter = new LiveAdapter(getActivity().getApplicationContext() ,liv);

        list.setAdapter(liveAdapter);

    }

    private Live[] getDetails(String json)throws JSONException{
        JSONObject jObj = new JSONObject(json);
        JSONArray jArr = jObj.getJSONArray("matches");
        Live[] liv=new Live[jArr.length()];
        for (int i = 0; i < jArr.length(); i++) {
            JSONObject jsonStand = jArr.getJSONObject(i);
            Live li = new Live();
            li.setLoc_score(getString("match_localteam_score",jsonStand));
            li.setVis_score(getString("match_visitorteam_score", jsonStand));
            li.setLocal(getString("match_localteam_name", jsonStand));
            li.setVisitor(getString("match_visitorteam_name", jsonStand));
            li.setTimer(getString("match_timer", jsonStand));
            li.setMatch_id(getString("match_id", jsonStand));
            liv[i]=li;


        }



        return liv;
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

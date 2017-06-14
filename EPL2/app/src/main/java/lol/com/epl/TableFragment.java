package lol.com.epl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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


public class TableFragment extends Fragment {
    public static final String TAG = MainActivity.class.getSimpleName();
    int leg;
    private Stand[] std;
    ListView list;
    private StandAdapter standAdapter;
    Spinner sp;
    List<String> city;
    String url;



    public TableFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String co;

         url = "http://api.football-data.org/alpha/soccerseasons/398/leagueTable";
        View view = inflater.inflate(R.layout.activity_table_fragment, container, false);
       list=(ListView)view.findViewById(R.id.list);

        city=new ArrayList<String>();
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
                    leg=0;
                    url = "http://api.football-data.org/alpha/soccerseasons/398/leagueTable";
                    call();
                }else if(pos==1) {
                    leg=1;
                    url = "http://api.football-data.org/alpha/soccerseasons/399/leagueTable";
                    call();
                }
                else if(pos==2){
                    leg=2;
                    url = "http://api.football-data.org/alpha/soccerseasons/394/leagueTable";
                    call();

            }}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });













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
                        std = getDetails(json);

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
                }
            }


        });
    }

    private void updateDisplay() {
        standAdapter = new StandAdapter(getActivity().getApplicationContext() ,std);


        list.setAdapter(standAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity().getApplicationContext(), std[position].getRec(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private Stand[] getDetails(String json) throws JSONException{

        JSONObject jObj = new JSONObject(json);
        JSONArray jArr = jObj.getJSONArray("standing");

        Stand[] std = new Stand[jArr.length()];
        for (int i = 0; i < jArr.length(); i++){
            JSONObject jsonStand = jArr.getJSONObject(i);
            Stand st = new Stand();
            st.setTeam(getString("teamName", jsonStand));
            st.setPos(getString("position", jsonStand));
            st.setWin(getString("goals", jsonStand));
            st.setLose(getString("points", jsonStand));
            st.setIcon(getString("teamName",jsonStand));


            std[i]=st;

        }

        return std;
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
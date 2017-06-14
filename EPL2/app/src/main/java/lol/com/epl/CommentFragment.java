package lol.com.epl;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.Spinner;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class CommentFragment extends Fragment {
    public static final String TAG = MainActivity.class.getSimpleName();
    ListView list;
    Comment[] com;
    CommentAdapter comAdapter;
    public CommentFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_comment_fragment, container, false);
        list=(ListView)rootView.findViewById(R.id.list);


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
                        com=getDetails(json);
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
                    Log.e(TAG, "net Exception");
                }catch(JSONException e){
                    Log.e(TAG, "json Exception");
                }

            }


        });









        // Inflate the list_com for this fragment
        return rootView;
    }

    private void updateDisplay() {
        comAdapter = new CommentAdapter(getActivity().getApplicationContext() ,com);

        list.setAdapter(comAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getActivity(), EventActivity.class);
                in.putExtra("pos", position+"");
                startActivity(in);

            }
        });


    }

    private Comment[] getDetails(String json)throws JSONException{

        JSONObject jObj = new JSONObject(json);
        JSONArray jArr = jObj.getJSONArray("matches");
        Comment[] com=new Comment[jArr.length()];
        for (int i = 0; i < jArr.length(); i++) {
            JSONObject jsonStand = jArr.getJSONObject(i);
            Comment co = new Comment();
            co.setLoc_score(getString("match_localteam_score",jsonStand));
            co.setVis_score(getString("match_visitorteam_score", jsonStand));
            co.setLocal(getString("match_localteam_name", jsonStand));
            co.setVisitor(getString("match_visitorteam_name", jsonStand));

            co.setDate(getString("match_date", jsonStand));
            co.setHalf(getString("match_ht_score",jsonStand));
            com[i]=co;


        }
        return com;

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

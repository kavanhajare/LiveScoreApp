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
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import static lol.com.epl.R.drawable.toggle_collapse;


public class TeamFragment extends Fragment {
     ImageView icon;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    public static final String TAG = MainActivity.class.getSimpleName();
    String url;
    String link;
    String name;
    TextView tx;
    Team te;
    Team[] tea;




    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_team, container, false);
        View vi= inflater.inflate(R.layout.list_group, container, false);
        icon = (ImageView) vi.findViewById(R.id.imageView);
       // tx = (TextView) v.findViewById(R.id.textView);
       url = "http://api.football-data.org/alpha/soccerseasons/398/teams";





        // get the listview
        expListView = (ExpandableListView)v. findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity().getApplicationContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, final int childPosition, long id) {
                if (groupPosition == 0) {
                    url = "http://api.football-data.org/alpha/soccerseasons/398/teams";
                } else if (groupPosition == 1) {
                    url = "http://api.football-data.org/alpha/soccerseasons/399/teams";
                } else if (groupPosition == 2) {
                    url = "http://api.football-data.org/alpha/soccerseasons/394/teams";
                }

                Toast.makeText(
                        getActivity().getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                name = listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition);
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
                                tea = getDetails(json);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                      //  tx.setText(tea[childPosition].getLink());
                                        Intent in = new Intent(getActivity(), DisplayActivity.class);
                                        in.putExtra("link", tea[childPosition].getLink());
                                        startActivity(in);
                                    }
                                });

                                // call();

                                //return tea;


                            } else {
                                Log.v(TAG, json);
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "Exception");
                        } catch (JSONException e) {
                            Log.e(TAG, "Exception");
                        }
                        //   return tea;
                    }


                });
                //    tx.setText(name);
                //  for (int i = 0; i < tea.length; i++) {
                //   if(name.equals(tea[i].getName()))

                //  tx.setText(tea.getLink());


                return false;
            }
        });








        //  call();


        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                icon.setImageResource(R.drawable.toggle_expand);
                Toast.makeText(getActivity().getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
             //   icon.setVisibility(View.INVISIBLE);
                icon.setImageResource(R.drawable.toggle_collapse);
                Toast.makeText(getActivity().getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });
        
        
        
        return v;
    }

    private void call(String url) {






    }


    private  Team[] getDetails(String json)throws JSONException {
        JSONObject jObj = new JSONObject(json);
        JSONArray jArr = jObj.getJSONArray("teams");
        Team[] tea = new Team[jArr.length()];
       // String[] str = new String[jArr.length()];
       // String st;
        for (int i = 0; i < jArr.length(); i++) {
            Team te = new Team();
            JSONObject jsonStand = jArr.getJSONObject(i);
            te.setName(getString("name",jsonStand));


                JSONObject curr = jsonStand.getJSONObject("_links");
                JSONObject sel = curr.getJSONObject("self");
                // JSONObject href = curr.getJSONObject("href");

              te.setLink(getString("href", sel));
              tea[i]=te;




            }

    return tea;
    }






    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("English Premiere League");
        listDataHeader.add("Primera Division");
        listDataHeader.add("Bundesliga");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("Manchester United FC");
        top250.add("Tottenham Hotspur FC");
        top250.add("AFC Bournemouth");
        top250.add("Aston Villa FC");
        top250.add("TEverton FC");
        top250.add("Watford FC");
        top250.add("Leicester City FC");
        top250.add("Sunderland AFC");
        top250.add("Norwich City FC");
        top250.add("Crystal Palace FC");
        top250.add("Chelsea FC");
        top250.add("Swansea City FC");
        top250.add("Newcastle United FC");
        top250.add("Southampton FC");
        top250.add("Arsenal FC");
        top250.add("West Ham United FC");
        top250.add("Stoke City FC");
        top250.add("Liverpool FC");
        top250.add("West Bromwich Albion FC");
        top250.add("Manchester City FC");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("RC Deportivo La Coruna");
        nowShowing.add("Real Sociedad de Fútbol");
        nowShowing.add("RCD Espanyol");
        nowShowing.add("Getafe CF");
        nowShowing.add("Club Atlético de Madrid");
        nowShowing.add("UD Las Palmas");
        nowShowing.add("Rayo Vallecano de Madrid");
        nowShowing.add("Valencia CF");
      //  nowShowing.add("RCD Espanyol");
        nowShowing.add("Málaga CF");
        nowShowing.add("Sevilla FC");
        nowShowing.add("Athletic Club");
        nowShowing.add("FC Barcelona");
        nowShowing.add("Sporting Gijón");
        nowShowing.add("Real Madrid CF");
        nowShowing.add("Levante UD");
        nowShowing.add("RC Celta de Vigo");
        nowShowing.add("Real Betis");
        nowShowing.add("Villarreal CF");
        nowShowing.add("Granada CF");
        nowShowing.add("SD Eibar");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("FC Bayern München");
        comingSoon.add("Hamburger SV");
        comingSoon.add("FC Augsburg");
        comingSoon.add("Hertha BSC");
        comingSoon.add("Bayer Leverkusen");
        comingSoon.add("TSG 1899 Hoffenheim");
        comingSoon.add("SV Darmstadt 98");
        comingSoon.add("Hannover 96");
        comingSoon.add("1. FSV Mainz 05");
        comingSoon.add("FC Ingolstadt 04");
        comingSoon.add("Werder Bremen");
        comingSoon.add("FC Schalke 04");
        comingSoon.add("Borussia Dortmund");
        comingSoon.add("HBor. Mönchengladbach");
        comingSoon.add("VfL Wolfsburg");
        comingSoon.add("Eintracht Frankfurt");
        comingSoon.add("VfB Stuttgart");
        comingSoon.add("1. FC Köln");


        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}

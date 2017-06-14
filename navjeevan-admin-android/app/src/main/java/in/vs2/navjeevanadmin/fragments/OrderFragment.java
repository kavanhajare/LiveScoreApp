package in.vs2.navjeevanadmin.fragments;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import in.vs2.navjeevanadmin.R;
import in.vs2.navjeevanadmin.adapters.OrderAdapter;
import in.vs2.navjeevanadmin.models.Const;
import in.vs2.navjeevanadmin.models.FoodOrder;
import in.vs2.navjeevanadmin.utils.JSONParser;
import in.vs2.navjeevanadmin.utils.Logcat;
import in.vs2.navjeevanadmin.utils.Utility;
import in.vs2.navjeevanadmin.views.GridSpacingItemDecoration;


public class OrderFragment extends Fragment {

	private static final String tag = OrderFragment.class.getSimpleName();
	
	private View mView;

    RecyclerView listView;

    RecyclerView.LayoutManager mLayoutManager;

    RecyclerView.Adapter mAdapter;

    CoordinatorLayout containerLayout;

    ArrayList<FoodOrder> mOrders;

    static final String SAVED_ORDER = "Orders";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_order, container, false);

        initViews();

        if (savedInstanceState != null) {
            mOrders = (ArrayList<FoodOrder>) savedInstanceState.getSerializable(SAVED_ORDER);
        }

        if (mOrders != null){
            fillGrid();
        }else{
            if (Utility.isOnline(getActivity())){
                new GetOrders().execute();
            }else{
                Snackbar.make(containerLayout, "No internet connection found!", Snackbar.LENGTH_LONG)
                        .show();
            }
        }

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mMessageReceiver, new IntentFilter("RemoveOrder"));

		return mView;
	}


    private void initViews(){

        containerLayout = (CoordinatorLayout) mView.findViewById(R.id.main_content);

        // Calling the RecyclerView
        listView = (RecyclerView) mView.findViewById(R.id.list);
        listView.setHasFixedSize(false);

        // The number of Columns
        //mLayoutManager = new GridLayoutManager(getActivity(), 1);

        int spanCount = 2;
        int spacing = 5;
        boolean includeEdge = true;
        listView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        // The number of Columns
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if (Utility.isTablet(getActivity())){
                mLayoutManager = new GridLayoutManager(getActivity(), 1);
            }else{
                mLayoutManager = new GridLayoutManager(getActivity(), 1);
            }

        }
        else{
            if (Utility.isTablet(getActivity())){
                mLayoutManager = new GridLayoutManager(getActivity(), 2);
            }else{
                mLayoutManager = new GridLayoutManager(getActivity(), 1);
            }

        }
        listView.setLayoutManager(mLayoutManager);


    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // Get extra data included in the Intent
            Logcat.e("RemoveOrder", "Received");
            if (intent.hasExtra("OrderId")){
                int orderId = intent.getIntExtra("OrderId",-1);

                if (orderId != -1){
                    int size = mOrders.size();
                    for (int i = 0; i < size; i++) {
                        if (mOrders.get(i).getOrderId() == orderId){

                            try {
                                mOrders.get(i).setStatus(intent.getIntExtra("Status",-1));
                                mOrders.get(i).setReason(intent.getStringExtra("Reason"));
                                mAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Logcat.e("RemoveOrder", "Error : " +e.toString());
                            }
                            break;


                        }
                    }
                }
            }


        }
    };

    private void fillGrid(){

        if (mOrders != null && mOrders.size() > 0) {
            mAdapter = new OrderAdapter(mOrders,getActivity(),false);
            listView.setAdapter(mAdapter);
        }else{
            Snackbar.make(containerLayout,  "There are no orders!", Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(SAVED_ORDER, mOrders);
    }


    public class GetOrders extends AsyncTask<String,Integer,JSONObject>{

        SpotsDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new SpotsDialog(getActivity(),R.style.CustomDialog);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONParser parser = new JSONParser(getActivity());

                return  parser.getJSONFromUrl(Const.GET_ORDER, JSONParser.GET,null);

            }catch (Exception e){
                return  null;
            }

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            try {
                pd.dismiss();
                if (result != null){
                    if (result.has("Data") && !result.isNull("Data")) {
                        mOrders = FoodOrder.getOrdersFromJson(result.getJSONArray("Data"));
                        fillGrid();
                    }else{
                        Toast.makeText(getActivity(),"" + result.getString("Message"),Toast.LENGTH_LONG).show();
                    }
                }else{
                    Snackbar.make(containerLayout,  "Something went wrong, try again later!", Snackbar.LENGTH_LONG)
                            .show();
                }

            }catch (Exception e){

            }
        }
    }




}
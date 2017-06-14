package vs2.navjivanclient.fragments;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import vs2.navjivanclient.AnalyticsApplication;
import vs2.navjivanclient.R;
import vs2.navjivanclient.adapters.CategoryAdapter;
import vs2.navjivanclient.models.Category;
import vs2.navjivanclient.models.Const;
import vs2.navjivanclient.utils.JSONParser;
import vs2.navjivanclient.utils.Utility;
import vs2.navjivanclient.views.GridSpacingItemDecoration;


public class HomeFragment extends Fragment {

	private static final String tag = HomeFragment.class.getSimpleName();
	
	private View mView;

	RecyclerView listView;

	RecyclerView.LayoutManager mLayoutManager;

	RecyclerView.Adapter mAdapter;

    ArrayList<Category> mCategories;


    CoordinatorLayout containerLayout;
    private Tracker mTracker;

    static final String SAVED_CATEGORY = "categories";

    public int count = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_home, container, false);

        initViews();

        if (savedInstanceState != null) {
            mCategories = (ArrayList<Category>) savedInstanceState.getSerializable(SAVED_CATEGORY);
        }

        if (mCategories != null){
            fillGrid();
        }else{
            if (Utility.isOnline(getActivity())){
                new GetCategory().execute();
            }else{
                Snackbar.make(containerLayout, "No internet connection found!", Snackbar.LENGTH_LONG)
                        //.setAction("Undo", mOnClickListener)
                        //.setActionTextColor(Color.RED)
                        .show();
            }
        }
		return mView;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendTracking();
    }

    private void sendTracking()
    {
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Categories Fragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void initViews(){

        containerLayout = (CoordinatorLayout) mView.findViewById(R.id.main_content);

        // Calling the RecyclerView
        listView = (RecyclerView) mView.findViewById(R.id.list);
        listView.setHasFixedSize(false);

        // The number of Columns
        //mLayoutManager = new GridLayoutManager(getActivity(), 1);

        int spanCount = 2;
        int spacing = 10;
        boolean includeEdge = true;
        listView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        // The number of Columns
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if (Utility.isTablet(getActivity())){
                mLayoutManager = new GridLayoutManager(getActivity(), 2);
            }else{
                mLayoutManager = new GridLayoutManager(getActivity(), 1);
            }

        }
        else{
            if (Utility.isTablet(getActivity())){
                mLayoutManager = new GridLayoutManager(getActivity(), 3);
            }else{
                mLayoutManager = new GridLayoutManager(getActivity(), 2);
            }

        }
        listView.setLayoutManager(mLayoutManager);

	}

    private void fillGrid(){
        if (mCategories != null && mCategories.size() > 0) {
            mAdapter = new CategoryAdapter(mCategories,getActivity());
            listView.setAdapter(mAdapter);
        }else{
            Snackbar.make(containerLayout, "No category found!", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(SAVED_CATEGORY, mCategories);
    }


    public class GetCategory extends AsyncTask<String,Integer,JSONObject>{

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

                return parser.getJSONFromUrl(Const.GET_CATEGORY, JSONParser.GET,null);

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
                        mCategories = Category.getCategoriesFromJson(result.getJSONArray("Data"));
                        fillGrid();
                    }else{
                        Snackbar.make(containerLayout, "" + result.getString("Message"), Snackbar.LENGTH_LONG)
                                .show();
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
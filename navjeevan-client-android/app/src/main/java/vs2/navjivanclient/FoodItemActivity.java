package vs2.navjivanclient;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import vs2.navjivanclient.adapters.FoodItemAdapter;
import vs2.navjivanclient.databases.DatabaseFunctions;
import vs2.navjivanclient.models.Category;
import vs2.navjivanclient.models.Const;
import vs2.navjivanclient.models.FoodItem;
import vs2.navjivanclient.objects.User;
import vs2.navjivanclient.preference.PreferenceData;
import vs2.navjivanclient.utils.JSONParser;
import vs2.navjivanclient.utils.Logcat;
import vs2.navjivanclient.utils.Utility;
import vs2.navjivanclient.verifications.VerificationActivity;
import vs2.navjivanclient.views.GridSpacingItemDecoration;


public class FoodItemActivity extends BaseActivity{

    RecyclerView listView;

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    ArrayList<FoodItem> mItems;

    Category category;

    CoordinatorLayout containerLayout;

    static final String SAVED_ITEMS = "Items";

    public static TextView textBadge;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooditem);

        sendTracking();
        DatabaseFunctions.openDB(FoodItemActivity.this);

        initViews(savedInstanceState);


    }

    private void sendTracking()
    {
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Item activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    private void initViews(Bundle savedInstanceState){

        if (getIntent().hasExtra("Category")){
            category = (Category)getIntent().getSerializableExtra("Category");
            setActionBarTitle(category.getCategory(),true);
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Category")
                    .setAction(category.getCategory())
                    .build());
        }else{
            setActionBarTitle(getResources().getString(R.string.app_name),true);
        }

        containerLayout = (CoordinatorLayout) findViewById(R.id.main_content);


        // Calling the RecyclerView
        listView = (RecyclerView) findViewById(R.id.list);
        listView.setHasFixedSize(false);

        // The number of Columns
        //mLayoutManager = new GridLayoutManager(getActivity(), 1);

        int spanCount = 2;
        int spacing = 10;
        boolean includeEdge = true;
        listView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        // The number of Columns
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if (Utility.isTablet(FoodItemActivity.this)){
                mLayoutManager = new GridLayoutManager(FoodItemActivity.this, 2);
            }else{
                mLayoutManager = new GridLayoutManager(FoodItemActivity.this, 1);
            }

        }
        else{
            if (Utility.isTablet(FoodItemActivity.this)){
                mLayoutManager = new GridLayoutManager(FoodItemActivity.this, 3);
            }else{
                mLayoutManager = new GridLayoutManager(FoodItemActivity.this, 2);
            }

        }
        listView.setLayoutManager(mLayoutManager);




        if (savedInstanceState != null) {
            mItems = (ArrayList<FoodItem>) savedInstanceState.getSerializable(SAVED_ITEMS);
        }



        if (mItems != null){
            fillGrid();
        }else{


            if (category != null){
                if (Utility.isOnline(FoodItemActivity.this)){
                    new GetFoodItem().execute();
                }else{
                    Snackbar.make(containerLayout, "No internet connection found!", Snackbar.LENGTH_LONG).show();
                }
            }

        }

    }

    private void fillGrid(){
        if (mItems != null && mItems.size() > 0) {
            mAdapter = new FoodItemAdapter(mItems,FoodItemActivity.this);
            listView.setAdapter(mAdapter);
        }else{
            Snackbar.make(containerLayout,  "NO items found!", Snackbar.LENGTH_LONG)
                    .show();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cart, menu);

        final  MenuItem cartMenu = menu.findItem(R.id.action_cart);

        MenuItemCompat.setActionView(cartMenu, R.layout.layout_badge);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(cartMenu);

        cartMenu.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (User.userExist(FoodItemActivity.this)) {
                    int count = DatabaseFunctions.getcartItemCount();
                    if (count > 0) {
                        startActivity(new Intent(FoodItemActivity.this, CartActivity.class));
                    }
                } else {
                    Intent intent = new Intent(FoodItemActivity.this, VerificationActivity.class);
                    startActivityForResult(intent, MainActivity.VERIFY_CODE);
                }
            }
        });
        textBadge = (TextView) notifCount.findViewById(R.id.text_badge);
        textBadge.setText(""+ DatabaseFunctions.getcartItemCount());

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (textBadge != null){
            textBadge.setText("" + DatabaseFunctions.getcartItemCount());
        }
        if (mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == MainActivity.VERIFY_CODE){
                if (resultCode == RESULT_OK){
                    String IsVerified = data.getStringExtra("IsVerified");
                    String PhoneNumber = data.getStringExtra("PhoneNumber");
                    Logcat.e("onActivityResult", "IsVerified: " + IsVerified);
                    Logcat.e("onActivityResult","PhoneNumber: "+PhoneNumber);
                    toast(PhoneNumber+" Phone verified "+IsVerified,true);
                    if (IsVerified.equals("true")){
                        PreferenceData.setBooleanPref(PreferenceData.KEY_IS_VERIFIED, FoodItemActivity.this, true);
                        PreferenceData.setStringPref(PreferenceData.KEY_MOBILE_NUMBER, FoodItemActivity.this, PhoneNumber);
                        String GCMId = PreferenceData.getStringPref(PreferenceData.KEY_GCM_ID,FoodItemActivity.this);
                        new UserLoginAPI().execute(PhoneNumber,GCMId);

                    }else{
                        PreferenceData.setBooleanPref(PreferenceData.KEY_IS_VERIFIED,FoodItemActivity.this,false);
                    }
                }
                if (resultCode == RESULT_CANCELED){

                }

            }else{
                Logcat.e("onActivityResult","requestCode: "+requestCode);
            }
        }catch (Exception e){
            Logcat.e("onActivityResult","Exception: "+e.toString());
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_ITEMS, mItems);
    }



    public class GetFoodItem extends AsyncTask<String,Integer,JSONObject> {

        SpotsDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new SpotsDialog(FoodItemActivity.this,R.style.CustomDialog);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONParser parser = new JSONParser(FoodItemActivity.this);
                return  parser.getJSONFromUrl(Const.GET_FOODITEM+category.getCategoryId(),JSONParser.GET,null);

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
                        mItems = FoodItem.getFoodItemsFromJson(result.getJSONArray("Data"));
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


    public class UserLoginAPI extends AsyncTask<String, Integer, JSONObject> {

       SpotsDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pd = new SpotsDialog(FoodItemActivity.this,R.style.CustomDialog);
                pd.show();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONParser parser = new JSONParser(FoodItemActivity.this);
            String url =Const.USER_LOGIN +"number="+params[0]+"&deviceId="+params[1];
            Logcat.e("GCM ID : ","GCM ID : "+params[1]);
            return parser.getJSONFromUrl(url, JSONParser.GET,null);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (pd != null && pd.isShowing()){
                pd.dismiss();
            }
            try {
                int status = 0;
                if (jsonObject != null){
                    if (jsonObject.has("Status")){
                        status = jsonObject.getInt("Status");
                        if (jsonObject.has("HotelStatus")){
                            int hotelStatus = jsonObject.getInt("HotelStatus");
                            PreferenceData.setHotelStatus(FoodItemActivity.this,hotelStatus);
                        }
                        if (status == 0){
                            //remain registration
                            Intent intent = new Intent(FoodItemActivity.this,RegistrationActivity.class);
                            startActivity(intent);

                        }else{

                            if (jsonObject.has("Data")){

                                // for save json jsonObject in preference
                                PreferenceData.setStringPref(PreferenceData.KEY_USER_JSON,FoodItemActivity.this,jsonObject.getJSONObject("Data").toString());

                            }

                        }
                    }
                }else{
                    Snackbar.make(containerLayout,  "Something went wrong, try again later!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }catch (Exception e) {
                // TODO: handle exception
            }
        }
    }



}
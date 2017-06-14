package vs2.navjivanclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import vs2.navjivanclient.GCM.RegistrationIntentService;
import vs2.navjivanclient.databases.DatabaseFunctions;
import vs2.navjivanclient.fragments.AboutFragment;
import vs2.navjivanclient.fragments.HomeFragment;
import vs2.navjivanclient.fragments.MyOrderFragment;
import vs2.navjivanclient.fragments.ProfileFragment;
import vs2.navjivanclient.models.Const;
import vs2.navjivanclient.objects.User;
import vs2.navjivanclient.preference.PreferenceData;
import vs2.navjivanclient.utils.JSONParser;
import vs2.navjivanclient.utils.Logcat;
import vs2.navjivanclient.utils.Utility;
import vs2.navjivanclient.verifications.VerificationActivity;

public class MainActivity extends BaseActivity
        implements
        NavigationView.OnNavigationItemSelectedListener {

    private int PLAY_SERVICES_RESOLUTION_REQUEST=9000;
    public static final int PICK_ADDRESS = 101;
    public static final int PICK_LOCATION = 555;
    private Tracker mTracker;
    private MenuItem mMenuItem;


    @SuppressWarnings("unused")
    private CharSequence mTitle;

    NavigationView navigationView;
    DrawerLayout mDrawerLayout;
    //Toolbar toolbar;

    private ActionBarDrawerToggle mDrawerToggle;
    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";

    private final Handler mDrawerActionHandler = new Handler();

    private int mNavItemId;
    public static final int VERIFY_CODE = 111;

    //public LayerDrawable cartIcon;
    public static TextView textBadge;

    public String GCMId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new UserLoginAPI().execute("9586042044","eGO96afLqkQ:APA91bHBxdNW9zw7uYuX45fNW435EyaineTSW9gS05HFzgXJ45OpMApgn_oGIWWgevJorDv0zZaQjgz_InxeMGA2-243mxBuni-kPCIQByTE7d8zoQwX58x8p_dx-qiVGc0GoOi8ISnR");

        mTitle = getTitle();
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            View headerView = getLayoutInflater().inflate(R.layout.drawer_header, navigationView, false);
            navigationView.addHeaderView(headerView);
        }


       setActionBarTitle(getResources().getString(R.string.app_name),true);



        if (getIntent().hasExtra("ShowOrder")){
            mNavItemId = R.id.navigation_order;
            navigate(mNavItemId);
        }else{
            // load saved navigation state if present
            if (null == savedInstanceState) {
                mNavItemId = R.id.navigation_home;
                navigate(mNavItemId);
            } else {
                mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
            }

        }
        navigationView.setNavigationItemSelectedListener(this);

        // select the correct nav menu item
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);

        // set up the hamburger icon to open and close the drawer

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        GCMId =PreferenceData.getStringPref(PreferenceData.KEY_GCM_ID,MainActivity.this);
        if (GCMId == null){
            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
                Logcat.e("onCreate", "play service exists");
            }
        }
        sendTracking();



    }

private void sendTracking()
{
    AnalyticsApplication application = (AnalyticsApplication) getApplication();
    mTracker = application.getDefaultTracker();
    mTracker.setScreenName("MainActivity");
    mTracker.send(new HitBuilders.ScreenViewBuilder().build());
}
    private void navigate(final int itemId) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemId) {
            case R.id.navigation_home:
                // react
                fragmentManager.beginTransaction().replace(R.id.container, new HomeFragment())
                        .commit();
                onSectionAttached(1);
                break;
            case R.id.navigation_offer:
                Intent intentOffer = new Intent(MainActivity.this, OfferListActivity.class);
                startActivity(intentOffer);
                break;
            case R.id.navigation_order:
                // react
                if (User.userExist(MainActivity.this)){
                fragmentManager.beginTransaction().replace(R.id.container, new MyOrderFragment())
                        .commit();
                onSectionAttached(2);
            }
                else{
                    if(PreferenceData.getStringPref(PreferenceData.KEY_MOBILE_NUMBER,MainActivity.this)!=null) {
                        String PhoneNumber=PreferenceData.getStringPref(PreferenceData.KEY_MOBILE_NUMBER, MainActivity.this);
                        if (Utility.isOnline(MainActivity.this)){
                            GCMId = PreferenceData.getStringPref(PreferenceData.KEY_GCM_ID,MainActivity.this);
                            new UserLoginAPI().execute(PhoneNumber,GCMId);
                        }else{
                            toast("No internet connection found!",true);
                        }


                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                        startActivityForResult(intent, VERIFY_CODE);
                    }

                }

                break;
            case R.id.navigation_profile:

                if (User.userExist(MainActivity.this)){
                    fragmentManager.beginTransaction().replace(R.id.container, new ProfileFragment())
                            .commit();
                    onSectionAttached(3);
                }else{
                    if(PreferenceData.getStringPref(PreferenceData.KEY_MOBILE_NUMBER,MainActivity.this)!=null) {
                        String PhoneNumber=PreferenceData.getStringPref(PreferenceData.KEY_MOBILE_NUMBER, MainActivity.this);
                        if (Utility.isOnline(MainActivity.this)){
                            GCMId = PreferenceData.getStringPref(PreferenceData.KEY_GCM_ID,MainActivity.this);
                            new UserLoginAPI().execute(PhoneNumber,GCMId);
                        }else{
                            toast("No internet connection found!",true);
                        }


                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                        startActivityForResult(intent, VERIFY_CODE);
                    }

                }

                break;
            case R.id.navigation_about:
                // react
                fragmentManager.beginTransaction().replace(R.id.container, new AboutFragment())
                        .commit();
                onSectionAttached(4);
                break;

        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "Navjivan Home Delivery";
                break;
            case 2:
                mTitle = "My Orders";

                break;
            case 3:
                mTitle = "Profile";
                break;
            case 4:
                mTitle = "About";
                break;
            default:
                mTitle = "Menu";
                break;
        }

        setActionBarTitle(mTitle.toString(),true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMenuItem != null)
            mMenuItem.setChecked(true);

        if (textBadge != null){
            textBadge.setText("" +DatabaseFunctions.getcartItemCount() );
           // Utility.setBadgeCount(this, cartIcon, DatabaseFunctions.getcartItemCount());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cart, menu);
        final MenuItem cartMenu = menu.findItem(R.id.action_cart);

        MenuItemCompat.setActionView(cartMenu, R.layout.layout_badge);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(cartMenu);

        cartMenu.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (User.userExist(MainActivity.this)){
                    int count = DatabaseFunctions.getcartItemCount();
                    if (count > 0){
                        startActivity(new Intent(MainActivity.this,CartActivity.class));
                    }
                    else
                    {
                        toast("Cart is empty! Please select items first.",true);

                    }
                }else{
                    if(PreferenceData.getStringPref(PreferenceData.KEY_MOBILE_NUMBER,MainActivity.this)!=null) {
                        String PhoneNumber=PreferenceData.getStringPref(PreferenceData.KEY_MOBILE_NUMBER, MainActivity.this);
                        if (Utility.isOnline(MainActivity.this)){
                            GCMId = PreferenceData.getStringPref(PreferenceData.KEY_GCM_ID,MainActivity.this);
                            new UserLoginAPI().execute(PhoneNumber,GCMId);
                        }else{
                            toast("No internet connection found!",true);
                        }


                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                        startActivityForResult(intent, VERIFY_CODE);
                    }
                }

            }
        });
        textBadge = (TextView) notifCount.findViewById(R.id.text_badge);
        textBadge.setText(""+ DatabaseFunctions.getcartItemCount());

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            return mDrawerToggle.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == VERIFY_CODE){
                if (resultCode == RESULT_OK){
                    String IsVerified = data.getStringExtra("IsVerified");
                    String PhoneNumber = data.getStringExtra("PhoneNumber");
                    Logcat.e("onActivityResult","IsVerified: "+IsVerified);
                    Logcat.e("onActivityResult","PhoneNumber: "+PhoneNumber);
                    toast(PhoneNumber+" Verified Sucessfully!!",true);
                    if (IsVerified.equals("true")){
                        PreferenceData.setBooleanPref(PreferenceData.KEY_IS_VERIFIED, MainActivity.this, true);
                        PreferenceData.setStringPref(PreferenceData.KEY_MOBILE_NUMBER, MainActivity.this, PhoneNumber);
                        if (Utility.isOnline(MainActivity.this)){
                            GCMId = PreferenceData.getStringPref(PreferenceData.KEY_GCM_ID,MainActivity.this);
                            new UserLoginAPI().execute(PhoneNumber,GCMId);
                        }else{
                            toast("No internet connection found!",true);
                        }

                    }else{
                        PreferenceData.setBooleanPref(PreferenceData.KEY_IS_VERIFIED,MainActivity.this,false);
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
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        // TODO Auto-generated method stub

        FragmentManager fragmentManager = getSupportFragmentManager();
        mNavItemId = menuItem.getItemId();
        if (mNavItemId == R.id.navigation_offer){
            menuItem.setChecked(false);

        }else{
            mMenuItem = menuItem;
            menuItem.setChecked(true);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);

        return true;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("check playservice", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    public class UserLoginAPI extends AsyncTask<String, Integer, JSONObject> {

        SpotsDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pd = new SpotsDialog(MainActivity.this,R.style.CustomDialog);
                pd.setCancelable(false);
                pd.show();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONParser parser = new JSONParser(MainActivity.this);
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
                            PreferenceData.setHotelStatus(MainActivity.this,hotelStatus);
                        }


                        if (status == 0){
                               //remain registration
                            Intent intent = new Intent(MainActivity.this,RegistrationActivity.class);
                            startActivity(intent);

                        }else{
                            if (jsonObject.has("Data")){

                                // for save json jsonObject in preference
                                PreferenceData.setStringPref(PreferenceData.KEY_USER_JSON,MainActivity.this,jsonObject.getJSONObject("Data").toString());

                            }

                        }
                    }
               }
           }catch (Exception e) {
               // TODO: handle exception
           }

        }
    }

}
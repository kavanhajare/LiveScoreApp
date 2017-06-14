package in.vs2.navjeevanadmin;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.special.ResideMenu.ResideMenu;

import in.vs2.navjeevanadmin.fragments.DailyReportFragment;
import in.vs2.navjeevanadmin.fragments.HomeFragment;
import in.vs2.navjeevanadmin.fragments.MonthlyReportFragment;
import in.vs2.navjeevanadmin.fragments.OfferFragment;
import in.vs2.navjeevanadmin.fragments.OrderFragment;
import in.vs2.navjeevanadmin.fragments.PendingOrderFragment;


public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = getTitle();
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        setActionBarTitle(getResources().getString(R.string.app_name),true);



        if (getIntent().hasExtra("ShowOrder")){
            mNavItemId = R.id.navigation_order;
            navigate(mNavItemId);
        }else{
            // load saved navigation state if present
            if (null == savedInstanceState) {
                mNavItemId = R.id.navigation_order;
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


    }


    private void navigate(final int itemId) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemId) {
            case R.id.navigation_home:
                // react
                fragmentManager.beginTransaction().replace(R.id.container, new HomeFragment())
                        .commit();
                setActionBarTitle(getResources().getString(R.string.app_name),true);
                break;
            case R.id.navigation_order:
                // react
                fragmentManager.beginTransaction().replace(R.id.container, new OrderFragment())
                        .commit();
                setActionBarTitle("Todays Orders",true);
                break;
            case R.id.navigation_pending_order:
                // react
                fragmentManager.beginTransaction().replace(R.id.container, new PendingOrderFragment())
                        .commit();
                setActionBarTitle("Pending Orders",true);
                break;
            case R.id.navigation_offers:
                // react
                fragmentManager.beginTransaction().replace(R.id.container, new OfferFragment())
                        .commit();
                setActionBarTitle("Offers",true);
                break;
            case R.id.navigation_daiy_report:
                // react
                fragmentManager.beginTransaction().replace(R.id.container, new DailyReportFragment())
                        .commit();
                setActionBarTitle("Daily Report",true);
                break;
            case R.id.navigation_monthly_report:
                // react
                fragmentManager.beginTransaction().replace(R.id.container, new MonthlyReportFragment())
                        .commit();
                setActionBarTitle("Monthly Report",true);
                break;

            case R.id.navigation_about:
                // react
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
                break;
            case R.id.navigation_setting:
                // react
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "Todays Orders";

                break;
            case 2:
                mTitle = "All Pending Orders";
                break;
            case 3:
                mTitle = "Menu";
                break;
            case 4:
                mTitle = "Daily Report";
                break;
            case 5:
                mTitle = "Monthly Report";
                break;
            default:
                mTitle = "Home";
                break;
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            return mDrawerToggle.onOptionsItemSelected(item);
        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        // TODO Auto-generated method stub
        menuItem.setChecked(true);

        FragmentManager fragmentManager = getSupportFragmentManager();



        mNavItemId = menuItem.getItemId();

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

}
package vs2.navjivanclient;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends BaseActivity  {

    private GoogleMap googleMap; // Might be null if Google Play services APK is not available.

    private static final String TAG = "LocationActivity";

    TextView tvLocation;

    private Button mButtonPickLocaton;
    String address;
    LatLng center;
    Location locNavjivan;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initGlobals();
        showNavjivanOnMap();
        pickLocationButtonSetup();
        sendTracking();

    }

    private void sendTracking()
    {
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Maps Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    private void initGlobals()
    {
        tvLocation = (TextView) findViewById(R.id.textAddress);
        locNavjivan = new Location("");
        locNavjivan.setLongitude(72.921992);
        locNavjivan.setLatitude(20.371246);
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        googleMap = fm.getMap();
        //googleMap.setMapType(GoogleMap.);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                center = googleMap.getCameraPosition().target;
                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(center.latitude, center.longitude, 1);
                    address = addresses.get(0).getAddressLine(0); // If a
                    if (addresses.get(0).getLocality() != null && !address.contains(addresses.get(0).getLocality()))
                        address = address + "," + addresses.get(0).getLocality();
                    if (addresses.get(0).getAdminArea() != null && !address.contains(addresses.get(0).getAdminArea()))
                        address = address + "," + addresses.get(0).getAdminArea();
                    if (addresses.get(0).getCountryName() != null && !address.contains(addresses.get(0).getCountryName()))
                        address = address + "," + addresses.get(0).getCountryName();
                    tvLocation.setText(address.toString());

                    if (!canDeliver(center))
                        Toast.makeText(MapsActivity.this, "Can Not deliver on this address", Toast.LENGTH_LONG).show();

                } catch (Exception e) {

                }

            }
        });
    }
    public void pickLocationButtonSetup() {
        mButtonPickLocaton = (Button) findViewById(R.id.button_pick);
        mButtonPickLocaton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (!canDeliver(center))
                    Toast.makeText(MapsActivity.this, "Can Not deliver on this address", Toast.LENGTH_LONG).show();
                else
                {
                    Intent resIntent= new Intent();
                    resIntent.putExtra("lat",center.latitude);
                    resIntent.putExtra("lng",center.longitude);
                    resIntent.putExtra("address",address);
                    setResult(MainActivity.PICK_LOCATION,resIntent);
                    finish();

                }

            }
        });
    }

    private void showNavjivanOnMap() {

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locNavjivan.getLatitude(),locNavjivan.getLongitude()),
                17));
    }


    private boolean canDeliver(LatLng current) {
        Location locCurrent = new Location("");
        locCurrent.setLatitude(current.latitude);
        locCurrent.setLongitude(current.longitude);
        float distanceInMeters = locCurrent.distanceTo(locNavjivan);
        Log.e("distance in meters", "" + distanceInMeters);
        if (distanceInMeters > 15000)
            return false;
        else
            return true;
    }

    /*
    @Override
    public void onBackPressed() {
       // Toast.makeText(MapsActivity.this,"Pick a Location",Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }
    */
}
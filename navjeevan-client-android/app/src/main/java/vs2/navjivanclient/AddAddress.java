package vs2.navjivanclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import vs2.navjivanclient.adapters.AreaSpinnerAdapter;
import vs2.navjivanclient.models.Const;
import vs2.navjivanclient.objects.Address;
import vs2.navjivanclient.objects.Area;
import vs2.navjivanclient.objects.User;
import vs2.navjivanclient.preference.PreferenceData;
import vs2.navjivanclient.utils.JSONParser;
import vs2.navjivanclient.utils.Logcat;

public class AddAddress extends BaseActivity{

    private EditText mEditTextAddress,mEditTextLandmark;
   // private EditText mEditTextPincode;
   // private Button mButtonPickLocation;
    private Button mButtonSave;
   // public static TextView mTextViewLocation;
   private static double Longitude=72.921992,Latitude = 20.371246;

  //  private final int PICK_LOCATION = 555;

 //   private GoogleMap googleMap;
  //  WorkaroundMapFragment fm;

  //  ScrollView mScrollView;

    private Spinner mPincodeSpinner;
    private String[] spinnerListArea;
    private ArrayList<Area> mAreaList;

 //   FrameLayout layoutMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
       /* fm = (WorkaroundMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        googleMap = fm.getMap();*/
        //fm.getView().setVisibility(View.GONE);
      //  googleMap.getUiSettings().setAllGesturesEnabled(false);
        setActionBarTitle("Add Address", true);

        initGloble();

      //  layoutMap.setVisibility(View.GONE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* if(resultCode==PICK_LOCATION)
        {
            try {
                if (data.hasExtra("address")) {
                    mTextViewLocation.setText(data.getStringExtra("address"));
                }
                if (data.hasExtra("lat")) {
                    Latitude = data.getDoubleExtra("lat", 0);
                }
                if (data.hasExtra("lng")) {
                    Longitude = data.getDoubleExtra("lng", 0);
                }
                LatLng point = new LatLng(Latitude, Longitude);
                //fm.getView().setVisibility(View.VISIBLE);
                layoutMap.setVisibility(View.VISIBLE);
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(point).title(mTextViewLocation.getText().toString()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13));
            }catch (Exception e)
            {
                Log.e("error", "error in reading activity result" + e.toString());
            }
        }*/
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void initGloble(){

       // mScrollView = (ScrollView) findViewById(R.id.scrollView);

      /*  fm.setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });*/

      //  layoutMap = (FrameLayout)findViewById(R.id.layout_map);

        mPincodeSpinner =(Spinner)findViewById(R.id.spin_pincode);

        mEditTextAddress = (EditText)findViewById(R.id.edittext_address);
        mEditTextLandmark = (EditText)findViewById(R.id.edittext_landmark);
     //   mEditTextPincode = (EditText)findViewById(R.id.edittext_pincode);

      //  mButtonPickLocation = (Button)findViewById(R.id.button_pick_location);
        mButtonSave = (Button)findViewById(R.id.button_save);

        //List<String> pincodeList = new ArrayList<String>(Const.areaPincodes.values());
      //  ArrayAdapter<String> adapter =new ArrayAdapter<String>(AddAddress.this,R.layout.spinner_item, pincodeList);

        /*spinnerListArea = Const.getAreaList();
        ArrayAdapter<String> strAdapter = new ArrayAdapter<String>(
                AddAddress.this,
                R.layout.support_simple_spinner_dropdown_item, spinnerListArea);
        strAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        mPincodeSpinner.setAdapter(strAdapter);*/

        mAreaList = new ArrayList<Area>();
        new AreaList().execute();

      //  setSpinnerAdapter(mSpinnerOccupation, spinnerListArea);

      //  mPincodeSpinner.setAdapter(adapter);

      //  mTextViewLocation = (TextView)findViewById(R.id.textview_location_name);

        /*mButtonPickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAddress.this, MapsActivity.class);
                startActivityForResult(intent, PICK_LOCATION);
                //startActivity(intent);
            }
        });*/
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Logcat.e("TAG","AresId: "+mAreaList.get(mPincodeSpinner.getSelectedItemPosition()).getAreaId());
                Logcat.e("TAG","AreaName: "+mAreaList.get(mPincodeSpinner.getSelectedItemPosition()).getAreaName());
                    if (checkEditText(mEditTextAddress)) {
                        if (checkEditText(mEditTextLandmark)) {
                            if (isValidPincode()) {
                                String mobileNumber = PreferenceData.getStringPref(PreferenceData.KEY_MOBILE_NUMBER, AddAddress.this);

                                try {
                                    User u = User.getUserDetailFromPreference(AddAddress.this);
                                    new AddAddressAPI().execute(u.getUserId() + "",
                                            URLEncoder.encode(mEditTextAddress.getText().toString(), "UTF-8"),
                                            URLEncoder.encode(mAreaList.get(mPincodeSpinner.getSelectedItemPosition()).getAreaId()+"", "UTF-8"),
                                            URLEncoder.encode(mEditTextLandmark.getText().toString()+", "+mAreaList.get(mPincodeSpinner.getSelectedItemPosition()).getAreaName(), "UTF-8"),
                                            "" + Longitude,
                                            "" + Latitude);
                                } catch (Exception e) {
                                }
                            }

                        }
                    }

            }
        });
    }

  /*  private String getValueFromKey(String key)
    {
        for (Map.Entry<String, String> entry : Const.areaPincodes.entrySet()) {
            if (entry.getValue().equals(key)) {
                return entry.getKey();
            }
        }
        return "0";
    }*/
  /*  public boolean isPickedLocation() {
        if(Longitude==0) {
            alert("Please pick location!");
            return false;
        }

        if(Latitude==0) {
            alert("Please pick location!");
            return false;
        }
        return true;
    }*/
  public boolean isValidPincode()
  {
      if(mAreaList.get(mPincodeSpinner.getSelectedItemPosition()).getAreaId() == 0)
      {
          //TextView errorText = (TextView) mPincodeSpinner.getSelectedView();
         // errorText.setError("Required");
          //errorText.setTextColor(Color.RED);
          alert("Please select your area");
          return false;
      }
      return true;
  }
    public boolean checkEditText(EditText editText){
        try {
            if (editText.getText().toString().trim().length() > 0 ){
                return  true;
            }
            alert(editText.getHint()+" Required");
            return false;
        }catch (Exception e){
            return false;
        }
    }
    public class AddAddressAPI extends AsyncTask<String, Integer, JSONObject> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pd = new ProgressDialog(AddAddress.this);
                pd.setMessage("Please wait...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.show();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject;
            User user = new User();
            JSONParser parser = new JSONParser(AddAddress.this);
            //name=&mobile=&token=&latitude=&longitude=&address=&pincode=&landmark=&addlongitude=&addlatitude=
            String url = Const.ADD_ADDRESS +"userId="+params[0]+"&address="+params[1]+"&pincode="+params[2]
                    +"&landmark="+params[3]+"&addlongitude="+params[4]+"&addlatitude="+params[5];
            Logcat.e("USER_REGISTER", "url: " + url);

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
                String msg ="";
                if (jsonObject != null){
                    if (jsonObject.has("Status")){
                        status = jsonObject.getInt("Status");
                        msg = jsonObject.getString("Message");
                        if (status == 1){
                            if (jsonObject.has("Data")){
                                PreferenceData.setStringPref(PreferenceData.KEY_USER_JSON, AddAddress.this, jsonObject.getJSONObject("Data").toString());
                                //MainActivity.user = User.getUserDetailsFromJSON(jsonObject.getJSONObject("Data"));

                                User u = User.getUserDetailFromPreference(AddAddress.this);
                                Address newAddress=u.getAddressArrayList().get(0);
                                Intent resIntent= new Intent();
                                resIntent.putExtra("Address", newAddress);
                                setResult(MainActivity.PICK_ADDRESS, resIntent);
                                finish();
                            }
                        }else{
                            alert(msg);
                        }
                    }
                }
            }catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
public void loadAreaInSpinner(ArrayList<Area> list){
    AreaSpinnerAdapter areaSpinnerAdapter = new AreaSpinnerAdapter(
           AddAddress.this, list);
    mPincodeSpinner.setAdapter(areaSpinnerAdapter);
}
    private class AreaList extends
            AsyncTask<Integer, Void, ArrayList<Area>> {

        ProgressDialog pd;
        Context c;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
           try {
               c= AddAddress.this;
               pd = new ProgressDialog(AddAddress.this);
               pd.setMessage("Please wait....");
               pd.setIndeterminate(false);
               pd.setCancelable(false);
               pd.show();

           }catch (Exception e) {
               // TODO: handle exception
           }
        }

        @Override
        protected ArrayList<Area> doInBackground(Integer... params) {
            // TODO Auto-generated method stub
            try {
                return Area.getAreaListFromServer(c);

            } catch (Exception e) {
                // TODO: handle exception
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Area> result) {
            // TODO Auto-generated method stub
            try {
                pd.dismiss();
                if (result != null && result.size() > 0) {
                    mAreaList.clear();
                    mAreaList = result;
                    loadAreaInSpinner(mAreaList);
                } else {
                    Toast.makeText(AddAddress.this, "Areas Not found",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                // TODO: handle exception
                Toast.makeText(AddAddress.this, "Unable to get area list",
                        Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }

}

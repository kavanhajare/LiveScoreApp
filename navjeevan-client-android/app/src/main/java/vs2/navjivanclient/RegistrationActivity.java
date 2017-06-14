package vs2.navjivanclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import vs2.navjivanclient.adapters.AreaSpinnerAdapter;
import vs2.navjivanclient.models.Const;
import vs2.navjivanclient.objects.Area;
import vs2.navjivanclient.objects.User;
import vs2.navjivanclient.preference.PreferenceData;
import vs2.navjivanclient.utils.JSONParser;
import vs2.navjivanclient.utils.Logcat;

public class RegistrationActivity extends BaseActivity {

	private EditText mEditTextYourName,mEditTextAddress,mEditTextLandmark;
	//private EditText mEditTextPincode;
	//private Button mButtonPickLocation;
	private Button mButtonSave;

//	public static TextView mTextViewLocation;
private static double Longitude=72.921992,Latitude = 20.371246;

	private Tracker mTracker;



	//private  GoogleMap googleMap;
	//WorkaroundMapFragment fm;


	TextView textTerms;
	CheckBox checkTerms;

	ScrollView mScrollView;

	private Spinner mPincodeSpinner;
	private String[] spinnerListArea;
	private ArrayList<Area> mAreaList;
    String TAG=RegistrationActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		/*fm = (WorkaroundMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		googleMap = fm.getMap();
		googleMap.getUiSettings().setAllGesturesEnabled(false);
		fm.getView().setVisibility(View.GONE);*/

		setActionBarTitle("Registration", true);

		initGloble();
		sendTracking();
	}
	private void sendTracking()
	{
		AnalyticsApplication application = (AnalyticsApplication) getApplication();
		mTracker = application.getDefaultTracker();
		mTracker.setScreenName("Registration Activity");
		mTracker.send(new HitBuilders.ScreenViewBuilder().build());
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==MainActivity.PICK_LOCATION)
        {
            try {
                if (data.hasExtra("address")) {
                 //   mTextViewLocation.setText(data.getStringExtra("address"));
                }
                if (data.hasExtra("lat")) {
                    Latitude = data.getDoubleExtra("lat", 0);
                }
                if (data.hasExtra("lng")) {
                    Longitude = data.getDoubleExtra("lng", 0);
                }
                LatLng point = new LatLng(Latitude, Longitude);
             //   fm.getView().setVisibility(View.VISIBLE);
            //    googleMap.clear();
            //    googleMap.addMarker(new MarkerOptions().position(point).title(mTextViewLocation.getText().toString()));
            //    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13));
            }catch (Exception e)
            {
                Log.e("error","error in reading activity result"+e.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initGloble(){

		mScrollView = (ScrollView) findViewById(R.id.scrollView);

		/*fm.setListener(new WorkaroundMapFragment.OnTouchListener() {
			@Override
			public void onTouch() {
				mScrollView.requestDisallowInterceptTouchEvent(true);
			}
		});*/
		mPincodeSpinner =(Spinner)findViewById(R.id.spin_pincode);
		mEditTextYourName = (EditText)findViewById(R.id.edittext_yourname);
		mEditTextAddress = (EditText)findViewById(R.id.edittext_address);
		mEditTextLandmark = (EditText)findViewById(R.id.edittext_landmark);
		//mEditTextPincode = (EditText)findViewById(R.id.edittext_pincode);

		//List<String> pincodeList = new ArrayList<String>(Const.areaPincodes.values());
		//ArrayAdapter<String> adapter =new ArrayAdapter<String>(RegistrationActivity.this,R.layout.spinner_item, pincodeList);
		/*spinnerListArea = Const.getAreaList();
		ArrayAdapter<String> strAdapter = new ArrayAdapter<String>(
				RegistrationActivity.this,
				R.layout.support_simple_spinner_dropdown_item, spinnerListArea);
		strAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		mPincodeSpinner.setAdapter(strAdapter);*/
		mAreaList = new ArrayList<Area>();
		new AreaList().execute();

		textTerms = (TextView) findViewById(R.id.text_terms);
		checkTerms = (CheckBox) findViewById(R.id.check_terms);
		textTerms.setText(Html.fromHtml("<a href='http://tinyurl.com/pgnvlf6'>Terms & Conditions</a>"));
		textTerms.setClickable(true);
		textTerms.setMovementMethod(LinkMovementMethod.getInstance());

		//mButtonPickLocation = (Button)findViewById(R.id.button_pick_location);
		mButtonSave = (Button)findViewById(R.id.button_save);

		//mTextViewLocation = (TextView)findViewById(R.id.textview_location_name);

/*		mButtonPickLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegistrationActivity.this, MapsActivity.class);
				startActivityForResult(intent, MainActivity.PICK_LOCATION);
				//startActivity(intent);
			}
		});*/
		mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if (isPickedLocation()) {
                    if (checkEditText(mEditTextYourName)) {
                        if (checkEditText(mEditTextAddress)) {
                            if (checkEditText(mEditTextLandmark)) {
                             //   if (checkEditText(mEditTextPincode)) {
									if(checkTerms.isChecked()){
                                        Log.i(TAG, "mButtonSave: ");
                                        if(isValidPincode()){
											String mobileNumber = PreferenceData.getStringPref(PreferenceData.KEY_MOBILE_NUMBER, RegistrationActivity.this);

											try {
												new UserRegisterAPI().execute(URLEncoder.encode(mEditTextYourName.getText().toString(), "UTF-8") + "",
														URLEncoder.encode(mobileNumber, "UTF-8"),
														"" + PreferenceData.getStringPref(PreferenceData.KEY_GCM_ID,RegistrationActivity.this),
														"" + Latitude,
														"" + Longitude,
														URLEncoder.encode(mEditTextAddress.getText().toString(), "UTF-8"),
														URLEncoder.encode(mAreaList.get(mPincodeSpinner.getSelectedItemPosition()).getAreaId()+"", "UTF-8"),
														URLEncoder.encode(mEditTextLandmark.getText().toString()+", "+mAreaList.get(mPincodeSpinner.getSelectedItemPosition()).getAreaName(), "UTF-8"),
														"" + Longitude,
														"" + Latitude);
												mTracker.send(new HitBuilders.EventBuilder()
														.setCategory("User")
														.setAction("Registration Called")
														.build());
											} catch (Exception e) {
											}
										}
									}else{
										alert("Please check terms & Conditions");
									}

                               // }
                            }
                        }
                    }
               // }
            }
        });
	}
/*	private String getValueFromKey(String key)
	{
		for (Map.Entry<String, String> entry : Const.areaPincodes.entrySet()) {
			if (entry.getValue().equals(key)) {
				return entry.getKey();
			}
		}
		return "0";
	}*/
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
	/*public boolean isPickedLocation() {
		if(Longitude==0) {
            alert("Please pick your location by clicking button!");
            return false;
        }

        if(Latitude==0) {
            alert("Please pick your location by clicking button!");
            return false;
        }
        return true;
	}*/
	public boolean isValidPincode()
	{
		if(mAreaList.get(mPincodeSpinner.getSelectedItemPosition()).getAreaId() == 0)
		{
			//TextView errorText = (TextView) mPincodeSpinner.getSelectedView();
			//errorText.setError("Required");
			//errorText.setTextColor(Color.RED);
			alert("Please select your area");
			return false;
		}
		return true;
	}
	public class UserRegisterAPI extends AsyncTask<String, Integer, JSONObject> {

		SpotsDialog pd;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			try {
				pd = new SpotsDialog(RegistrationActivity.this,R.style.CustomDialog);
				pd.show();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			JSONObject jsonObject;
			User user = new User();
			JSONParser parser = new JSONParser(RegistrationActivity.this);
			//name=&mobile=&token=&latitude=&longitude=&address=&pincode=&landmark=&addlongitude=&addlatitude=
			String url = Const.USER_REGISTER +"name="+params[0]+"&mobile="+params[1]+"&token="+params[2]
					+"&latitude="+params[3]+"&longitude="+params[4]+"&address="+params[5]+"&pincode="+params[6]
					+"&landmark="+params[7]+"&addlongitude="+params[8]+"&addlatitude="+params[9];
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
								mTracker.send(new HitBuilders.EventBuilder()
										.setCategory("User")
										.setAction("Registration Sucessful")
										.build());
								PreferenceData.setStringPref(PreferenceData.KEY_USER_JSON, RegistrationActivity.this, jsonObject.getJSONObject("Data").toString());
								//MainActivity.user = User.getUserDetailsFromJSON(jsonObject.getJSONObject("Data"));
								Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
								startActivity(intent);
								finish();
							}
						}else{
							alert(msg);
							mTracker.send(new HitBuilders.EventBuilder()
									.setCategory("User")
									.setAction("Registration Error:" + msg)
									.build());
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

		//http://tinyurl.com/pgnvlf6
	}

	public void loadAreaInSpinner(ArrayList<Area> list){
		AreaSpinnerAdapter areaSpinnerAdapter = new AreaSpinnerAdapter(
				RegistrationActivity.this, list);
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
				c= RegistrationActivity.this;
				pd = new ProgressDialog(RegistrationActivity.this);
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
					Toast.makeText(RegistrationActivity.this, "Areas Not found",
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(RegistrationActivity.this, "Unable to get area list",
						Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}
	}


}

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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import vs2.navjivanclient.adapters.AreaSpinnerAdapter;
import vs2.navjivanclient.models.Const;
import vs2.navjivanclient.objects.Address;
import vs2.navjivanclient.objects.Area;
import vs2.navjivanclient.objects.User;
import vs2.navjivanclient.preference.PreferenceData;
import vs2.navjivanclient.utils.JSONParser;
import vs2.navjivanclient.utils.Logcat;

public class UpdateAddress extends BaseActivity{

    private EditText mEditTextAddress,mEditTextLandmark;
    private Button mButtonSave;
    private static double Longitude=72.921992,Latitude = 20.371246;
    ScrollView mScrollView;
    private Spinner mPincodeSpinner;
    private String[] spinnerListArea;
    private String mAddressId,mAddress,mLandmark;
    private ArrayList<Area> mAreaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        setActionBarTitle("Update Address", false);
        getBundleData();
        initGloble();
        setValues();
    }

    public void getBundleData(){
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mAddressId = bundle.getString("AddressId");
                mAddress = bundle.getString("Address");
                mLandmark = bundle.getString("Landmark");
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void setValues(){

        mEditTextAddress.setText(""+mAddress);
        mEditTextLandmark.setText(""+mLandmark);

        /*spinnerListArea = Const.getAreaList();
        ArrayAdapter<String> strAdapter = new ArrayAdapter<String>(
                UpdateAddress.this,
                R.layout.support_simple_spinner_dropdown_item, spinnerListArea);
        strAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        mPincodeSpinner.setAdapter(strAdapter);*/

        mAreaList = new ArrayList<Area>();
        new AreaList().execute();

    }

    public void initGloble(){

        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mPincodeSpinner =(Spinner)findViewById(R.id.spin_pincode);
        mEditTextAddress = (EditText)findViewById(R.id.edittext_address);
        mEditTextLandmark = (EditText)findViewById(R.id.edittext_landmark);
        mButtonSave = (Button)findViewById(R.id.button_save);

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (checkEditText(mEditTextAddress)) {
                        if (checkEditText(mEditTextLandmark)) {
                            if (isValidPincode()) {
                                try {
                                    User u = User.getUserDetailFromPreference(UpdateAddress.this);
                                    new UpdateAddressAPI().execute(mAddressId,u.getUserId() + "",
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
    public boolean isValidPincode()
    {
        if(mAreaList.get(mPincodeSpinner.getSelectedItemPosition()).getAreaId() == 0)
        {
           // TextView errorText = (TextView) mPincodeSpinner.getSelectedView();
           // errorText.setError("Required");
           // errorText.setTextColor(Color.RED);
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
    public class UpdateAddressAPI extends AsyncTask<String, Integer, JSONObject> {

        SpotsDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pd = new SpotsDialog(UpdateAddress.this,R.style.CustomDialog);
                pd.show();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject;
            User user = new User();
            JSONParser parser = new JSONParser(UpdateAddress.this);
            String url="";
                url = Const.UPDATE_ADDRESS +"addressId="+params[0]+"&userId="+params[1]+"&address="+params[2]+"&pincode="+params[3]
                        +"&landmark="+params[4]+"&addlongitude="+params[5]+"&addlatitude="+params[6];

            Logcat.e("UpdateAddressAPI", "url: " + url);

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
                                PreferenceData.setStringPref(PreferenceData.KEY_USER_JSON, UpdateAddress.this, jsonObject.getJSONObject("Data").toString());
                               // User u = User.getUserDetailFromPreference(UpdateAddress.this);
                                Address newAddress= new Address(Integer.parseInt(mAddressId),mEditTextAddress.getText().toString(),
                                        mPincodeSpinner.getSelectedItemPosition()+"",mEditTextLandmark.getText().toString() + ", " + mPincodeSpinner.getSelectedItem().toString(),
                                        Longitude+"",Latitude+"");
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
                UpdateAddress.this, list);
        mPincodeSpinner.setAdapter(areaSpinnerAdapter);
    }
    private class AreaList extends
            AsyncTask<Integer, Void, ArrayList<Area>> {

       SpotsDialog pd;
        Context c;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {
                c= UpdateAddress.this;
                pd = new SpotsDialog(UpdateAddress.this);
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
                    Toast.makeText(UpdateAddress.this, "Areas Not found",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                // TODO: handle exception
                Toast.makeText(UpdateAddress.this, "Unable to get area list",
                        Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }

}

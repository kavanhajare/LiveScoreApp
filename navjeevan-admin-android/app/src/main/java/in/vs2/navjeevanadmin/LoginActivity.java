package in.vs2.navjeevanadmin;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONObject;

import in.vs2.navjeevanadmin.GCM.RegistrationIntentService;
import in.vs2.navjeevanadmin.models.Const;
import in.vs2.navjeevanadmin.models.PreferenceData;
import in.vs2.navjeevanadmin.utils.JSONParser;
import in.vs2.navjeevanadmin.utils.Logcat;
import in.vs2.navjeevanadmin.utils.Utility;


public class LoginActivity extends BaseActivity{


    EditText editCode;

    private int PLAY_SERVICES_RESOLUTION_REQUEST=9000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if(PreferenceData.getBooleanPrefs(PreferenceData.LOGIN_STATUS,LoginActivity.this)){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
        setActionBarTitle("Login", false);

       editCode =(EditText) findViewById(R.id.edit_code);
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
            Log.e("oncreate", "play service exists");
        }

    }


    public void onLogin(View v){
        if (Utility.notBlank(editCode,false)){
            if (Utility.isOnline(LoginActivity.this) && RegistrationIntentService.getmGCMKey() !=null){
                new VerifyAdmin().execute(RegistrationIntentService.getmGCMKey(),editCode.getText().toString().trim());
            }else{
                toast("No internet connection found!",true);
            }
        }
    }

    public class VerifyAdmin extends AsyncTask<String,Integer,JSONObject> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONParser parser = new JSONParser(LoginActivity.this);
                //deviceId=&code=
                String params = "&deviceId="+ strings[0]+"&code="+ strings[1];
                String url = Const.VERIFY_ADMIN+params;
                Logcat.e("Login","Url : " + url);
                return  parser.getJSONFromUrl(url,JSONParser.GET,null);

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
                    if (result.has("Status") && !result.isNull("Status")) {
                        int status = result.getInt("Status");
                        if (status == 1){

                            int hotel = result.getInt("HotelStatus");
                            PreferenceData.setBooleanPrefs(PreferenceData.HOTEL_STATUS,LoginActivity.this,(hotel == 1?true:false));
                            PreferenceData.setBooleanPrefs(PreferenceData.LOGIN_STATUS,LoginActivity.this,true);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else{
                            alert(result.getString("Message"));
                        }
                    }else{
                        toast("Something went wrong at server",true);
                    }
                }

            }catch (Exception e){

            }
        }
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

}
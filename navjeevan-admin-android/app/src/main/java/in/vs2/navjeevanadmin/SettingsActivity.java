package in.vs2.navjeevanadmin;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import in.vs2.navjeevanadmin.models.Const;
import in.vs2.navjeevanadmin.models.PreferenceData;
import in.vs2.navjeevanadmin.utils.JSONParser;
import in.vs2.navjeevanadmin.utils.Logcat;
import in.vs2.navjeevanadmin.utils.Utility;


public class SettingsActivity extends BaseActivity{


    //Switch swichUpdate;

    private TextView mTextViewStatus,mTextViewMessage;
    private Button mButtonOpenClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_new);

        setActionBarTitle(getResources().getString(R.string.action_settings),true);
        initViews(savedInstanceState);

    }
    public void setButtonAndStatus(boolean status){
        if (status){
            mTextViewMessage.setText("To close Restaurent please press on Close Restaurent Button");
            mTextViewStatus.setText("Currently Restaurent is Open");
            mTextViewStatus.setTextColor(getResources().getColor(R.color.accent));

            mButtonOpenClose.setText("Close Restaurent");
            mButtonOpenClose.setBackgroundColor(getResources().getColor(R.color.red));
        }else{
            mTextViewMessage.setText("To open Restaurent please press on Open Restaurent Button");
            mTextViewStatus.setText("Currently Restaurent is Close");
            mTextViewStatus.setTextColor(getResources().getColor(R.color.red));

            mButtonOpenClose.setText("Open Restaurent");
            mButtonOpenClose.setBackgroundColor(getResources().getColor(R.color.accent));
        }
    }

    private void initViews(Bundle savedInstanceState){
      //  swichUpdate = (Switch) findViewById(R.id.switch_edit);
        mButtonOpenClose = (Button)findViewById(R.id.button_open_close);
        mTextViewStatus = (TextView)findViewById(R.id.textview_status);
        mTextViewMessage = (TextView)findViewById(R.id.textview_message);
        setButtonAndStatus(PreferenceData.getBooleanPrefs(PreferenceData.HOTEL_STATUS,SettingsActivity.this));
      //  swichUpdate.setChecked(PreferenceData.getBooleanPrefs(PreferenceData.HOTEL_STATUS,SettingsActivity.this));

        mButtonOpenClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isOnline(SettingsActivity.this)){
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = df.format(c.getTime());
                    if (PreferenceData.getBooleanPrefs(PreferenceData.HOTEL_STATUS,SettingsActivity.this)){
                        new UpdateHotelStatus(false).execute(formattedDate);
                    }else{
                        new UpdateHotelStatus(true).execute(formattedDate);
                    }
                }else{
                    toast("No internet connection found!",true);
                }
            }
        });

        /*swichUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isOnline(SettingsActivity.this)){
                    new UpdateHotelStatus(swichUpdate.isChecked()).execute();
                }else{
                    swichUpdate.setChecked(!swichUpdate.isChecked());
                    toast("No internet connection found!",true);
                }
            }
        });*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class UpdateHotelStatus extends AsyncTask<String,Integer,JSONObject> {

        SpotsDialog pd;
        boolean hotelStatus;

        public UpdateHotelStatus(boolean status){
            this.hotelStatus = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new SpotsDialog(SettingsActivity.this,R.style.CustomDialog);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONParser parser = new JSONParser(SettingsActivity.this);
                //&status=1

                String url = Const.UPDATE_HOTEL_STATUS+ "&status="+ (this.hotelStatus?1:0) +"&Date="+strings[0];
                Logcat.e("TAG","URL : "+url);
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
                            PreferenceData.setBooleanPrefs(PreferenceData.HOTEL_STATUS, SettingsActivity.this, hotelStatus);
                            //swichUpdate.setChecked(hotelStatus);
                            setButtonAndStatus(hotelStatus);

                        }else{
                            alert(result.getString("Message"));
                            setButtonAndStatus(!hotelStatus);
                           // swichUpdate.setChecked(!hotelStatus);
                        }
                    }else{
                        toast("Something went wrong at server",true);
                        setButtonAndStatus(!hotelStatus);
                        //swichUpdate.setChecked(!hotelStatus);
                    }
                }

            }catch (Exception e){

            }
        }
    }
}
package vs2.navjivanclient.verifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.sinch.verification.Config;
import com.sinch.verification.SinchVerification;
import com.sinch.verification.Verification;
import com.sinch.verification.VerificationListener;

import vs2.navjivanclient.AnalyticsApplication;
import vs2.navjivanclient.BaseActivity;
import vs2.navjivanclient.R;
import vs2.navjivanclient.utils.Logcat;

public class VerificationActivity extends BaseActivity {

    private EditText mEditTextPhoneNumber,mEditTextCode;
    private Button mButtonOk,mButtonVerify;
    private LinearLayout mLayoutSendVerification,mLayoutVerify;
    //private final String APPLICATION_KEY = "7ecf0a90-5176-43f6-ab95-58f4ea9bcd19";
    private final String APPLICATION_KEY = "9318f57c-77ed-4015-b6ea-e8c94a67177c";
    private VerificationListener mVerificationListener;
    private Verification mVerification;
    private ProgressBar mProgressBar,mProgressBar2;
    private boolean isManualVarifiactionDone=false;

    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setActionBarTitle("Verification", true);
        initGloble();
        sendTracking();
    }
    private void sendTracking()
    {
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Verification Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    public void initGloble(){

        mEditTextPhoneNumber = (EditText)findViewById(R.id.edittext_phoneNo);
        mEditTextCode = (EditText)findViewById(R.id.edittext_code);
        mButtonVerify = (Button)findViewById(R.id.button_verify);
        mButtonOk = (Button)findViewById(R.id.button_ok);
        mLayoutSendVerification = (LinearLayout)findViewById(R.id.layout_sendVerification);
        mLayoutVerify = (LinearLayout)findViewById(R.id.layout_onVerification);
        mLayoutSendVerification.setVisibility(View.VISIBLE);
        mLayoutVerify.setVisibility(View.GONE);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mProgressBar2 = (ProgressBar)findViewById(R.id.progressBarV);
        mProgressBar.setVisibility(View.GONE);
        mProgressBar2.setVisibility(View.GONE);

        mButtonVerify.setVisibility(View.VISIBLE);
        mButtonOk.setVisibility(View.VISIBLE);

        setOnClickListener();

        mVerificationListener = new VerificationListener() {
            @Override
            public void onInitiated() {
                Logcat.e("VerificationListener", "Verifying...");

                mEditTextPhoneNumber.setEnabled(false);


            }

            @Override
            public void onInitiationFailed(Exception e) {
                Logcat.e("VerificationListener","Initialization failed : "+e.toString());
                mProgressBar.setVisibility(View.GONE);
                mButtonOk.setVisibility(View.VISIBLE);
                showAlertDailog("Please provide valid Phone number");

            }

            @Override
            public void onVerified() {
                Logcat.e("VerificationListener", "Phone verified!");
                backMainActivity(mEditTextPhoneNumber.getText().toString(), true);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("User")
                        .setAction("Verification Sucessfull")
                        .build());
            }

            @Override
            public void onVerificationFailed(Exception e) {
                Logcat.e("VerificationListener", "Phone verification failed : " + e.toString());
                mEditTextPhoneNumber.setEnabled(true);

                if(isManualVarifiactionDone) {
                    alert("Verification Failed. Try again.");
                    initGloble();
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("User")
                            .setAction("Verification Failed")
                            .build());
                }
                else
                {
                    mLayoutSendVerification.setVisibility(View.GONE);
                    mLayoutVerify.setVisibility(View.VISIBLE);
                }
            }
        };

    }
    public void setOnClickListener(){
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendVerification();

            }
        });
        mButtonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVerify();
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("User")
                        .setAction("Manual Verification Shown")
                        .build());
            }
        });
    }

    public void showAlertDailogForBack(final String message,final String PhoneNumber,final boolean isVerified){
        AlertDialog.Builder bld = new AlertDialog.Builder(VerificationActivity.this);
        bld.setMessage(message);
        bld.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // TODO Auto-generated method stub
                        backMainActivity(PhoneNumber,isVerified);
                    }
                });
        bld.create().show();
    }
    public void showAlertDailog(final String message){
        AlertDialog.Builder bld = new AlertDialog.Builder(VerificationActivity.this);
        bld.setMessage(message);
        bld.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // TODO Auto-generated method stub


                    }
                });
        bld.create().show();
    }
    public void backMainActivity(final String phoneNumber,final boolean verified) {
        try {
            Logcat.e("backMainActivity", "backMainActivity");
            Intent returnIntent = new Intent();
            returnIntent.putExtra("IsVerified", ""+verified);
            returnIntent.putExtra("PhoneNumber", ""+phoneNumber);
            setResult(RESULT_OK, returnIntent);
            finish();
        } catch (Exception e) {
            // TODO: handle exception
            Logcat.e("backMainActivity error", "" + e.toString());
        }

    }
    public void  onSendVerification(){
        if (mEditTextPhoneNumber.getText().length() > 0){
            mProgressBar.setVisibility(View.VISIBLE);
            mButtonOk.setVisibility(View.GONE);
            Config config = SinchVerification.config().applicationKey(APPLICATION_KEY).context(getApplicationContext()).build();
            mVerification = SinchVerification.createSmsVerification(config, "+91"+mEditTextPhoneNumber.getText().toString(), mVerificationListener);

            mVerification.initiate();
            isManualVarifiactionDone=false;

            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Verification")
                    .setAction(mEditTextPhoneNumber.getText().toString())
                    .build());

        }else {
            Toast.makeText(VerificationActivity.this, "Please add mobile number", Toast.LENGTH_SHORT).show();
        }

    }


    public void onVerify(){
        if (mVerification != null){
            Logcat.e("onVerify","mVerification not null");

            if (mEditTextCode.getText().length() > 0){
                mVerification.verify(mEditTextCode.getText().toString());
                mButtonVerify.setVisibility(View.GONE);
                mProgressBar2.setVisibility(View.VISIBLE);
                isManualVarifiactionDone=true;
            }else {
                Toast.makeText(VerificationActivity.this, "Please enter your verification code", Toast.LENGTH_SHORT).show();
            }
        }else{
            Logcat.e("onVerify","mVerification null");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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

}
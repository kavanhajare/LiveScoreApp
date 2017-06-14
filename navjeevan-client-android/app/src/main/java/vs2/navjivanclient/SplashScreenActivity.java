package vs2.navjivanclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import vs2.navjivanclient.databases.DatabaseFunctions;
import vs2.navjivanclient.preference.PreferenceData;

public class SplashScreenActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		DatabaseFunctions.openDB(SplashScreenActivity.this);

		if(PreferenceData.isSplashShown(SplashScreenActivity.this)){
			sendtoActivity();
		}else{
			PreferenceData.setSplashShown(SplashScreenActivity.this,true);
			runThread();
		}

	}

	public void runThread(){
		try {
			Thread sthread = new Thread() {
				@Override
				public void run() {
					try {
						int waited = 0;
						while (waited <= 3000) {
							sleep(100);
							waited += 100;
						}
					} catch (InterruptedException e) {

					} finally {
						runOnUiThread(new Runnable() {
							public void run() {

								sendtoActivity();
							}
						});
					}
				}
			};
			sthread.start();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("runThread", "Exception: " + e.toString());
		}
	}
	
	public void sendtoActivity(){
		Intent intentHome = new Intent(SplashScreenActivity.this, MainActivity.class);
		startActivity(intentHome);
		finish();
	}
	

}

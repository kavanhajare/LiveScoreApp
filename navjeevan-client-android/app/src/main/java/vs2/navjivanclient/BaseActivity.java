package vs2.navjivanclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// opening transition animations
		overridePendingTransition(R.anim.activity_open_translate,
				R.anim.activity_close_scale);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// closing transition animations
		overridePendingTransition(R.anim.activity_open_scale,
				R.anim.activity_close_translate);
	}

	public void toast(String message, boolean isLong) {
		Toast.makeText(this, message,
				isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
	}

	public void alert(String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(this);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		bld.create().show();
	}

	public void setActionBarTitle(String title) {

		setActionBarTitle(title, false);

	}
	
	public void setActionBarTitle(String title,String subTitle) {

		setActionBarTitle(title,subTitle, false);

	}

	@SuppressLint("InflateParams")
	public void setActionBarTitle(String title, boolean showHome) {
		// Customize the ActionBar
		final ActionBar abar = getSupportActionBar();

		View viewActionBar = getLayoutInflater().inflate(
				R.layout.layout_actionbar, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				// Center the textview in the ActionBar !
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		TextView textviewTitle = (TextView) viewActionBar
				.findViewById(R.id.actionbar_textview);
		textviewTitle.setText(title);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		if (showHome) {
			//abar.setIcon(R.drawable.arrow);
			abar.setDisplayHomeAsUpEnabled(true);
			abar.setHomeButtonEnabled(true);
		}

	}
	
	@SuppressLint("InflateParams")
	public void setActionBarTitle(String title,String subtitle, boolean showHome) {
		// Customize the ActionBar
		final ActionBar abar = getSupportActionBar();

		View viewActionBar = getLayoutInflater().inflate(
				R.layout.layout_actionbar_detail, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				// Center the textview in the ActionBar !
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		
		TextView textviewTitle = (TextView) viewActionBar
				.findViewById(R.id.actionbar_textview);
		
		TextView textviewSubTitle = (TextView) viewActionBar
				.findViewById(R.id.actionbar_textdetail);
		textviewTitle.setText(title);
		textviewSubTitle.setText(subtitle);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		if (showHome) {
			abar.setDisplayHomeAsUpEnabled(true);
			abar.setHomeButtonEnabled(true);
		}

	}

	/*
	@SuppressLint("InflateParams")
	public void setActionBarTitleImage(boolean showHome) {
		// Customize the ActionBar
		final ActionBar abar = getSupportActionBar();

		View viewActionBar = getLayoutInflater().inflate(
				R.layout.layout_image_actionbar, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				// Center the textview in the ActionBar !
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		if (showHome) {
			abar.setDisplayHomeAsUpEnabled(true);
			abar.setHomeButtonEnabled(true);
		}

	}
	*/

}

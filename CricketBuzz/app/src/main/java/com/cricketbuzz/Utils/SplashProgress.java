package com.cricketbuzz.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cricketbuzz.R;
import com.wang.avi.AVLoadingIndicatorView;


public class SplashProgress extends Dialog {
	private Context context;
	private ImageView imgLogo;
    AVLoadingIndicatorView loadingIndicatorView;
	public SplashProgress(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_dialog);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         loadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
	}

	@Override
	protected void onStart() {
    loadingIndicatorView.show();
	}

	@Override
	protected void onStop() {
		loadingIndicatorView.hide();
	}
}

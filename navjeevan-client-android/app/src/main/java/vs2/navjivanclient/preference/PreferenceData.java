package vs2.navjivanclient.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceData {
	

	public static final String PREFS_SETTINGS = "Navjeevan_Setting";
	public static final String KEY_IS_VERIFIED = "IsVerified";
	public static final String KEY_USER_JSON = "UserJson";
	public static final String KEY_MOBILE_NUMBER = "MobileNumber";

	public static final String KEY_HOTEL_STATUS = "HotelStatus";

	public static final String KEY_SPLASH_SHOWN = "SplashScreenShown";

	public static final String KEY_GCM_ID = "GCM";

	public static void clearPreference(Context context){
		SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_SETTINGS, 0);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.clear();
		editor.commit();
	}
	
	public static void setBooleanPref(String prefKey,Context context,boolean status){
		SharedPreferences settings = context.getSharedPreferences(PREFS_SETTINGS, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putBoolean(prefKey, status);
	      editor.commit();
	}
	
	public static boolean getBooleanPref(String prefKey,Context context){
		SharedPreferences settings = context.getSharedPreferences(PREFS_SETTINGS, 0);
	       return settings.getBoolean(prefKey, false);
	}
	
	public static void setIntPref(String prefKey,Context context,int value){
		SharedPreferences settings = context.getSharedPreferences(PREFS_SETTINGS, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putInt(prefKey, value);
	      editor.commit();
	}
	
	public static int getIntPref(String prefKey,Context context){
		SharedPreferences settings = context.getSharedPreferences(PREFS_SETTINGS, 0);
	    return settings.getInt(prefKey, 0);
	}
	
	public static void setStringPref(String prefKey,Context context,String value){
		SharedPreferences settings = context.getSharedPreferences(PREFS_SETTINGS, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString(prefKey, value);
	      editor.commit();
	}

	
	public static String getStringPref(String prefKey,Context context){
		SharedPreferences settings = context.getSharedPreferences(PREFS_SETTINGS, 0);
	    return settings.getString(prefKey,null);
	}


	public static int getHotelStatus(Context context){
		SharedPreferences settings = context.getSharedPreferences(PREFS_SETTINGS, 0);
		return settings.getInt(KEY_HOTEL_STATUS, 0);
	}

	public static void setHotelStatus(Context context,int value){
		SharedPreferences settings = context.getSharedPreferences(PREFS_SETTINGS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(KEY_HOTEL_STATUS, value);
		editor.commit();
	}


	public static void setSplashShown(Context context,boolean status){
		SharedPreferences settings = context.getSharedPreferences(PREFS_SETTINGS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(KEY_SPLASH_SHOWN, status);
		editor.commit();
	}

	public static boolean isSplashShown(Context context){
		SharedPreferences settings = context.getSharedPreferences(PREFS_SETTINGS, 0);
		return settings.getBoolean(KEY_SPLASH_SHOWN, false);
	}

}

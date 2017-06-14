package in.vs2.navjeevanadmin.models;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceData {

	public static final String PREFS_SETTINGS = "NavjeevanAdmin";
	public static final String PREFS_GCM_SETTINGS = "NavjeevanAdmin_GCM";
	
	public static final String GCM_ID = "GcmId";

	public static final String LOGIN_STATUS = "LoginStatus";

	public static final String HOTEL_STATUS = "HotelStatus";
	
	
	public static void clearPreference(Context context){
		SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_SETTINGS, 0);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.clear();
		editor.commit();
	}
	
	public static void clearGCMPreference(Context context){
		SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_GCM_SETTINGS, 0);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.clear();
		editor.commit();
	}

	public static void setStringPrefs(String prefKey, Context context,
			String Value) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_SETTINGS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(prefKey, Value);
		editor.commit();
	}

	public static String getStringPrefs(String prefKey, Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_SETTINGS, 0);
		return settings.getString(prefKey, null);
	}

	public static void setBooleanPrefs(String prefKey, Context context,
			Boolean value) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_SETTINGS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(prefKey, value);
		editor.commit();
	}

	public static boolean getBooleanPrefs(String prefKey, Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_SETTINGS, 0);
		return settings.getBoolean(prefKey, false);
	}

	public static boolean getEnableBooleanPrefs(String prefKey, Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_SETTINGS, 0);
		return settings.getBoolean(prefKey, true);
	}

	public static void setLongPref(String prefKey, Context context,
			long noOfResult) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_SETTINGS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(prefKey, noOfResult);
		editor.commit();
	}

	public static long getLongPref(String prefKey, Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_SETTINGS, 0);
		return settings.getLong(prefKey, 0);
	}

	public static void setIntPref(String prefKey, Context context,
			int noOfResult) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_SETTINGS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(prefKey, noOfResult);
		editor.commit();
	}

	public static int getIntPref(String prefKey, Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_SETTINGS, 0);
		return settings.getInt(prefKey, 0);
	}
	
	public static void setGcmId(Context context,String gcmId){
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_GCM_SETTINGS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(GCM_ID, gcmId);
		editor.commit();
	
	}
	public static String getGcmId(Context context){
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_GCM_SETTINGS, 0);
		return settings.getString(GCM_ID, null);
	}
	
	

}

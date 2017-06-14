package in.vs2.navjeevanadmin.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.provider.Settings.Secure;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class Utility {

	/**
	 * 
	 * @param edit
	 *            : EditText to validate
	 * @param validateEmail
	 *            : if true validate email also
	 * @return true/false
	 */
	public static boolean notBlank(EditText edit, boolean validateEmail) {
		if (edit.getText().length() > 0) {
			if (validateEmail) {
				if (!Utility.isValidEmail(edit.getText().toString())) {
					edit.setError("Not valid email!");
					return false;
				}
			}
			edit.setError(null);
			return true;

		} else {
			edit.setError("Required " + edit.getHint());
			return false;
		}
	}

	/**
	 * 
	 * @param target
	 *            : send email to validate
	 * @return true/false
	 */
	public static boolean isValidEmail(CharSequence target) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}

	/**
	 * 
	 * @param context
	 * @return true/false Check Internet connection available
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return (cm == null || cm.getActiveNetworkInfo() == null) ? false : cm
				.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	public static void getHashKey(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					"com.vs2.viacontacts", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Logcat.e("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}

	public static void getFacebookHash(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					"com.vanitydating", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.e("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}

	/**
	 * Round to certain number of decimals
	 * 
	 * @param d
	 * @param decimalPlace
	 * @return
	 */
	public static float round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	/**
	 * Generate random numbersNeeded from max number
	 * 
	 * @param numbersNeeded
	 * @param max
	 */
	public static List<Integer> getRandomNumbers(int numbersNeeded, int max) {

		Random rng = new Random(); // Ideally just create one instance globally
		// Note: use LinkedHashSet to maintain insertion order
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < numbersNeeded) {
			Integer next = rng.nextInt(max) + 1;
			// As we're adding to a set, this will automatically do a
			// containment check
			generated.add(next);
		}
		Logcat.e("Random numbers", generated.toString());

		List<Integer> randomNumbers = new ArrayList<Integer>();
		Iterator<Integer> numberIterator = generated.iterator();
		while (numberIterator.hasNext()) {
			randomNumbers.add(numberIterator.next());
		}
		Collections.sort(randomNumbers);

		return randomNumbers;
	}

	/**
	 * Check if application exist
	 * 
	 * @param targetPackage
	 * @return
	 */
	public static boolean isAppInstalled(String targetPackage, Context context) {
		List<ApplicationInfo> packages;
		PackageManager pm = context.getPackageManager();
		packages = pm.getInstalledApplications(0);
		for (ApplicationInfo packageInfo : packages) {
			if (packageInfo.packageName.equals(targetPackage))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
		String deviceId = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);

		return deviceId;
	}

	public static int dpToPx(int dp, Context context) {
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		int px = Math.round(dp
				* (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}

	public static int pxToDp(int px, Context context) {
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		int dp = Math.round(px
				/ (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return dp;
	}

	public static String getCurrentTime() {
		long date = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZ",
				Locale.getDefault());
		return sdf.format(date);
	}

	public static long getMillisec(String date) {
		// yyyy-MM-dd'T'HH:mm:ssZZZZ
		try {
			// 2015-05-29 05:23:55 +0000
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss ZZZZ", Locale.getDefault());
			cal.setTime(format.parse(date));
			return cal.getTimeInMillis();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Logcat.e("Date", "Error : " + e.toString());
		}
		return 0;
	}

	@SuppressLint("SimpleDateFormat")
	public static String toDate(long timestamp, String format) {
		Date date = new Date(timestamp * 1000);
		return new SimpleDateFormat(format).format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getChatDate(long time) {
		SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss.SSS");
		DecimalFormat DF = new DecimalFormat("000");
		return SDF.format(time / 1000) + DF.format(time % 1000);
	}

	
	public static int toInt(String number) {
		try {
			int n = Integer.parseInt(number);
			return n; // Converted successfully!
		} catch (NumberFormatException e) {
			return -1; // There was an error
		}
	}

	public static boolean deleteFile(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			if (files == null) {
				return true;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteFile(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	public static File getFileFromBitmap(Context c, Bitmap bm) {

		try

		{

			File f = new File(c.getCacheDir(), "temp.temp");

			f.createNewFile();

			// Convert bitmap to byte array

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			bm.compress(CompressFormat.PNG, 0, bos);

			byte[] bitmapdata = bos.toByteArray();

			// write the bytes in file

			FileOutputStream fos = new FileOutputStream(f);

			fos.write(bitmapdata);

			fos.flush();

			fos.close();

			return f;

		} catch (Exception e)

		{

			return null;

		}

	}

	public static Bitmap getResizedBitmap(Bitmap bitmap, int width, int height) {

		try {

			return Bitmap.createScaledBitmap(bitmap, width, height, true);

		} catch (Exception e) {

			// TODO: handle exception

			Log.e("GetResizedBitmap", "Exception: " + e.toString());

			return null;

		}

	}

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK)
				>= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

}
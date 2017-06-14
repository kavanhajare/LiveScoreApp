package in.vs2.navjeevanadmin.utils;

import android.util.Log;

public class Logcat{

	/*
	 * ALLOW_LOG to enable or disable debugging log
	 */
	private static final boolean ALLOW_LOG = true;

	
	/*
	 * @since API level 1
	 * 
	 * Send an DEBUG log message.
	 */
	public static void e(String tag, String message){
		if(ALLOW_LOG){
			Log.e(tag, message);
		}
	}
	
	/*
	 * @since API level 1
	 * 
	 * Send an DEBUG log message.
	 * 
	 */
	
	public static void d(String tag, String message){
		if(ALLOW_LOG){
			Log.d(tag, message);
		}
	}
	
	/*
	 * @since API level 1
	 * 
	 * Send an VERBOSE log message.
	 */
	public static void v(String tag, String message){
		if(ALLOW_LOG){
			Log.v(tag, message);
		}
	}
	
	
	/*
	 * @since API level 1
	 * 
	 * Send an INFO log message.
	 */
	public static void i(String tag, String message){
		if(ALLOW_LOG){
			Log.i(tag, message);
		}
	}
	
	/*
	 * @since API level 1
	 * 
	 * Send a WARN log message.
	 */
	public static void w(String tag, String message){
		if(ALLOW_LOG){
			Log.w(tag, message);
		}
	}
	

	/*
	 * @since API level 8 
	 * 
	 * What a Terrible Failure: Report a condition that should never happen.
	 * The error will always be logged at level ASSERT with the call stack.
	 * Depending on system configuration, a report may be added to the DropBoxManager
	 * and/or the process may be terminated immediately with an error dialog.
	 */
	public static void wtf(String tag, String message){
		if(ALLOW_LOG){
			Log.wtf(tag, message);
		}
	}
}

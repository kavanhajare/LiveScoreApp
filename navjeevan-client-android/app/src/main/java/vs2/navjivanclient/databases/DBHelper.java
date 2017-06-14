package vs2.navjivanclient.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import vs2.navjivanclient.utils.Logcat;

public class DBHelper extends SQLiteOpenHelper {

	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "NavjeevanClientDB";

	public static final String TABLE_CART = "CartMaster";
	public static final String NAME = "Name";
	public static final String STATUS = "Status";
	public static final String MENU_ID = "menu_id";
	public static final String DESCRIPTION = "description";
	public static final String CATEGORY_ID = "category_id";
	public static final String PRICE = "price";
	public static final String IMAGE_URL = "imageUrl";
	public static final String QUANTITY = "quantity";

	public static final String tag = DBHelper.class.getSimpleName();

	private Context mContext;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		version1Create(db);
		Log.e(tag, "Database created successfully!");
	}

	private void version1Create(SQLiteDatabase db) {

		String createContact = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_CART + "("
				+ MENU_ID + " INTEGER,"
				+ CATEGORY_ID + " INTEGER,"
				+ NAME + " VARCHAR,"
				+ DESCRIPTION + " VARCHAR,"
				+ STATUS + " INTEGER,"
				+ QUANTITY + " INTEGER,"
				+ PRICE + " REAL,"
				+ IMAGE_URL + " VARCHAR);";

		db.execSQL(createContact);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		switch (newVersion) {
		case 1:
			version1Create(db);
			Logcat.e(DB_NAME + " UPGRADE", "Database updated to version " + 1);
		}

		Log.e(tag, "Database updated successfully!");
	}

}

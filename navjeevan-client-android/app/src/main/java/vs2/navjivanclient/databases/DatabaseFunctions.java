package vs2.navjivanclient.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import vs2.navjivanclient.models.FoodCart;
import vs2.navjivanclient.models.FoodItem;
import vs2.navjivanclient.utils.Logcat;

public class DatabaseFunctions {

	private static final String tag = DatabaseFunctions.class.getSimpleName();
	static SQLiteDatabase db;
	static DBHelper dbHelper;

	public static void addToCart(FoodCart cart) {

		if (itemExists(cart.getItem().getMenuId())){
			updateQuantity(cart.getItem().getMenuId(),cart.getQuantity());
			return;
		}

		ContentValues values;
		try {
			values = new ContentValues();
			values.put(DBHelper.MENU_ID, cart.getItem().getMenuId());
			values.put(DBHelper.NAME, cart.getItem().getName());
			values.put(DBHelper.STATUS, cart.getItem().getStatus());
			values.put(DBHelper.DESCRIPTION,cart.getItem().getDescription());
			values.put(DBHelper.CATEGORY_ID, cart.getItem().getCategoryId());
			values.put(DBHelper.PRICE, cart.getItem().getPrice());
			values.put(DBHelper.QUANTITY, cart.getQuantity());
			values.put(DBHelper.IMAGE_URL,cart.getItem().getImageUrl());

			db.insert(DBHelper.TABLE_CART, null, values);
		} catch (Exception e) {
			Logcat.e(tag, "addContactEntry error : " + e.toString());
		}
	}

	private static boolean itemExists(int menuId) {
		// TODO Auto-generated method stub
		Cursor cursor = null;
		try {
			cursor = db.rawQuery("SELECT * FROM "
					+ DBHelper.TABLE_CART + " WHERE "
					+ DBHelper.MENU_ID + "=" + menuId, null);
			if (cursor != null && cursor.getCount() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Logcat.e(tag, "itemExists Error : " + e.toString());
			return false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static boolean updateQuantity(int menuId,int quantity) {

		ContentValues values;
		try {
			values = new ContentValues();
			values.put(DBHelper.QUANTITY, quantity);
			db.update(DBHelper.TABLE_CART, values, DBHelper.MENU_ID + "=?", new String[]{menuId+""});
			return true;
		} catch (Exception e) {
			Logcat.e(tag, "updateQuantity error : " + e.toString());
			return false;
		}

	}

	public static int getQuantity(int menuId) {

		int quantity = 0;
		Cursor cursor = null;

		try {
			cursor = db
					.rawQuery(
							"SELECT "
									+ DBHelper.QUANTITY + " FROM "+ DBHelper.TABLE_CART
									+ " WHERE " + DBHelper.MENU_ID + "="+menuId, null);

			while (cursor.moveToNext()) {
				quantity = cursor.getInt(0);
				return quantity;
			}
			return quantity;

		} catch (Exception e) {
			Logcat.e("getQuantity", "error : " + e.toString());
			return quantity;
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}
	}

	public static int getcartItemCount() {

		int count = 0;
		Cursor cursor = null;

		try {
			String countQuery = "SELECT  TOTAL("+DBHelper.QUANTITY+") as qty FROM " + DBHelper.TABLE_CART;
			cursor = db.rawQuery(countQuery, null);
            cursor.moveToFirst();
			count = cursor.getInt(0);

			return count;

		} catch (Exception e) {
			Logcat.e("getcartItemCount", "error : " + e.toString());
			return count;
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}

	}

	public static void deleteCart() {
		try {
			int count = db.delete(DBHelper.TABLE_CART, "1", null);
			Logcat.e(tag, count + " rows deleted");
		} catch (Exception e) {
			Logcat.e(tag, "deleteCart Exception : " + e.toString());
		}
	}

	public static boolean deleteMenu(int menuId) {
		try {
			db.execSQL("DELETE FROM " + DBHelper.TABLE_CART + " WHERE "+DBHelper.MENU_ID + "=" + menuId);
			Log.e(tag, "deleteMenu : Record deleted!");
			return true;
		} catch (Exception e) {
			Log.e(tag, "deleteMenu Exception : " + e.toString());
			return false;
		}
	}

	public static ArrayList<FoodCart> getCartItems() {
		Cursor cursor = null;
		ArrayList<FoodCart> cartItems = null;
		try {
			cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_CART, null);
			if (cursor != null) {
				cartItems = new ArrayList<FoodCart>();
				while (cursor.moveToNext()) {

					/*
					+ MENU_ID + " INTEGER,"
				+ CATEGORY_ID + " INTEGER,"
				+ NAME + " VARCHAR,"
				+ DESCRIPTION + " VARCHAR,"
				+ STATUS + " INTEGER,"
				+ QUANTITY + " INTEGER,"
				+ PRICE + " REAL,"
				+ IMAGE_URL + " VARCHAR)
					 */
					FoodItem item = new FoodItem();
					item.setMenuId(cursor.getInt(0));
					item.setCategoryId(cursor.getInt(1));
					item.setName(cursor.getString(2));
					item.setDescription(cursor.getString(3));
					item.setStatus(cursor.getInt(4));
					item.setPrice(cursor.getDouble(6));
					item.setImageUrl(cursor.getString(7));

					FoodCart cart = new FoodCart(item,cursor.getInt(5));

					cartItems.add(cart);
				}
				return cartItems;
			} else {
				return cartItems;
			}
		} catch (Exception e) {
			Logcat.e(tag, "getCartItems Error : " + e.toString());
			return cartItems;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static void openDB(Context context) {
		dbHelper = new DBHelper(context);
		if (db != null) {
			if (!db.isOpen()) {
				db = dbHelper.getWritableDatabase();
			}
		} else {
			db = dbHelper.getWritableDatabase();
		}
	}

	public static void closeDB() {
		if (db.isOpen()) {
			db.close();
			dbHelper.close();
		}
	}
}

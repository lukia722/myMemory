package nz.cchang.myandroidtuorial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ItemDAO {
	

	public static final String TABLE_NAME = "item";
	

	public static final String KEY_ID = "_id";
	
	
	public static final String DATETIME_COLUMN = "datetime";
	public static final String IT_DATE_COLUMN = "it_date";
	public static final String TIME_COLUMN = "time";
	public static final String COLOR_COLUMN = "color";
	public static final String TITLE_COLUMN = "title";
	public static final String CONTENT_COLUMN = "content";
	public static final String FILENAME_COLUMN = "filename";
	public static final String LATITUDE_COLUMN = "latitude";
	public static final String LONGITUDE_COLUMN = "longitude";
	public static final String LASTMODIFY_COLUMN = "lastmodify";
	public static final String LOCATION_COLUMN = "location";
	public static final String PEOPLE_COLUMN = "people";
	public static final String ACTIVITY_COLUMN = "activity";
	

	public static final String CREATE_TABLE = 
			"CREATE TABLE " + TABLE_NAME + " (" + 
			KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			DATETIME_COLUMN + " INTEGER NOT NULL, " + 
			IT_DATE_COLUMN + " TEXT, " +	
			TIME_COLUMN + " TEXT, " +
			COLOR_COLUMN + " INTEFER NOT NULL, " +
			TITLE_COLUMN + " TEXT NOT NULL, " +
			LOCATION_COLUMN + " TEXT NOT NULL, " +
			PEOPLE_COLUMN + " TEXT NOT NULL, " +
			ACTIVITY_COLUMN + " TEXT NOT NULL, " + 
			CONTENT_COLUMN + " TEXT, " +
			FILENAME_COLUMN + " TEXT, " + 
			LATITUDE_COLUMN + " REAL, " +
			LONGITUDE_COLUMN + " REAL, " + 
			LASTMODIFY_COLUMN + " INTEGER " + ") ";
	

	private SQLiteDatabase db;	
	


	public ItemDAO(Context context){
		db = MyDBHelper.getDatabase(context);
	}
	
	

	public void close() {
		db.close();
	}
	
	

	public Item insert(Item item) {

		ContentValues cv = new ContentValues();
		

		cv.put(DATETIME_COLUMN, item.getDatetime());
		cv.put(IT_DATE_COLUMN, item.getIt_date());	
		cv.put(TIME_COLUMN, item.getTime());
		cv.put(COLOR_COLUMN, item.getColor().parseColor());
		cv.put(TITLE_COLUMN, item.getTitle());
		cv.put(LOCATION_COLUMN, item.getLocation());
		cv.put(PEOPLE_COLUMN, item.getPeople());
		cv.put(ACTIVITY_COLUMN, item.getActivity());
		cv.put(CONTENT_COLUMN, item.getContent());
		cv.put(FILENAME_COLUMN, item.getFileName());
		cv.put(LATITUDE_COLUMN, item.getLatitude());
		cv.put(LONGITUDE_COLUMN, item.getLongitude());
		cv.put(LASTMODIFY_COLUMN, item.getLastModify());
		

		long id = db.insert(TABLE_NAME, null, cv);
		

		item.setId(id);
		
		return item;
	}
	
	

	public boolean update(Item item) {

		ContentValues cv = new ContentValues();
		

		cv.put(DATETIME_COLUMN, item.getDatetime());
		cv.put(IT_DATE_COLUMN, item.getIt_date());	
		cv.put(TIME_COLUMN, item.getTime());
		cv.put(COLOR_COLUMN, item.getColor().parseColor());
		cv.put(TITLE_COLUMN, item.getTitle());
		cv.put(LOCATION_COLUMN, item.getLocation());
		cv.put(PEOPLE_COLUMN, item.getPeople());
		cv.put(ACTIVITY_COLUMN, item.getActivity());
		cv.put(CONTENT_COLUMN, item.getContent());
		cv.put(FILENAME_COLUMN, item.getFileName());
		cv.put(LATITUDE_COLUMN, item.getLatitude());
		cv.put(LONGITUDE_COLUMN, item.getLongitude());
		cv.put(LASTMODIFY_COLUMN, item.getLastModify());		
		

		String where = KEY_ID + "=" + item.getId();
		

		return db.update(TABLE_NAME, cv, where, null) > 0;
	}
	
	

	public boolean delete(long id) {

		String where = KEY_ID + "=" +id;

		return db.delete(TABLE_NAME, where, null) > 0;
	}
	
	

	public List<Item> getAll() {
		List<Item> result = new ArrayList<Item>();
//		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, KEY_ID+ " DESC", null);	
		
		while (cursor.moveToNext()) {
			result.add(getRecord(cursor));
		}
		
		cursor.close();
		return result;
	}
	
	

	public Item get(long id) {

		Item item =null;

		String where = KEY_ID + "=" +id;

		Cursor result = db.query(TABLE_NAME, null, where, null, null, null, KEY_ID+ " DESC", null);
		

		if (result.moveToFirst()) {

			item = getRecord(result);
		}
		

		result.close();

		return item;
	}


	private Item getRecord(Cursor cursor) {

		Item result = new Item();
		
		result.setId(cursor.getLong(0));
		result.setDatetime(cursor.getLong(1));
		result.setIt_date(cursor.getString(2));
		result.setTime(cursor.getString(3));
		result.setColor(ItemActivity.getColors(cursor.getInt(4)));
		result.setTitle(cursor.getString(5));
		result.setLocation(cursor.getString(6));
		result.setPeople(cursor.getString(7));
		result.setActivity(cursor.getString(8));
		result.setContent(cursor.getString(9));
		result.setFileName(cursor.getString(10));
		result.setLatitude(cursor.getDouble(11));
		result.setLongitude(cursor.getDouble(12));
		result.setLastModify(cursor.getLong(13));
		

		return result;
	}
	
	

	public int getCount() {
		int result = 0;
		Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
		
		if (cursor.moveToNext()) {
			result = cursor.getInt(0);
		}
		
		return result;
	}
	
	

	public void sample() {
//		Item item = new Item(0, new Date().getTime(), Colors.RED, "01/02/2014", "Morning", "Hello MyAndroidApp", "Home", "Carole", "Breakfast", "Hello Content", "", 0, 0, 0);
//		Item item2 = new Item(0, new Date().getTime(), Colors.BLUE, "10/05/2014","Afternoon", "Welcome to MyAndroidApp", "Office","Luke", "Chatting", "Welcome!!", "", 0, 0, 0);
//		Item item3 = new Item(0, new Date().getTime(), Colors.GREEN, "10/10/2014","Afternoon", "Thank you for using MyAndroidApp", "Tea Room", "Coffee", "Janet", "Hello Content", "", 25.04719, 121.516981, 0);
//		Item item4 = new Item(0, new Date().getTime(), Colors.ORANGE, "22/12/2014","Evening", "Data stores on SQLiteDatabase", "Supermarket", "Shopping", "Amber", "Hello Data", "", 0, 0, 0);
	
		Item item = new Item(0, new Date().getTime(), "1/11/2014", "Morning", Colors.RED, "Hello MyAndroidApp", "Home", "Amy", "talking", "Hello Content", "", 0, 0, 0);
		Item item2 = new Item(0, new Date().getTime(), "22/1/2014", "Afternoon", Colors.BLUE, "Welcome to MyAndroidApp", "University", "Luke", "studying", "Welcome!!", "", 0, 0, 0);
		Item item3 = new Item(0, new Date().getTime(), "3/5/2014", "Evening", Colors.GREEN, "Thank you for using MyAndroidApp", "Supermarket", "Aliven", "shopping", "Hello Content", "", 25.04719, 121.516981, 0);
		Item item4 = new Item(0, new Date().getTime(), "4/10/2014", "Morning", Colors.ORANGE, "Data stores on SQLiteDatabase", "Office", "Carole", "reading", "Hello Data", "", 0, 0, 0);
		
		insert(item);
		insert(item2);
		insert(item3);
		insert(item4);
	}
}

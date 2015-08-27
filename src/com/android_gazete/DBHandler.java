package com.android_gazete;



import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper{

	// -- Database Version
	private static final int DATABASE_VERSION = 1;

	// -- Database Name
	private static final String DATABASE_NAME = "userdatabase125";//bunlarý hep güncelle

	// -- Contacts table name
	private static final String TABLE_PREFERENCES = "userpreferences100";//güncelle
	private static final String TABLE_NEWS = "newspaper100";
	//private static final String TABLE_CATEGORIES="categorytable";

	// -- Preference Table Columns names
	public static final String KEY_LIST_ROW = "list_row_id";
	public static final String KEY_ID = "_id";
	public static final String KEY_SORT_TYPE="sort_type";
	
	// -- Newspaper Table Columns names
	public static final String KEY_NEWS_ID="_id";
	public static final String KEY_NEWS_NAME="name";
	public static final String KEY_NEWS_URL="url";
	public static final String KEY_NEWS_IMAGE="image";
	public static final String KEY_NEWS_RANK="rank";
	
	public DBHandler(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_PREFERENCE_TABLE = "CREATE TABLE " + TABLE_PREFERENCES + "("
				 + KEY_ID +" INTEGER PRIMARY KEY,"+ KEY_LIST_ROW + " TEXT,"+KEY_SORT_TYPE+" TEXT"+")";
		
		String CREATE_NEWSPAPER_TABLE = "CREATE TABLE " + TABLE_NEWS + "("
				 + KEY_NEWS_ID +" INTEGER PRIMARY KEY,"+ KEY_NEWS_NAME + " TEXT," 
				 + KEY_NEWS_URL	+" TEXT,"+ KEY_NEWS_IMAGE + " BLOB,"	+ KEY_NEWS_RANK +" TEXT"+")";
		
		
		db.execSQL(CREATE_PREFERENCE_TABLE);
		db.execSQL(CREATE_NEWSPAPER_TABLE);
		//addNewPreference("Büyük");
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
		
	
		// -- create tables again
		onCreate(db);
		
	}
	
	
	
	/*preference operations*/
	public int getPreferenceCount()
	{
		String countQuery="SELECT * FROM " + TABLE_PREFERENCES;
		SQLiteDatabase db= this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		return cursor.getCount();
	}
	
	public void addNewPreference(String preference,String sorting)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_LIST_ROW, preference); 
	    values.put(KEY_SORT_TYPE, sorting);
	    
	    // Inserting Row
	    db.insert(TABLE_PREFERENCES, null, values);
	    db.close(); // Closing database connection
	}
	
	public void updatePreference(int id, String size,String sorting)
	{
		
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		    ContentValues values = new ContentValues();
		    values.put(KEY_LIST_ROW, size);
		    values.put(KEY_SORT_TYPE, sorting);
		    	 
		    // updating row
		     db.update(TABLE_PREFERENCES, values, KEY_ID + " = ?",
		         new String[]{String.valueOf(id)});
		     db.close();
	}
	
	public String getPreference(String field){
		SQLiteDatabase db= this.getReadableDatabase();
		String getQuery= "SELECT "+ field +" FROM " + TABLE_PREFERENCES;
		
		
		Cursor cursor = db.rawQuery(getQuery, null);
		// -- loop through all rows and adding to list
				if (cursor!=null){
						cursor.moveToFirst();}
				String value = cursor.getString(0);
		cursor.close();
		db.close();
		return value;
	}
	

	/*gazete operations*/
	public// Adding new gazetes
	void addJournal(Gazete journal) {
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues values = new ContentValues();
	
	values.put(KEY_NEWS_NAME, journal.name); // news name
	values.put(KEY_NEWS_URL, journal.url); // news url
	values.put(KEY_NEWS_IMAGE,journal.image);
	values.put(KEY_NEWS_RANK, journal.rank);

	// Inserting Row
	db.insert(TABLE_NEWS, null, values);
	db.close(); // Closing database connection
	}


	public Gazete getGazete(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NEWS, new String[] { KEY_NEWS_ID,
				KEY_NEWS_NAME,KEY_NEWS_URL, KEY_NEWS_IMAGE,KEY_NEWS_RANK }, KEY_NEWS_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if(cursor!=null)
			cursor.moveToFirst();
		
		Gazete journal = new Gazete(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getBlob(3),cursor.getInt(4));
		return journal;
	}
	
	public List<Gazete> getAllGazete(String orderMethod,boolean asc){
		
		List gazeteler= new ArrayList<Gazete>();
		String selectQuery="";
		//select all query
		if(asc) selectQuery="SELECT * FROM " + TABLE_NEWS + " ORDER BY "+ "LOWER("+orderMethod+")" +" ASC"; 
		else selectQuery="SELECT * FROM " + TABLE_NEWS + " ORDER BY "+ orderMethod +" DESC"; 
		SQLiteDatabase db= this.getWritableDatabase();
		Cursor cursor=db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst())
			{
				do{
					Gazete journal=new Gazete();
					journal.set_id(Integer.parseInt(cursor.getString(0)));
					journal.setName(cursor.getString(1));
					journal.setUrl(cursor.getString(2));
					journal.setImage(cursor.getBlob(3));
					journal.setRank(cursor.getInt(4));
					gazeteler.add(journal);
				}while(cursor.moveToNext());
				db.close();
			}
		return gazeteler;
	}
	
	public int updateGazete(Gazete gazete)
	{
		SQLiteDatabase db= this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(KEY_NEWS_NAME, gazete.getName());
		values.put(KEY_NEWS_URL, gazete.getUrl());
		values.put(KEY_NEWS_IMAGE, gazete.getImage());
		values.put(KEY_NEWS_RANK, gazete.getRank());
		
		/*updating row*/
		return db.update(TABLE_NEWS, values, KEY_NEWS_ID + " = ?",
				new String[] { String.valueOf(gazete.get_id()) });

	}
	
	public void deleteGazete(Gazete gazete) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NEWS, KEY_NEWS_ID + " = ?",
		new String[] { String.valueOf(gazete.get_id()) });
		db.close();
		}
	
	public int getGazeteCount() {
		String countQuery = "SELECT * FROM " + TABLE_NEWS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		//cursor.close();

		// return count
		return cursor.getCount();
		}

	
}

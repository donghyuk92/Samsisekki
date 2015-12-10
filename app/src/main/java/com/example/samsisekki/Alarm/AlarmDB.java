package com.example.samsisekki.Alarm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AlarmDB {

	DatabaseHelper mHelper;

	/**
    * The database that the provider uses as its underlying data store
    */
    private static final String DATABASE_NAME = "alarm.db";
    
    /**
     * The database that the provider uses as its underlying data store
     */
 	private static final String DATABASE_TABLE_NAME = "tblAlarm";
 	
 	/**
     * Column name for the id of the alarm
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ID = "id";
    
    /**
     * Column name for the requestCode of the alarm
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_GC = "gc";
    
 	/**
     * Column name for the title of the alarm
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_TIME = "time";
    

    public static final String COLUMN_NAME_WEEKDATE = "weekdate";

	public AlarmDB(Context context)
	{
		mHelper = new DatabaseHelper(context);
	}
	
	/*
	 * Insert new record to database 
	 */
	public void INSERT(int gc, String time, int weekdate)
	{
		
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ").append(DATABASE_TABLE_NAME).append(" VALUES ");
		sb.append("(").append("null").append(", ");
		sb.append(gc).append(", ");
		sb.append("'").append(time).append("', ");
		sb.append(weekdate).append("");
		sb.append("); ");  
		
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.execSQL(sb.toString());

		mHelper.close();

	}
	
	/*
	 * Delete existed record in database 
	 */
	public void DELETE_GROUP(int gc)
	{
		try
		{
			StringBuffer sb = new StringBuffer();

			sb.append("DELETE FROM ").append(DATABASE_TABLE_NAME).append(" WHERE ");
			sb.append(COLUMN_NAME_GC).append("=").append(gc).append(";");
			
			SQLiteDatabase db = mHelper.getWritableDatabase();

			db.execSQL(sb.toString());
			mHelper.close();
		}
		catch(Exception ex)
		{
			
		}
	}

	/*
 * Clear all data in table if it's existed
 */
	public void DROP()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ").append(DATABASE_TABLE_NAME).append(";");

		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.execSQL(sb.toString());
		mHelper.close();
	}

	/*
	 * Query to get list of note in database
	 */
	public ArrayList<Alarm> query() {
		ArrayList<Alarm> list = new ArrayList<Alarm>();
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor;

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ").append(DATABASE_TABLE_NAME);
		
		cursor = db.rawQuery(sb.toString(), null);
		
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);  
			String gc = cursor.getString(1);  
			String time = cursor.getString(2);  
			String weekdate = cursor.getString(3);
			
			list.add(new Alarm(Integer.parseInt(id), Integer.parseInt(gc), time, Integer.parseInt(weekdate)));
		}

		cursor.close();
		mHelper.close();
		
		return list;
	}
	
	public ArrayList<Alarm> queryByGroup(int rgc) {
		ArrayList<Alarm> list = new ArrayList<Alarm>();
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor;

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ").append(DATABASE_TABLE_NAME).append(" WHERE ").append(COLUMN_NAME_GC).append(" = ").append(rgc);
		
		cursor = db.rawQuery(sb.toString(), null);
		
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);  
			String gc = cursor.getString(1);  
			String time = cursor.getString(2);  
			String weekdate = cursor.getString(3);
			
			list.add(new Alarm(Integer.parseInt(id), Integer.parseInt(gc), time, Integer.parseInt(weekdate)));
		}

		cursor.close();
		mHelper.close();
		
		return list;
	}
	

	
	class DatabaseHelper extends SQLiteOpenHelper {
		
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, 1);
		}

		// Declare table with 4 column: (id - int auto increment, title - String , note - String, date - integer)
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_NAME + " ("
	                   + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	                   + COLUMN_NAME_GC + " INTEGER,"
	                   + COLUMN_NAME_TIME + " TEXT, "
	                   + COLUMN_NAME_WEEKDATE + " INTEGER "
	                   + ");");
		}

		// Drop table if it's existed
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			StringBuffer sb = new StringBuffer();
			sb.append("DROP TABLE IF EXISTS ").append(DATABASE_NAME).append("");
			
			db.execSQL(sb.toString());
			onCreate(db);
		}
	}

}


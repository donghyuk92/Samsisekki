package com.example.samsisekki.dbtest;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController  extends SQLiteOpenHelper {

    SQLiteDatabase db;

	public DBController(Context applicationcontext) {
        super(applicationcontext, "user.db", null, 1);
    }
	//Creates Table
	@Override
	public void onCreate(SQLiteDatabase database) {
        db = database;
		String query;
		query = "CREATE TABLE IF NOT EXISTS test (" +
                " deviceID varchar(40), inserttime TIMESTAMP, class varchar(10)," +
                " menu varchar(30), rating float, url varchar(50) )";
        database.execSQL(query);
	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query;
		query = "DROP TABLE IF EXISTS test";
		database.execSQL(query);
        onCreate(database);
	}
	
	
	/**
	 * Inserts User into SQLite DB
	 * @param queryValues
	 */
	public void insertUser(HashMap<String, String> queryValues) {

        SQLiteDatabase database = this.getWritableDatabase();

        String query;
        query = "CREATE TABLE IF NOT EXISTS test (" +
                " deviceID varchar(40), inserttime TIMESTAMP, class varchar(10)," +
                " menu varchar(30), rating float, url varchar(50) )";
        database.execSQL(query);

        ContentValues values = new ContentValues();
        values.put("deviceID", queryValues.get("deviceID"));
        values.put("inserttime", queryValues.get("inserttime"));
        values.put("class", queryValues.get("class"));
        values.put("menu", queryValues.get("menu"));
        values.put("rating", queryValues.get("rating"));
        values.put("url", queryValues.get("url"));
		database.insert("test", null, values);
		database.close();
	}

	/**
	 * Get list of Users from SQLite DB as Array List
	 * @return
	 */
	public ArrayList<HashMap<String, String>> getAllUsers() {
		ArrayList<HashMap<String, String>> usersList;
		usersList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM users";
	    SQLiteDatabase database = this.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("userId", cursor.getString(0));
	        	map.put("userName", cursor.getString(1));
                usersList.add(map);
	        } while (cursor.moveToNext());
	    }
	    database.close();
	    return usersList;
	}

}

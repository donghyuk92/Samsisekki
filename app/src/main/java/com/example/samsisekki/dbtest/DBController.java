package com.example.samsisekki.dbtest;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBController  extends SQLiteOpenHelper {

	public DBController(Context applicationcontext) {
        super(applicationcontext, "test.db", null, 1);
    }
	//Creates Table
	@Override
	public void onCreate(SQLiteDatabase database) {
		String query;
		query = "CREATE TABLE IF NOT EXISTS test (" +
                " deviceID varchar(40), inserttime TIMESTAMP, class varchar(10)," +
                " menu varchar(30), rating float, url varchar(50), PRIMARY KEY (deviceID, menu) )";
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
	public ArrayList<HashMap<String, String>> getHistory(String deviceID) {
		ArrayList<HashMap<String, String>> usersList;
		usersList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT * from test where deviceID='" + deviceID + "' order by inserttime desc";
        SQLiteDatabase database = this.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
				map.put("deviceID",cursor.getString(0));
				map.put("inserttime", cursor.getString(1));
				map.put("class", cursor.getString(2));
				map.put("menu", cursor.getString(3));
				map.put("rating", cursor.getString(4));
				map.put("url", cursor.getString(5));
				usersList.add(map);
	        } while (cursor.moveToNext());
	    }
	    database.close();
	    return usersList;
	}
	public void UpdateSQLite(String deviceID, String clas, String menu, Float rating, String url) {

        String query = "insert or ignore into test values ('"+deviceID+"', CURRENT_TIME, '"+clas+"','"+menu+"','"+rating+"','"+url+"');" +
                        "UPDATE test SET inserttime=CURRENT_TIME, rating='"+rating+"' where deviceID='"+deviceID+"' and menu='"+menu+"';";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public Cursor getHist(String deviceID){
        String sql = "select menu,url from test where deviceID='" + deviceID + "' order by inserttime asc;";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor result = database.rawQuery(sql, null);
        result.moveToFirst();
        return result;
    }
    public Cursor getRank(){
        String sql = "select menu,avg,url from (select menu,avg(rating) as avg,url from test group by menu) order by avg desc;";
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor result = database.rawQuery(sql, null);
        result.moveToFirst();
        return result;
    }
    public Cursor getRelated(String menu) {
        String sql = "select menu,url from test where menu like '%"+menu+"%' group by menu;";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor result = database.rawQuery(sql, null);
        result.moveToFirst();
        return result;
    }
    public Cursor getIDs() {
        String sql = "select distinct deviceID from test;";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor result = database.rawQuery(sql, null);
        result.moveToFirst();
        return result;
    }
    public Cursor getIDVector(String deviceID) {
        String sql = "select menu, rating from test where deviceID='" + deviceID + "';";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor result = database.rawQuery(sql, null);
        result.moveToFirst();
        return result;
    }
    public void drop() {
        String query = "DROP TABLE IF EXISTS test";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
    public void delete() {
        String query = "delete from test;";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void createtbl() {
        String query = "CREATE TABLE IF NOT EXISTS test (" +
                " deviceID varchar(40), inserttime TIMESTAMP, class varchar(10)," +
                " menu varchar(30), rating float, url varchar(50), PRIMARY KEY (deviceID, menu) )";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
}
package com.android.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MusicDBSource implements DBSource<SongEntity, String>{
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	private String[] allColumns = { DBHelper._ID, DBHelper.ARTIST,
			DBHelper.SONG_NAME, DBHelper.ENERGY, DBHelper.TEMPO,
			DBHelper.DANCEABILITY, DBHelper.DURATION,
			DBHelper.SONG_ID };


	public MusicDBSource(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void insertEntry(SongEntity song) {
		ContentValues values = new ContentValues();
		values.put(DBHelper._ID, song.get_id());
		values.put(DBHelper.ARTIST, song.get_artist());
		values.put(DBHelper.SONG_NAME, song.get_artist());
		values.put(DBHelper.ENERGY, song.get_energy());
		values.put(DBHelper.TEMPO, song.get_tempo());
		values.put(DBHelper.DANCEABILITY, song.get_danceability());
		values.put(DBHelper.DURATION, song.get_duration());
		values.put(DBHelper.SONG_ID, song.get_echonest_id());
		database.insert(DBHelper.dbName, null, values);
	}
	
	public Cursor readEntry(String echonest_id) {
		Cursor cursor = database.query(DBHelper.dbName, allColumns, DBHelper.SONG_ID + "=" + echonest_id, null, null, null, null);
		if(null != cursor) {
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	public boolean deleteEntry(String echonest_id) {
		return database.delete(DBHelper.dbName, DBHelper.SONG_ID + "=" + echonest_id, null) > 0;
	}
	
	public boolean updateEntry(SongEntity song) {
		ContentValues values = new ContentValues();
		values.put(DBHelper._ID, song.get_id());
		values.put(DBHelper.ARTIST, song.get_artist());
		values.put(DBHelper.SONG_NAME, song.get_artist());
		values.put(DBHelper.ENERGY, song.get_energy());
		values.put(DBHelper.TEMPO, song.get_tempo());
		values.put(DBHelper.DANCEABILITY, song.get_danceability());
		values.put(DBHelper.DURATION, song.get_duration());
		values.put(DBHelper.SONG_ID, song.get_echonest_id());
		return database.update(DBHelper.dbName, values, DBHelper.SONG_ID + "=" + song.echonest_id, null) > 0;
	}
 
}

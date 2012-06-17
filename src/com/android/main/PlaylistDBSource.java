//package com.android.main;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//
//public class PlaylistDBSource implements DBSource<Playlist, String>{
//	DBHelper dbHelper;
//	SQLiteDatabase database;
//	
//	public PlaylistDBSource(Context context) {
//		dbHelper = new DBHelper(context);
//	}
//	
//	public void open() throws SQLException {
//		database = dbHelper.getWritableDatabase();
//	}
//	
//	public void close() {
//		dbHelper.close();
//	}
//	
//	public void insertEntry(Playlist playlist) {
//		ContentValues values = new ContentValues();
//		values.put(DBHelper.PLAYLIST_ID, playlist.se);
//		values.put(DBHelper.PLAYLIST_NAME, playlist.getName());
//		values.put(DBHelper.SONG_NAME, song.title);
//		values.put(DBHelper.ENERGY, song.energy);
//		database.insert(DBHelper.dbName, null, values);
//	}
//	
//	public Cursor readEntry(String echonest_id) {
//		Cursor cursor = database.query(DBHelper.dbName, allColumns, DBHelper.SONG_ID + "=" + echonest_id, null, null, null, null);
//		if(null != cursor) {
//			cursor.moveToFirst();
//		}
//		return cursor;
//	}
//	
//	public boolean deleteEntry(String echonest_id) {
//		return database.delete(DBHelper.dbName, DBHelper.SONG_ID + "=" + echonest_id, null) > 0;
//	}
//	
//	public boolean updateEntry(Playlist song) {
//		ContentValues values = new ContentValues();
//		values.put(DBHelper._ID, song.id);
//		values.put(DBHelper.ARTIST, song.artist);
//		values.put(DBHelper.SONG_NAME, song.title);
//		values.put(DBHelper.ENERGY, song.energy);
//		return database.update(DBHelper.dbName, values, DBHelper.SONG_ID + "=" + song.echonest_id, null) > 0;
//	}
// 
//
//}

package com.android.main;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	static final String dbName = "musicDB";

	// Music table properties
	static final String musicTable = "MusicProperties";
	static final String _ID = "_id";
	static final String ARTIST = "atrist";
	static final String SONG_NAME = "song_name";
	static final String ENERGY = "energy";
	static final String TEMPO = "tempo";
	static final String DANCEABILITY = "danceability";
	static final String DURATION = "duration";
	static final String SONG_ID = "song_id";
	
	//Playlist table properties
	static final String playlistTable = "Playlists";
	static final String PLAYLIST_ID = "playlist_id";
	static final String PLAYLIST_NAME = "playlist_name";
	static final String NUM_SONGS = "num_songs";
	static final String SONG_LIST = "song_list"; 

	public DBHelper(Context context) {
		super(context, dbName, null, 33);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String create_musicTable = "CREATE TABLE " + musicTable + "(" + _ID
				+ " TEXT PRIMARY KEY," + ARTIST + " TEXT," + SONG_NAME
				+ " TEXT," + ENERGY + " FLOAT," + TEMPO + " FLOAT,"
				+ DANCEABILITY + " FLOAT," + DURATION + " FLOAT" + ");";
		try {
			db.execSQL(create_musicTable);
		} catch (SQLException e) {
			e.getMessage();
			
		}
		
		String create_playlistTable = "CREATE TABLE " + playlistTable + "(" + PLAYLIST_ID
				+ " INTEGER PRIMARY KEY," + PLAYLIST_NAME + " TEXT NOT NULL," + NUM_SONGS
				+ " INTEGER," + SONG_LIST + " TEXT NOT NULL);";
		db.execSQL(create_playlistTable);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
}

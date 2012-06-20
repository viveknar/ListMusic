package com.android.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

public class ListMusicActivity extends ListActivity {
	public String[] musicList = null;
	public Cursor songCursor;
	private String songs = "";
	private ArrayList<SongEntity> songDetails = new ArrayList<SongEntity>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MusicDBSource db = new MusicDBSource(getApplicationContext());
		String[] projection = new String[] {
				android.provider.MediaStore.Audio.Media.ARTIST_ID,
				android.provider.MediaStore.Audio.Media.ARTIST,
				android.provider.MediaStore.Audio.Media.TITLE,
				android.provider.MediaStore.Audio.Media.DURATION };
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
		songCursor = getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
				selection, null, null);
		if (null == songCursor) {
			new Exception("Error reading song files");
		} else if (songCursor.getCount() < 1) {

		} else {
			songCursor.moveToFirst();
			List<String> songList = new ArrayList<String>();
			while (songCursor.moveToNext()) {
				songList.add(songCursor.getString(songCursor
						.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST))
						+ ", "
						+ songCursor.getString(songCursor
								.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE)));
			}
			musicList = songList.toArray(new String[songList.size()]);

		}
		new EchoNestRequest().execute(musicList);
		for (SongEntity s : songDetails) {
			db.open();
			db.insertEntry(s);
			db.close();
		}
	}

	class EchoNestRequest extends AsyncTask<String[], Void, String> {

		@Override
		protected String doInBackground(String[]... params) {
			String output = "";
			ExecutorService executor = Executors.newFixedThreadPool(5);
			String[] temp = {params[0][100], params[0][101], params[0][102], params[0][103], params[0][104], params[0][105], params[0][106], params[0][107]};
			for (String songdet : temp) {
				String[] det = songdet.split(",");
				RequestThread worker = new RequestThread(det[0], det[1]);
				executor.execute(worker);
			}

			executor.shutdown();
			while (!executor.isTerminated()) {
			}
			return songDetails.get(6).get_echonest_id();

		}

		@Override
		protected void onPostExecute(String output) {
			Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG)
					.show();
		}

	}

	class RequestThread implements Runnable {
		public String artist, title;

		public RequestThread(String artist, String title) {
			this.artist = artist;
			this.title = title;
			
		}

		public void run() {
			try {
				
				if (EchoNest.get_song_details(artist, title) == null) {
					songs += "No song";
				} else {
					
					songs += artist+" "+title+"\n";
					songDetails.add(EchoNest.get_song_details(artist, title));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
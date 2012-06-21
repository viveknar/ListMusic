package com.android.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

public class ListMusicActivity extends Activity {
	public String[] musicList = null;
	public Cursor songCursor;
	private String songs = "";
	private ArrayList<SongEntity> songDetails = new ArrayList<SongEntity>();
	private int numSongsAdded = 0;
	private int numSongsNotAdded = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);

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
		songCursor.close();

	}

	class EchoNestRequest extends AsyncTask<String[], Void, String> {

		@Override
		protected String doInBackground(String[]... params) {
			String output = "";
			ExecutorService executor = Executors.newFixedThreadPool(5);
			String[] temp = { params[0][80], params[0][81], params[0][82],
					params[0][83], params[0][84], params[0][85], params[0][86],
					params[0][87] };
			for (String songdet : params[0]) {
				String[] det = songdet.split(",");
					RequestThread worker = new RequestThread(det[0], det[1]);
					executor.execute(worker);
				
			}

			executor.shutdown();
			while (!executor.isTerminated()) {
			}
			numSongsNotAdded = params.length;
			return songs;

		}

		@Override
		protected void onPostExecute(String output) {
			// Toast.makeText(getApplicationContext(), output,
			// Toast.LENGTH_LONG)
			// .show();
			MusicDBSource db = new MusicDBSource(getApplicationContext());
			db.open();
			for (SongEntity s : songDetails) {
				db.insertEntry(s);

			}
			numSongsAdded = db.getNumEntries();
			numSongsNotAdded = musicList.length - numSongsAdded;
			db.close();
			try {
				TextView tv = new TextView(getApplicationContext());
				tv = (TextView) findViewById(R.id.addedTracks);
				tv.setText(Integer.toString(numSongsAdded) + " tracks added");
				if (numSongsNotAdded > 0) {
					tv.append("\n" + Integer.toString(numSongsNotAdded)
							+ " not added");
					tv.append("\nPlease check and fix broken ID3 tags");
				}

			} catch (NullPointerException e) {
				Toast.makeText(getApplicationContext(), "null pointer",
						Toast.LENGTH_LONG).show();
			}
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
				SongEntity songEntity = EchoNest
						.get_song_details(artist, title);
				if (songEntity.get_echonest_id().length() != 0) {
					songs += artist + " " + title + "\n";
					songDetails.add(songEntity);
				}
				Thread.sleep(50);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				songs += e.getMessage();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}

		}
	}

	@Override
	public void onBackPressed() {
		Intent goBack = new Intent(this, WelcomeScreenActivity.class);
		goBack.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(goBack);
		return;
	}

}
package com.android.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListMusicActivity extends ListActivity {
	public String[] musicList = null;
	public Cursor songCursor;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        String[] projection = new String[] {
				android.provider.MediaStore.Audio.Media.ARTIST_ID,
				android.provider.MediaStore.Audio.Media.ARTIST,
				android.provider.MediaStore.Audio.Media.TITLE,
				android.provider.MediaStore.Audio.Media.DURATION
				};
        String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0"; 
        songCursor = getContentResolver().query (
        				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        				projection,
        				selection,
        				null, null);
        if (null == songCursor) {
        	new Exception("Error reading song files");
        }
        else if (songCursor.getCount() < 1) {
        	
        }
        else {
        	songCursor.moveToFirst();
        	List<String> songList = new ArrayList<String>(); 
        	while(songCursor.moveToNext()) {
        		songList.add(songCursor.getString(songCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST))+", "+
        		songCursor.getString(songCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE)));
        	}
        	musicList = songList.toArray(new String[songList.size()]);
       }
       
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_view, musicList));
        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String[] params = ((TextView) view).getText().toString().split(",");
	    		try {
					SongEntity song = EchoNest.get_song_details(params[0], params[1]);
	        		Toast.makeText(getApplicationContext(), song.get_artist(), Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

        	}
        });
        
        
    }
    
//	private class DownloadMusicDetails extends AsyncTask<String, Void, String> {
//		@Override
//		protected String doInBackground(String... params) {
//			try {
//				EchoNest.get_song_details(params[0], params[1]);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return params[0]+params[1];
//			 
//		}
//		
//		protected void onPostExecute(String response) {
//			Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//		}
//	}

}
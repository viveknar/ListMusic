package com.android.main;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.database.Cursor;
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
        		songList.add(songCursor.getString(songCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST_ID))+", "+
        		songCursor.getString(songCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST))+", "+
        		songCursor.getString(songCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE))+", "+
        		songCursor.getString(songCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DURATION)));
        		
        	}
        	musicList = songList.toArray(new String[songList.size()]);
       }
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_view, musicList));
        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
        	}
        });
        
        
    }
}
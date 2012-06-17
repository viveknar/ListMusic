package com.android.main;

import java.io.IOException;

import org.json.JSONException;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeScreenActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_screen);
		EchoNest.setAPIKey("FQTGFAEJK1DLGZVAL");
		try {
			EchoNest.get_song_details("metallica", "one");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.new_playlist: 
			Intent newPlaylistIntent = new Intent(this, NewPlaylistActivity.class);
			startActivity(newPlaylistIntent);
			return true;
		case R.id.all_songs:
			Intent allSongsIntent = new Intent(this, ListMusicActivity.class);
			startActivity(allSongsIntent);
		case R.id.quit:
			finish();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
		
	}

}

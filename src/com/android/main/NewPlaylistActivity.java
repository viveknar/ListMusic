package com.android.main;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NewPlaylistActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.android.main.MESSAGE";	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_playlist);
		Button sendButton = (Button) findViewById(R.id.button_create_playlist);
		sendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new EchoNestRequest().execute();
			}
			
			class EchoNestRequest extends AsyncTask<Void, Void, String> {
				
				@Override
				protected String doInBackground(Void... params) {
					try {

						return EchoNest.get_song_details("metallica", "one").get_echonest_id();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return "";
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return "";
					}
				}
				
				@Override
				protected void onPostExecute(String output) {
					Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
				}

			}

		});
		
	}
	
	
	
}

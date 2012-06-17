package com.android.main;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewPlaylistActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.android.main.MESSAGE";	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_playlist);
	}
	
}

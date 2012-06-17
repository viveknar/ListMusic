package com.android.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TestIntent extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String message = intent.getStringExtra(NewPlaylistActivity.EXTRA_MESSAGE);
		TextView tv = new TextView(this);
		tv.setTextSize(40);
		tv.setText(message);
		setContentView(tv);

	}

}

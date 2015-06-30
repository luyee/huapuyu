package com.anders.anniversary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AddActivity extends Activity {

	private TextView textView1 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		Intent intent = getIntent();
		String value = intent.getStringExtra("name");
		textView1 = (TextView) findViewById(R.id.textView1);
		// textView1.setText(R.string.add_textView1);
		textView1.setText(value);

	}

}

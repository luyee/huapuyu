package com.anders.anniversary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AddActivity extends Activity{

	private TextView textView1 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		textView1 = (TextView)findViewById(R.id.textView1);
		textView1.setText(R.string.add_textView1);
	}

}

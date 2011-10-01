package com.anders.anniversary;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button button1 = null;
	private Button button2 = null;
	private Button button3 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button1 = (Button) findViewById(R.id.button1);
		button1.setText(R.string.add_button1);
		button1.setOnClickListener(new Button1Listener());

		button2 = (Button) findViewById(R.id.button2);
		button2.setText(R.string.sendmsg_button2);
		button2.setOnClickListener(new Button2Listener());

		button3 = (Button) findViewById(R.id.button3);
		button3.setText(R.string.calculator);
		button3.setOnClickListener(new Button3Listener());
	}

	class Button1Listener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.putExtra("name", "zhuzhen");
			intent.setClass(MainActivity.this, AddActivity.class);
			MainActivity.this.startActivity(intent);
		}
	}

	class Button2Listener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Uri uri = Uri.parse("smsto:0800000123");
			Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
			intent.putExtra("sms_body", "妹妹我爱你！");
			startActivity(intent);
		}
	}

	class Button3Listener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, CalculatorActivity.class);
			startActivity(intent);
		}
	}
}
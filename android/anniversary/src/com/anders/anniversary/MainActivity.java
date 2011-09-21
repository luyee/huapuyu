package com.anders.anniversary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button button1 = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button1 = (Button)findViewById(R.id.button1);
        button1.setText(R.string.add_button1);
        button1.setOnClickListener(new Button1Listener());
    }
    
    class Button1Listener implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, AddActivity.class);
			MainActivity.this.startActivity(intent);
		}
    }
}
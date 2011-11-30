package com.anders.anniversary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorActivity extends Activity {
	private EditText edtCal1 = null;
	private EditText edtCal2 = null;
	private EditText edtCal3 = null;
	private TextView txtCal1 = null;
	private TextView txtCal2 = null;
	private Button btnCal1 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);

		edtCal1 = (EditText) findViewById(R.id.edtCal1);
		edtCal2 = (EditText) findViewById(R.id.edtCal2);
		edtCal3 = (EditText) findViewById(R.id.edtCal3);
		edtCal3.setEnabled(false);
		txtCal1 = (TextView) findViewById(R.id.txtCal1);
		txtCal1.setText(R.string.cal_mul);
		txtCal2 = (TextView) findViewById(R.id.txtCal2);
		txtCal2.setText(R.string.cal_result);
		btnCal1 = (Button) findViewById(R.id.btnCal1);
		btnCal1.setOnClickListener(new Button1Listener());
		btnCal1.setText(R.string.cal_cal);
	}

	class Button1Listener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// double d1 = edtCal1.getText().
			// edtCal3.setText()
		}
	}

}

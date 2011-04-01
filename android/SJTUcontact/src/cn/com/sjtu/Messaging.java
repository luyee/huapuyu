package cn.com.sjtu;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Messaging extends Activity {
	private EditText phoneText;
	private EditText contentText;
	private Button button;
	private Button cancel;
	private Cursor mCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messaging);

		phoneText = (EditText) this.findViewById(R.messid.phoneNum);
		contentText = (EditText) this.findViewById(R.messid.content);
		button = (Button) this.findViewById(R.messid.sent);
		cancel = (Button) this.findViewById(R.messid.cancel);
		final Intent intent = getIntent();
		mCursor = managedQuery(intent.getData(), ContactColumn.PROJECTION,
				null, null, null);
		mCursor.moveToFirst();
		phoneText.setText(mCursor.getString(ContactColumn.MOBILE_COLUMN));

		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				sendMessage();
			}
		});
		
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**  
     *   
     */
	private void sendMessage() {
		// 获取文本框中的内容
		String phone_num = phoneText.getText().toString();
		String content = contentText.getText().toString();

		SmsManager smsMagager = SmsManager.getDefault();
		try {
			if (content.length() > 70) {
				List<String> sms = smsMagager.divideMessage(content);
				for (String con : sms) {
					smsMagager
							.sendTextMessage(phone_num, null, con, null, null);
				}
			} else {
				smsMagager
						.sendTextMessage(phone_num, null, content, null, null);
			}
		} catch (IllegalArgumentException e) {
			if (content == null || content.equals("")) {
				Toast.makeText(this, R.string.null_error, Toast.LENGTH_SHORT)
						.show();
				return;
			}
		}
		Toast.makeText(this, R.string.info, Toast.LENGTH_SHORT).show();
	}
}

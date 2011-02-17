package cn.com.sjtu;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddGroupView extends Activity {
	private EditText nameText;
	private Button saveButton;
	private Button cancelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_add);
		nameText = (EditText) findViewById(R.id.group_name_text);
		saveButton = (Button) findViewById(R.id.group_add_save);
		cancelButton = (Button) findViewById(R.id.group_add_cancel);

		saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String text = nameText.getText().toString();
				if (text.length() == 0) {
				} else {
					ContentValues value = new ContentValues();
					value.put(ContactColumn.GROUP_NAME, text);
					getContentResolver().insert(GroupProvider.GROUP_URI, value);
				}
				finish();
			}

		});
		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

	}
}


package cn.com.sjtu;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.util.Tools;

public class ContactEditor extends Activity {

	private static final String TAG = "ContactEditor";

	private static final int STATE_EDIT = 0;
	private static final int STATE_INSERT = 1;

	private static final int REVERT_ID = Menu.FIRST;
	private static final int DISCARD_ID = Menu.FIRST + 1;
	private static final int DELETE_ID = Menu.FIRST + 2;
	private static final int CALL_ID = Menu.FIRST + 3;
	private static final int SENTMESS_ID = Menu.FIRST + 4;
	
	private int mState;
	private Uri mUri;
	private Cursor mCursor;

	private EditText nameText;
	private EditText mPhoneText;
	private EditText emailText;
	private EditText homeNumText;
	private EditText addressText;
	
	private Spinner spaSpinner;
	private Button saveButton;
	private Button cancelButton;

	private String originalNameText = "";
	private String originalMPhoneText = "";
	private String originalEmailText = "";

	private ArrayAdapter<String> groupAdapter;
	
	private Map<Integer, Integer> groupIndexMap;

	private int id;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Intent intent = getIntent();
		final String action = intent.getAction();
		Log.e(TAG + ":onCreate", action);
		if (Intent.ACTION_EDIT.equals(action)) {
			mState = STATE_EDIT;
			mUri = intent.getData();
		} else if (Intent.ACTION_INSERT.equals(action)) {
			mState = STATE_INSERT;
			mUri = getContentResolver().insert(intent.getData(), null);

			if (mUri == null) {
				Log.e(TAG + ":onCreate", "Failed to insert new Contact into " + getIntent().getData());
				finish();
				return;
			}
			setResult(RESULT_OK, (new Intent()).setAction(mUri.toString()));

		} else {
			Log.e(TAG + ":onCreate", " unknown action");
			finish();
			return;
		}

		setContentView(R.layout.contact_editor);
		
		Cursor cursor = managedQuery(GroupProvider.GROUP_URI, ContactColumn.GROUPPRO, null, null, null);
		groupIndexMap = new HashMap<Integer, Integer>();
		String[] strs = new String[cursor.getCount()];
		int nameColumnIndex = cursor.getColumnIndex(ContactColumn.GROUP_NAME);
		int idColumnIndex = cursor.getColumnIndex(ContactColumn._ID);
		cursor.moveToFirst();
		for (int i = 0; i < strs.length; i++) {
			strs[i] = cursor.getString(nameColumnIndex);
			groupIndexMap.put(i, cursor.getInt(idColumnIndex));
			cursor.moveToNext();
		}
		groupAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strs);
		
		
		nameText = (EditText) findViewById(R.id.EditText01);
		mPhoneText = (EditText) findViewById(R.id.EditText02);
		emailText = (EditText) findViewById(R.id.EditText03);
		spaSpinner = (Spinner) findViewById(R.id.Spinner01);
		addressText = (EditText) findViewById(R.id.EditText04);
		homeNumText = (EditText) findViewById(R.id.EditText05);
		
		spaSpinner.setAdapter(groupAdapter);
		
		
		saveButton = (Button) findViewById(R.id.Button01);
		cancelButton = (Button) findViewById(R.id.Button02);

		saveButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String text = nameText.getText().toString();
				if (text.length() == 0) {
					setResult(RESULT_CANCELED);
					deleteContact();
					finish();
				} else {
					updateContact();
				}
			}

		});
		cancelButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mState == STATE_INSERT) {
					setResult(RESULT_CANCELED);
					deleteContact();
					finish();
				} else {
					backupContact();
				}

			}

		});
		
		
		
		Log.e(TAG + ":onCreate", mUri.toString());
		// 获得并保存原始联系人信息
		mCursor = managedQuery(mUri, ContactColumn.PROJECTION, null, null, null);
		mCursor.moveToFirst();
		originalNameText = mCursor.getString(ContactColumn.NAME_COLUMN);
		originalMPhoneText = mCursor.getString(ContactColumn.MOBILE_COLUMN);
		originalEmailText = mCursor.getString(ContactColumn.EMAIL_COLUMN);

		Log.e(TAG, "end of onCreate()");
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (mCursor != null) {
			Log.e(TAG + ":onResume", "count:" + mCursor.getColumnCount());
			// 读取并显示联系人信息
			mCursor.moveToFirst();
			if (mState == STATE_EDIT) {
				setTitle(getText(R.string.contact_edit));
			} else if (mState == STATE_INSERT) {
				setTitle(getText(R.string.contact_create));
			}
			String name = mCursor.getString(ContactColumn.NAME_COLUMN);
			String mPhone = mCursor.getString(ContactColumn.MOBILE_COLUMN);
			String email = mCursor.getString(ContactColumn.EMAIL_COLUMN);
			String address = mCursor.getString(mCursor.getColumnIndex(ContactColumn.ADDRESS));
			String homenum = mCursor.getString(mCursor.getColumnIndex(ContactColumn.HOMENUM));
			
			Log.e(TAG + ":onResume", "name:" + name + "mPhone:" + mPhone + "email:" + email);
			
			id = mCursor.getInt(mCursor.getColumnIndex(ContactColumn._ID));
			
			nameText.setText(name);
			mPhoneText.setText(mPhone);
			emailText.setText(email);
			addressText.setText(address);
			homeNumText.setText(homenum);
			
			Set<Integer> keySet = groupIndexMap.keySet();
			for (Integer key : keySet) {
				if(groupIndexMap.get(key) == mCursor.getInt(mCursor.getColumnIndex(ContactColumn.GROUPNUM))){
					spaSpinner.setSelection(key);
				}
			}
		} else {
			setTitle(getText(R.string.error_msg));
		}

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mCursor != null) {
			String text = nameText.getText().toString();

			if (text.length() == 0) {
				Log.e(TAG + ":onPause", "nameText is null ");
				setResult(RESULT_CANCELED);
				deleteContact();

				// 更新信息
			} else {
				ContentValues values = new ContentValues();
				values.put(ContactColumn.NAME, nameText.getText().toString());
				values.put(ContactColumn.MOBILE, mPhoneText.getText().toString());
				values.put(ContactColumn.EMAIL, emailText.getText().toString());
				Log.e(TAG + ":onPause", mUri.toString());
				Log.e(TAG + ":onPause", values.toString());
				getContentResolver().update(mUri, values, null, null);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		if (mState == STATE_EDIT) {
			menu.add(0, REVERT_ID, 0, R.string.menu_revert).setShortcut('0', 'r').setIcon(android.R.drawable.ic_menu_revert);
			menu.add(0, DELETE_ID, 0, R.string.menu_delete).setShortcut('0', 'd').setIcon(android.R.drawable.ic_menu_delete);

		} else {
			menu.add(0, DISCARD_ID, 0, R.string.menu_discard).setShortcut('0', 'd').setIcon(android.R.drawable.ic_menu_delete);
		}
		menu.add(0, CALL_ID, 0, R.string.service_call).setShortcut('5', 'f').setIcon(android.R.drawable.ic_menu_call);
		menu.add(0, SENTMESS_ID, 0, R.string.service_message).setShortcut('6', 'g').setIcon(android.R.drawable.ic_menu_agenda);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case DELETE_ID:
			deleteContact();
			finish();
			break;
		case DISCARD_ID:
			cancelContact();
			break;
		case REVERT_ID:
			backupContact();
			break;
		case CALL_ID:
			//TODO 拨号
			Cursor cursor = managedQuery(ContentUris.withAppendedId(ContactsProvider.CONTENT_URI, id), ContactColumn.PROJECTION, null,null, null);
			cursor.moveToFirst();
			intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+cursor.getString(cursor.getColumnIndex(ContactColumn.MOBILE))));   
	        this.startActivity(intent);   
	        return true;
		case SENTMESS_ID:
			intent = new Intent(Intent.ACTION_SEND, ContentUris.withAppendedId(ContactsProvider.CONTENT_URI, id));
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// 删除联系人信息
	private void deleteContact() {
		if (mCursor != null) {
			mCursor.close();
			mCursor = null;
			getContentResolver().delete(mUri, null, null);
			nameText.setText("");
		}

	}

	// 丢弃信息
	private void cancelContact() {
		if (mCursor != null) {
			deleteContact();
		}
		setResult(RESULT_CANCELED);
		finish();

	}

	// 更新 变更的信息
	private void updateContact() {
		if (mCursor != null) {
			mCursor.close();
			mCursor = null;
			ContentValues values = new ContentValues();
			values.put(ContactColumn.NAME, nameText.getText().toString());
			values.put(ContactColumn.MOBILE, mPhoneText.getText().toString());
			values.put(ContactColumn.EMAIL, emailText.getText().toString());
			values.put(ContactColumn.MODIFIED, Tools.getTime());
			values.put(ContactColumn.ADDRESS, addressText.getText().toString());
			values.put(ContactColumn.HOMENUM, homeNumText.getText().toString());
			values.put(ContactColumn.GROUPNUM, groupIndexMap.get(spaSpinner.getSelectedItemPosition()));
			
			Log.e(TAG + ":onPause", mUri.toString());
			Log.e(TAG + ":onPause", values.toString());
			getContentResolver().update(mUri, values, null, null);
		}
		setResult(RESULT_CANCELED);
		finish();

	}

	// 取消用，回退到最初的信息
	private void backupContact() {
		if (mCursor != null) {
			mCursor.close();
			mCursor = null;
			ContentValues values = new ContentValues();
			values.put(ContactColumn.NAME, this.originalNameText);
			values.put(ContactColumn.MOBILE, this.originalMPhoneText);
			values.put(ContactColumn.EMAIL, this.originalEmailText);
			Log.e(TAG + ":onPause", mUri.toString());
			Log.e(TAG + ":onPause", values.toString());
			getContentResolver().update(mUri, values, null, null);
		}
		setResult(RESULT_CANCELED);
		finish();

	}

}

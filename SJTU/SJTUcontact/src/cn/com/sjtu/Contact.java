package cn.com.sjtu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.util.Constants;
import com.util.Tools;
import com.util.User;

public class Contact extends ListActivity {
	private static final String TAG = "Contacts";
	private ListView myListView;
	private Button backToGroup;
	private Button search;
	private Button sort;
	private Button add;
	private EditText searchArea;
	private String fileName;
	private LayoutInflater layoutInflater;
	private View viewAddEmployee;
	private Spinner fileNameSpinner;
	private RadioGroup fileTypeGroup;
	private RadioGroup codeStyleGroup;

	private static final int ExportContact_ID = Menu.FIRST;
	private static final int EditContact_ID = Menu.FIRST + 1;
	private static final int CallContact_ID = Menu.FIRST + 2;
	private static final int SendMess_ID = Menu.FIRST + 3;
	private static final int IMPORT_ID = Menu.FIRST + 4;
	
	/**
	 * 排序标志（0：姓名排序；1：手机排序）
	 */
	private int sortInt = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		myListView = (ListView) findViewById(android.R.id.list);
		search = (Button) findViewById(R.id.submitId);
		sort = (Button) findViewById(R.id.sort);
		add = (Button) findViewById(R.id.add);
		backToGroup = (Button) findViewById(R.id.backToGroup);
		searchArea = (EditText) findViewById(R.id.searchArea);

		// 启用快捷键支持
		setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);

		// 获取/设置Intent，用于从ContactsProvider中提取通讯录内容
		Intent intent = getIntent();
		if (intent.getData() == null) {
			intent.setData(ContactsProvider.CONTENT_URI);
		}

		// 启用长按支持，弹出的上下文菜单在
		getListView().setOnCreateContextMenuListener(this);

		// 使用managedQuery获取ContactsProvider的Cursor
		Cursor cursor = null;
		if (getIntent().getData().toString().indexOf(ContactsProvider.CONTENT_URI.toString()) != -1) {
			cursor = managedQuery(getIntent().getData(), ContactColumn.PROJECTION, null, null, ContactColumn.NAME);
		}
		else {
			cursor = managedQuery(ContactsProvider.CONTENT_URI, ContactColumn.PROJECTION, ContactColumn.GROUPNUM + " = ?", new String[] { getIntent().getData().getPathSegments().get(1) }, ContactColumn.NAME);
			intent.setData(ContactsProvider.CONTENT_URI);
		}

		// 使用SimpleCursorAdapter建立Cursor的Adapter以便使用，数据表示形式为：姓名 - 手机号码
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.contact_list_item, cursor, new String[] { ContactColumn.NAME, ContactColumn.MOBILE }, new int[] { R.id.name, R.id.contactinfo });

		// 为当前ListView关联Adapter
		setListAdapter(adapter);
		// myListView.setAdapter(adapter);
		initButtonAction();
		initEditAction();
		Log.e(TAG + "onCreate", " is ok");

	}

	// 目录建立的回调函数
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// 在目录中增加“添加”按钮并为之设定快捷键及图标
		menu.add(0, ExportContact_ID, 0, R.string.menu_export).setShortcut('4', 'e').setIcon(android.R.drawable.ic_menu_set_as);
		if (myListView.getSelectedItem() != null) {
			menu.add(0, CallContact_ID, 0, R.string.service_call).setShortcut('5', 'f').setIcon(android.R.drawable.ic_menu_call);
			menu.add(0, SendMess_ID, 0, R.string.service_message).setShortcut('6', 'g').setIcon(android.R.drawable.ic_menu_agenda);
		}
		menu.add(0, IMPORT_ID, 0, R.string.menu_import).setShortcut('7', 'h').setIcon(android.R.drawable.ic_menu_upload);
		return true;
	}

	// 目录显示之前的回调函数
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		final boolean haveItems = getListAdapter().getCount() > 0;

		// 如果当前列表不为空
		if (haveItems) {
			Uri uri = ContentUris.withAppendedId(getIntent().getData(), getSelectedItemId());

			Intent[] specifics = new Intent[1];
			specifics[0] = new Intent(Intent.ACTION_EDIT, uri);
			MenuItem[] items = new MenuItem[1];

			Intent intent = new Intent(null, uri);
			intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
			menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0, null, specifics, intent, 0, items);

			// 如果有CATEGORY_ALTERNATIVE类型的菜单项,即编辑选项，被加入，则为之添加快捷键
			if (items[0] != null) {
				items[0].setShortcut('1', 'e');
			}
		}
		else {
			menu.removeGroup(Menu.CATEGORY_ALTERNATIVE);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		long id;
		Intent intent;

		switch (item.getItemId()) {
		case ExportContact_ID:
			initLayOutView(ExportContact_ID);
			new AlertDialog.Builder(this).setTitle(getText(R.string.is_export)).setView(viewAddEmployee).setPositiveButton(getText(R.string.code_export), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					fileName = ((EditText) viewAddEmployee.findViewById(R.exportid.fileNameId)).getText().toString();
					if (fileTypeGroup.getCheckedRadioButtonId() == R.exportid.xml) {
						if (codeStyleGroup.getCheckedRadioButtonId() == R.exportid.des) {
							exportXML(fileName, true, Constants.DES);
						}
						else {
							exportXML(fileName, true, Constants.USUAL);
						}
					}
					else {
						if (codeStyleGroup.getCheckedRadioButtonId() == R.exportid.des) {
							exportExcel(fileName, true, Constants.DES);
						}
						else {
							exportExcel(fileName, true, Constants.USUAL);
						}
					}
				}

			}).setNeutralButton(getText(R.string.uncode_export), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					fileName = ((EditText) viewAddEmployee.findViewById(R.exportid.fileNameId)).getText().toString();
					if (fileTypeGroup.getCheckedRadioButtonId() == R.exportid.xml) {
						exportXML(fileName, false, -1);
					}
					else {
						exportExcel(fileName, false, -1);
					}
				}

			}).setNegativeButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

				}
			}).show();
			return true;
		case CallContact_ID:
			id = myListView.getSelectedItemId();
			Cursor cursor = managedQuery(ContentUris.withAppendedId(ContactsProvider.CONTENT_URI, id), ContactColumn.PROJECTION, null, null, null);
			cursor.moveToFirst();
			intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + cursor.getString(cursor.getColumnIndex(ContactColumn.MOBILE))));
			this.startActivity(intent);
			return true;
		case SendMess_ID:
			id = myListView.getSelectedItemId();
			intent = new Intent(Intent.ACTION_SEND, ContentUris.withAppendedId(ContactsProvider.CONTENT_URI, id));
			startActivity(intent);
			return true;
		case IMPORT_ID:
			// TODO 导入
			initLayOutView(IMPORT_ID);
			new AlertDialog.Builder(this).setTitle(getText(R.string.is_import)).setView(viewAddEmployee).setPositiveButton(getText(R.string.menu_import), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String fileName = getFileFromSdcard();
					importContact(fileName);
				}

			}).setNegativeButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			}).show();
			return true;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	protected void importContact(String fileName2) {
		boolean flag = false;
		List<User> returnValue = new ArrayList<User>();
		XmlTools xmlTools = new XmlTools();
		ExcelTools excelTools = new ExcelTools();
		try {
			if (fileName2.indexOf(".xls") != -1) {
				returnValue = excelTools.readExcel(fileName2);
			}
			else {
				returnValue = xmlTools.readXml(fileName2);
			}
			for (User user : returnValue) {
				ContentValues value = new ContentValues();
				value.put(ContactColumn.NAME, user.getName());
				value.put(ContactColumn.MOBILE, user.getMobileNumber());
				value.put(ContactColumn.EMAIL, user.getEmail());
				value.put(ContactColumn.MODULE, user.getModule());
				value.put(ContactColumn.POSTNUM, user.getPostnum());
				value.put(ContactColumn.GROUPNUM, user.getGroupnum());
				value.put(ContactColumn.ADDRESS, user.getAddress());
				value.put(ContactColumn.HOMENUM, user.getHomenum());
				value.put(ContactColumn.JOB, user.getJob());
				value.put(ContactColumn.JOBNUM, user.getJobnum());

				getContentResolver().insert(ContactsProvider.CONTENT_URI, value);
			}
			flag = true;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (BiffException e) {
			e.printStackTrace();
		}
		if (flag) {
			Toast.makeText(Contact.this, "导入成功", Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(Contact.this, "导入失败", Toast.LENGTH_SHORT).show();
		}
	}

	private String getFileFromSdcard() {
		Object selectItem = fileNameSpinner.getSelectedItem();
		String fileName = Tools.getContactSavePath() + (selectItem == null ? "" : selectItem.toString());
		return fileName;
	}

	private void initLayOutView(int initcase) {
		switch (initcase) {
		case ExportContact_ID:
			layoutInflater = LayoutInflater.from(this);
			viewAddEmployee = layoutInflater.inflate(R.layout.exportview, null);
			fileTypeGroup = (RadioGroup) viewAddEmployee.findViewById(R.exportid.filetype);
			codeStyleGroup = (RadioGroup) viewAddEmployee.findViewById(R.exportid.codestyle);
			break;
		case IMPORT_ID:
			File filePath = new File(Tools.getContactSavePath());
			String[] fileNames = new String[filePath.list().length];
			for (int i = 0; i < fileNames.length; i++) {
				fileNames[i] = filePath.list()[i];
			}
			layoutInflater = LayoutInflater.from(this);
			ArrayAdapter<String> fileNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fileNames);
			viewAddEmployee = layoutInflater.inflate(R.layout.importview, null);
			fileNameSpinner = (Spinner) viewAddEmployee.findViewById(R.importid.fileNameId);
			fileNameSpinner.setAdapter(fileNameAdapter);
			break;
		default:
			break;
		}

	}

	// 上下文菜单，本例会通过长按条目激活上下文菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info;
		try {
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		}
		catch (ClassCastException e) {
			return;
		}

		Cursor cursor = (Cursor) getListAdapter().getItem(info.position);
		if (cursor == null) {
			return;
		}

		menu.setHeaderTitle(cursor.getString(1));

		menu.add(0, EditContact_ID, 0, R.string.menu_delete);
	}

	// 上下文菜单选择的回调函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info;
		try {
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		}
		catch (ClassCastException e) {
			return false;
		}

		switch (item.getItemId()) {
		// 选择编辑条目
		case EditContact_ID: {
			Uri noteUri = ContentUris.withAppendedId(getIntent().getData(), info.id);
			getContentResolver().delete(noteUri, null, null);
			return true;
		}
		}
		return false;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);

//		String action = getIntent().getAction();
//		if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {
//			// 如果通讯录列表的Activity是被其他Activity调用以返回选择的通讯信息
//			// 比如，短信程序通过本例来获取某人的电话号码
//			setResult(RESULT_OK, new Intent().setData(uri));
//		}
//		else {
//			// 编辑 联系人
			startActivity(new Intent(Intent.ACTION_EDIT, uri));
//		}
	}

	private void initButtonAction() {
		backToGroup.setOnClickListener(new BackToGroupAction());
		add.setOnClickListener(new AddAction());
		search.setOnClickListener(new SearchAction(this));
		sort.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final CharSequence[] items = {"姓名", "手机"};  
				Dialog dialog = new AlertDialog.Builder(Contact.this).setTitle("设置排序").setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sortInt = which;
					}
				}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SearchMethod();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sortInt = 0;
					}
				}).create();
		
				dialog.show();
			}
		});
	}

	private void initEditAction() {
		searchArea.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (searchArea.getText().toString().equals("查询区域"))
					searchArea.setText("");
			}
		});
	}

	class SearchAction implements OnClickListener {

		private Contact contact;

		public SearchAction(Contact contact) {
			this.contact = contact;
		}

		public void onClick(View v) {
			SearchMethod();
		}
	}
	
	private void SearchMethod() {
		String str = searchArea.getText().toString();
		
		Cursor cursor;
		if (sortInt == 0)
			cursor = managedQuery(getIntent().getData(), ContactColumn.PROJECTION, ContactColumn.NAME + " like ?", new String[] { "%" + str + "%" }, ContactColumn.NAME);
		else if (sortInt == 1)
			cursor = managedQuery(getIntent().getData(), ContactColumn.PROJECTION, ContactColumn.NAME + " like ?", new String[] { "%" + str + "%" }, ContactColumn.MOBILE);
		else
			cursor = managedQuery(getIntent().getData(), ContactColumn.PROJECTION, ContactColumn.NAME + " like ?", new String[] { "%" + str + "%" }, ContactColumn.NAME);

		// 使用SimpleCursorAdapter建立Cursor的Adapter以便使用，数据表示形式为：姓名 - 手机号码
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(Contact.this, R.layout.contact_list_item, cursor, new String[] { ContactColumn.NAME, ContactColumn.MOBILE }, new int[] { R.id.name, R.id.contactinfo });

		// 为当前ListView关联Adapter
		setListAdapter(adapter);
	}

	class BackToGroupAction implements OnClickListener {
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setClass(Contact.this, ShowGroup.class);
			Contact.this.startActivity(intent);
		}
	}

	class AddAction implements OnClickListener {
		public void onClick(View v) {
			startActivity(new Intent(Intent.ACTION_INSERT, getIntent().getData()));
		}
	}

	protected void exportExcel(String fileName2, boolean b, int codeStyle) {
		int returnValue = 0;
		ExcelTools excelTools = new ExcelTools();
		Cursor cur = getContentResolver().query(getIntent().getData(), ContactColumn.USER, null, null, null);
		Cursor cursor = managedQuery(GroupProvider.GROUP_URI, ContactColumn.GROUPPRO, null, null, null);
		try {
			returnValue = excelTools.writeExcel(fileName2.equals("") ? null : fileName2 + ".xls", Tools.cursor2User(cur, Tools.getIdColumnMap(cursor), b, codeStyle), codeStyle);
			returnValue = 1;
		}
		catch (BiffException e) {
			e.printStackTrace();
		}
		catch (WriteException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		if (1 == returnValue)
			Toast.makeText(Contact.this, "导出成功", Toast.LENGTH_SHORT).show();
		if (0 == returnValue)
			Toast.makeText(Contact.this, "导出失败", Toast.LENGTH_SHORT).show();
		cur.close();
		cursor.close();
	}

	private void exportXML(String fileName, boolean isCode, int codeStyle) {

		int returnValue = 0;
		XmlTools xmlTools = new XmlTools();
		Cursor cur = getContentResolver().query(getIntent().getData(), ContactColumn.USER, null, null, null);
		Cursor cursor = managedQuery(GroupProvider.GROUP_URI, ContactColumn.GROUPPRO, null, null, null);
		try {
			returnValue = xmlTools.writeXml(fileName.equals("") ? null : fileName + ".xml", Tools.cursor2User(cur, Tools.getIdColumnMap(cursor), isCode, codeStyle), isCode, codeStyle);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();

		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		if (1 == returnValue)
			Toast.makeText(Contact.this, "导出成功", Toast.LENGTH_SHORT).show();
		if (0 == returnValue)
			Toast.makeText(Contact.this, "导出失败", Toast.LENGTH_SHORT).show();
		cur.close();
		cursor.close();
	}
}
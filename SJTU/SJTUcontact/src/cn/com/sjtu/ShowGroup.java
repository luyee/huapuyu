package cn.com.sjtu;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShowGroup extends ListActivity {
	
	
	private static final int AddContact_ID = Menu.FIRST;
	private static final int EditContact_ID = Menu.FIRST + 1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_list);
		
		Intent intent = getIntent();
	    if (intent.getData() == null) {
	          intent.setData(GroupProvider.GROUP_URI);
	    }
		// 启用长按支持，弹出的上下文菜单在
		getListView().setOnCreateContextMenuListener(this);
		
		
		Cursor cursor = managedQuery(getIntent().getData(),
				ContactColumn.GROUPPRO, null, null, null);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.group_list_item, cursor,
				new String[] { ContactColumn.GROUP_NAME }, new int[] { R.id.name });
		setListAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// 在目录中增加“添加”按钮并为之设定快捷键及图标
		menu.add(0,AddContact_ID, 0, R.string.group_add).setShortcut('3', 'a')
				.setIcon(android.R.drawable.ic_menu_add);

		return true;

	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case AddContact_ID:
        	Intent intent = new Intent();
			intent.setClass(ShowGroup.this, AddGroupView.class);
			ShowGroup.this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info;
		try {
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		} catch (ClassCastException e) {
			return false;
		}
		switch (item.getItemId()) {
			case EditContact_ID: {
				Uri noteUri = ContentUris.withAppendedId(getIntent().getData(), info.id);
				getContentResolver().delete(noteUri, null, null);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info;
		try {
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		} catch (ClassCastException e) {
			return;
		}
		if(0 == info.position){
			return;
		}
		Cursor cursor = (Cursor) getListAdapter().getItem(info.position);
		if (cursor == null) {
			return;
		}
		
		menu.setHeaderTitle("分组  "+cursor.getString(1));

		menu.add(0, EditContact_ID, 0, R.string.menu_delete);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if(0 == position) {
			startActivity(new Intent(Intent.ACTION_EDIT, ContactsProvider.CONTENT_URI));
		}else{
//			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, ContentUris.withAppendedId(ContactsProvider.CONTENT_URI, id));
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, ContentUris.withAppendedId(GroupProvider.GROUP_URI, id));
			startActivity(intent);
		}
	}
}

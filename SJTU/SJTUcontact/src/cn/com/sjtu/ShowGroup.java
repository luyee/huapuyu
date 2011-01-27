package cn.com.sjtu;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import cn.com.sjtu.ContactColumn;
import cn.com.sjtu.R;

public class ShowGroup extends ListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_list);
		Cursor cursor = managedQuery(GroupProvider.GROUP_URI, ContactColumn.PROJECTION, null, null, null);

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.group_list_item, cursor, new String[] { ContactColumn.NAME }, new int[] { R.id.name });
		setListAdapter(adapter);
	}
}

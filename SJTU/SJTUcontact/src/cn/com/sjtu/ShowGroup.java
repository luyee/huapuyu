package cn.com.sjtu;


import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import cn.com.sjtu.ContactColumn;
import cn.com.sjtu.R;

public class ShowGroup extends ListActivity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_list);
		Cursor cursor = managedQuery(getIntent().getData(), ContactColumn.PROJECTION, null, null,null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.contact_list_item, cursor,
                new String[] { ContactColumn.NAME,ContactColumn.MOBILE }, new int[] { R.id.name, R.id.contactinfo });
        setListAdapter(adapter);
	}
}

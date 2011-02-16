
package cn.com.sjtu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "gcontacts.db";
	public static final int DATABASE_VERSION = 2;
	public static final String CONTACTS_USER_TABLE = "contacts_user";
	public static final String CONTACTS_GROUP_TABLE = "contacts_group";
	// 创建数据库
	private static final String DATABASE_USER_CREATE = "CREATE TABLE " + CONTACTS_USER_TABLE + " (" + ContactColumn._ID + " integer primary key autoincrement," + ContactColumn.NAME + " text," + ContactColumn.MOBILE + " text," + ContactColumn.EMAIL + " text," + ContactColumn.POSTNUM + " test," + ContactColumn.MODULE + " test," + ContactColumn.JOB  + " test,"+ ContactColumn.JOBNUM  + " test," + ContactColumn.GROUPNUM + " integer,"+ ContactColumn.HOMENUM + " text,"+ ContactColumn.ADDRESS + " text);";
	private static final String DATABASE_GROUP_CREATE = "CREATE TABLE " + CONTACTS_GROUP_TABLE + "(" + ContactColumn._ID + " integer primary key autoincrement," + ContactColumn.GROUP_NAME + " text)";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_USER_CREATE);
		db.execSQL(DATABASE_GROUP_CREATE);
		db.execSQL("insert into "+CONTACTS_GROUP_TABLE+" ("+ContactColumn.GROUP_NAME+") values ('全部');");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_USER_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_GROUP_TABLE);
		onCreate(db);
	}

}

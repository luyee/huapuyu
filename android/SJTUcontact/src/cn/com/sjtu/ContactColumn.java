package cn.com.sjtu;

import android.provider.BaseColumns;

import com.util.Constants;

public class ContactColumn implements BaseColumns {
	public ContactColumn() {
	}

	// User 列名
	public static final String NAME = "name";
	public static final String MOBILE = "mobileNumber";
	public static final String EMAIL = "email";
	public static final String GROUPNUM = "groupnum";
	public static final String HOMENUM = "homenum";
	public static final String ADDRESS = "address";

	public static final String POSTNUM = Constants.POSTNUM;
	public static final String MODULE = Constants.MODULE;
	public static final String JOB = Constants.JOB;
	public static final String JOBNUM = Constants.JOBNUM;

	// Group 列名
	public static final String GROUP_NAME = "group_name";
	// 列 索引值
	public static final int _ID_COLUMN = 0;
	public static final int NAME_COLUMN = 1;
	public static final int MOBILE_COLUMN = 2;
	public static final int EMAIL_COLUMN = 3;
	public static final int CREATED_COLUMN = 4;
	public static final int MODIFIED_COLUMN = 5;
	public static final int GROUP_COLUMN = 6;

	// 查询结果
	public static final String[] PROJECTION = { _ID,// 0
			NAME,// 1
			MOBILE,// 2
			EMAIL,// 3
			GROUPNUM, // 6
			HOMENUM, ADDRESS, POSTNUM,// 6
			MODULE, JOB, JOBNUM };

	public static final String[] USER = { _ID,// 0
			NAME,// 1
			MOBILE,// 2
			EMAIL,// 3
			POSTNUM,// 6
			MODULE, GROUPNUM, HOMENUM, ADDRESS, JOB, JOBNUM };

	public static final String[] GROUPPRO = { _ID, GROUP_NAME };

}

package com.anders.anniversary.activity;

import java.sql.SQLException;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.anders.anniversary.bo.Account;
import com.anders.anniversary.dao.DataHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

public class OrmliteActivity extends OrmLiteBaseActivity<DataHelper> {

	private Button button1 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ormlite);
		button1 = (Button) findViewById(R.id.btnOrmlite);
		button1.setText(R.string.ormlite);
		button1.setOnClickListener(new Button1Listener());
	}

	class Button1Listener implements OnClickListener {
		@Override
		public void onClick(View v) {
			try {
				Dao<Account, Integer> AccountDao = getHelper().getHelloDataDao();
				Log.e(OrmliteActivity.class.getName(), "添加数据");
				// 添加数据
				for (int i = 0; i < 2; i++) {
					Account Account = new Account("Account" + i);
					AccountDao.create(Account);
				}

				Log.e(OrmliteActivity.class.getName(), "查询添加的数据");
				// 查询添加的数据
				List<Account> Accounts = AccountDao.queryForAll();
				for (Account h : Accounts) {
					Log.e(OrmliteActivity.class.getName(), h.getName());
				}

				Log.e(OrmliteActivity.class.getName(), "删除数据第一条数据");
				// 删除数据第一条数据
				AccountDao.delete(Accounts.get(0));

				Log.e(OrmliteActivity.class.getName(), "重新查询数据");
				// 重新查询数据
				Accounts = AccountDao.queryForAll();
				for (Account h : Accounts) {
					Log.e(OrmliteActivity.class.getName(), h.getName());
				}

				Log.e(OrmliteActivity.class.getName(), "修改数据");
				// 修改数据
				Account h1 = Accounts.get(0);
				h1.setName("ggggggggg");
				AccountDao.update(h1);

				Log.e(OrmliteActivity.class.getName(), "重新查询数据");
				// 重新查询数据
				Accounts = AccountDao.queryForAll();
				for (Account h : Accounts) {
					Log.e(OrmliteActivity.class.getName(), h.getName());
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

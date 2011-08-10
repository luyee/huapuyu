package com.baidu.rigel.unique.tinyse.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baidu.rigel.service.tinyse.data.SaleData;
import com.baidu.rigel.service.tinyse.data.ShifenData;
import com.baidu.rigel.unique.tinyse.TinyseMgr;

@Service("tinyseMgr")
public class MockTinyseMgrImpl implements TinyseMgr {

	public boolean addSaleData(Long custId, String companyname) {
		return false;
	}

	public boolean modSaleData(Long custId, String companyname) {
		return false;
	}

	public boolean delSaleData(List<Long> ids) {
		return false;
	}

	public List<SaleData> querySaleData(String query) {
		return new ArrayList<SaleData>(0);
	}

	public List<SaleData> querySaleData(String query, int limit) {
		return new ArrayList<SaleData>(0);
	}

	public List<SaleData> querySaleData(String query, int pageNum, int limit) {
		return new ArrayList<SaleData>(0);
	}

	public List<ShifenData> queryShifenData(String query, int limit) {
		return new ArrayList<ShifenData>(0);
	}

	public List<SaleData> querySaleDataWithHold(String query, int limit) {
		return new ArrayList<SaleData>(0);
	}

	public List<ShifenData> queryShifenDataWithHold(String query, int limit) {
		return new ArrayList<ShifenData>(0);
	}

	public List<SaleData> querySaleDataWithWordPhrase(String query, int limit) {
		return new ArrayList<SaleData>(0);
	}

	public List<SaleData> querySaleDataWithWordPhrase(String query, int pageNum, int limit) {
		return new ArrayList<SaleData>(0);
	}

	public List<ShifenData> queryShifenDataWithWordPhrase(String query, int limit) {
		List<ShifenData> list = new ArrayList<ShifenData>();
		ShifenData shifenData1 = new ShifenData();
		shifenData1.setId(1);
		shifenData1.setRealcompanyname("realcompanyname");
		shifenData1.setCompanyname("companyname");
		shifenData1.setCustomername("customername");
		shifenData1.setPhone("12345678");
		list.add(shifenData1);

		ShifenData shifenData2 = new ShifenData();
		shifenData2.setId(2);
		shifenData2.setCompanyname("companyname");
		shifenData2.setCustomername("customername");
		shifenData2.setPhone("12345678");
		list.add(shifenData2);

		ShifenData shifenData3 = new ShifenData();
		shifenData3.setId(3);
		shifenData3.setCustomername("customername");
		shifenData3.setPhone("12345678");
		list.add(shifenData3);

		return list;
	}

	public List<SaleData> querySeasonCustDataWithHold(String query, int limit) {
		List<SaleData> list = new ArrayList<SaleData>();
		SaleData saleData = new SaleData();
		saleData.setId(1234);
		saleData.setCompanyname("zhangsan");
		list.add(saleData);
		return list;
	}

	public String removeNoneCoreWord(String query) {
		return "";
	}

	public String removeAreaWord(String query, Long posId) {
		return "";
	}

	public boolean addSeasonCustData(Long seasonCustId, String companyname) {
		return true;
	}

	public boolean delSeasonCustData(List<Long> ids) {
		// TODO Auto-generated method stub
		return false;
	}
}

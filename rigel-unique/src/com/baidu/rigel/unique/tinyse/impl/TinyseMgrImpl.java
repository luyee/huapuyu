package com.baidu.rigel.unique.tinyse.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.baidu.rigel.service.tinyse.client.TinyseClient;
import com.baidu.rigel.service.tinyse.data.SaleData;
import com.baidu.rigel.service.tinyse.data.ShifenData;
import com.baidu.rigel.service.tinyse.data.TermInfo;
import com.baidu.rigel.service.tinyse.protocol.TsHead;
import com.baidu.rigel.unique.tinyse.TinyseMgr;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class TinyseMgrImpl implements TinyseMgr, InitializingBean {

	private static Log log = LogFactory.getLog(TinyseMgrImpl.class);

	private List<TinyseClient> saleClients;
	private List<TinyseClient> shifenClients;

	private static Gson gson = new GsonBuilder().serializeNulls().create();

	private static Type listOfSaleData = new TypeToken<List<SaleData>>() {
	}.getType();

	private static Type listOfShifenData = new TypeToken<List<ShifenData>>() {
	}.getType();

	private static Type listOfTermInfo = new TypeToken<List<TermInfo>>() {
	}.getType();

	public boolean addSaleData(Long custId, String companyname) {
		if (custId == null || companyname == null) {
			return false;
		}
		SaleData sd = new SaleData();
		sd.setId(custId);
		sd.setCompanyname(companyname);
		boolean flag = true;
		for (TinyseClient sc : saleClients) {
			try {
				sc.add(sd);
			} catch (IOException e) {
				log.error(sc.getUpdateServer() + " addSaleData error. custid:" + custId + " companyname:" + companyname, e);
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}

	public boolean modSaleData(Long custId, String companyname) {
		if (custId == null || companyname == null) {
			return false;
		}
		SaleData sd = new SaleData();
		sd.setId(custId);
		sd.setCompanyname(companyname);
		boolean flag = true;
		for (TinyseClient sc : saleClients) {
			try {
				sc.mod(sd);
			} catch (IOException e) {
				log.error(sc.getUpdateServer() + " modSaleData error. custid:" + custId + " companyname:" + companyname, e);
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}

	public boolean delSaleData(List<Long> ids) {
		boolean flag = true;
		for (TinyseClient sc : saleClients) {
			try {
				sc.del(ids);
			} catch (IOException e) {
				String idlist = StringUtils.join(ids, ",");
				log.error(sc.getUpdateServer() + " delSaleData error. ids:" + idlist, e);
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}

	public List<SaleData> querySaleData(String query) {
		return querySaleData(query, MAX_NUM_LIMIT);
	}

	public List<SaleData> querySaleData(String query, int limit) {
		List<TinyseClient> randomList = saleClients;
		// shuffle
		if (randomList.size() > 1) {
			randomList = new ArrayList<TinyseClient>(randomList);
			Collections.shuffle(randomList);
		}
		for (TinyseClient sc : randomList) {
			try {
				List<SaleData> list = new ArrayList<SaleData>(0);
				TsHead tshead = new TsHead();
				String json = sc.queryWithBasic(tshead, query, 0, limit);
				if (json == null || json.length() == 0)
					return list;
				if (tshead.getDispNum() > 0) {
					list = gson.fromJson(json, listOfSaleData);
				}
				return list;
			} catch (IOException e) {
				log.error(sc.getQueryServer() + " querySaleData error. query:" + query, e);
				e.printStackTrace();
			}
		}
		return new ArrayList<SaleData>(0);
	}

	public List<SaleData> querySaleData(String query, int pageNum, int limit) {
		List<TinyseClient> randomList = saleClients;
		// shuffle
		if (randomList.size() > 1) {
			randomList = new ArrayList<TinyseClient>(randomList);
			Collections.shuffle(randomList);
		}
		for (TinyseClient sc : randomList) {
			try {
				List<SaleData> list = new ArrayList<SaleData>(0);
				TsHead tshead = new TsHead();
				String json = sc.queryWithBasic(tshead, query, pageNum, limit);
				if (json == null || json.length() == 0)
					return list;

				if (tshead.getDispNum() > 0) {
					list = gson.fromJson(json, listOfSaleData);
				}
				return list;
			} catch (IOException e) {
				log.error(sc.getQueryServer() + " querySaleData error. query:" + query, e);
				e.printStackTrace();
			}
		}
		return new ArrayList<SaleData>(0);
	}

	public List<ShifenData> queryShifenData(String query, int limit) {
		List<TinyseClient> randomList = shifenClients;
		// shuffle
		if (randomList.size() > 1) {
			randomList = new ArrayList<TinyseClient>(randomList);
			Collections.shuffle(randomList);
		}
		for (TinyseClient sc : randomList) {
			try {
				List<ShifenData> list = new ArrayList<ShifenData>(0);
				TsHead tshead = new TsHead();
				String json = sc.queryWithBasic(tshead, query, 0, limit);
				if (json == null || json.length() == 0)
					return list;
				if (tshead.getDispNum() > 0) {
					list = gson.fromJson(json, listOfShifenData);
				}
				return list;
			} catch (IOException e) {
				log.error(sc.getQueryServer() + " queryShifenData error. query:" + query, e);
				e.printStackTrace();
			}
		}
		return new ArrayList<ShifenData>(0);
	}

	public List<SaleData> querySaleDataWithHold(String query, int limit) {
		if (query == null || query.length() == 0) {
			throw new RuntimeException("公司名称不合法");
		}
		List<TinyseClient> randomList = saleClients;
		// shuffle
		if (randomList.size() > 1) {
			randomList = new ArrayList<TinyseClient>(randomList);
			Collections.shuffle(randomList);
		}
		for (TinyseClient sc : randomList) {
			try {
				List<SaleData> list = new ArrayList<SaleData>(0);
				TsHead tshead = new TsHead();
				String json = sc.query(tshead, query, 0, limit);
				if (json == null || json.length() == 0)
					return list;
				if (tshead.getDispNum() > 0) {
					return gson.fromJson(json, listOfSaleData);
				}
				List<TermInfo> terms = gson.fromJson(json, listOfTermInfo);
				int total = terms.size();
				terms = removeTerm(terms, total);
				while (terms != null) {
					query = term2query(terms);
					if (query == null)
						return list;
					json = sc.queryWithTerm(tshead, query, 0, limit);
					if (json == null || json.length() == 0)
						return list;
					if (tshead.getDispNum() > 0) {
						return gson.fromJson(json, listOfSaleData);
					}
					terms = removeTerm(terms, total);
				}
				return list;
			} catch (IOException e) {
				log.error(sc.getQueryServer() + " querySaleData error. query:" + query, e);
				e.printStackTrace();
			}
		}
		return new ArrayList<SaleData>(0);
	}

	public List<ShifenData> queryShifenDataWithHold(String query, int limit) {
		if (query == null || query.length() == 0) {
			throw new RuntimeException("公司名称不合法");
		}
		List<TinyseClient> randomList = shifenClients;
		// shuffle
		if (randomList.size() > 1) {
			randomList = new ArrayList<TinyseClient>(randomList);
			Collections.shuffle(randomList);
		}
		for (TinyseClient sc : randomList) {
			try {
				List<ShifenData> list = new ArrayList<ShifenData>(0);
				TsHead tshead = new TsHead();
				String json = sc.query(tshead, query, 0, limit);
				if (json == null || json.length() == 0)
					return list;
				if (tshead.getDispNum() > 0) {
					return gson.fromJson(json, listOfShifenData);
				}
				List<TermInfo> terms = gson.fromJson(json, listOfTermInfo);
				int total = terms.size();
				terms = removeTerm(terms, total);
				while (terms != null) {
					query = term2query(terms);
					if (query == null) {
						return list;
					}
					json = sc.queryWithTerm(tshead, query, 0, limit);
					if (json == null || json.length() == 0) {
						return list;
					}
					if (tshead.getDispNum() > 0) {
						return gson.fromJson(json, listOfShifenData);
					}
					terms = removeTerm(terms, total);
				}
				return list;
			} catch (IOException e) {
				log.error(sc.getQueryServer() + " queryShifenData error. query:" + query, e);
				e.printStackTrace();
			}
		}
		return new ArrayList<ShifenData>(0);
	}

	private List<TermInfo> removeTerm(List<TermInfo> terms, int total) {
		// 排序
		Collections.sort(terms);
		int size = terms.size();
		// 删除为0的term
		while (terms.size() > 0 && terms.get(0).getIndnum() == 0) {
			terms.remove(0);
		}
		// 至少删除1个term，删除第一个term
		if (terms.size() > 0 && terms.size() == size) {
			terms.remove(0);
		}
		double rate = terms.size() * 1.0 / total;
		if (rate <= QUERY_THRESHOLD) {
			return null;
		}
		return terms;
	}

	private String term2query(List<TermInfo> terms) {
		if (terms == null || terms.size() == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (TermInfo ti : terms) {
			sb.append(" ").append(ti.getTerm());
		}
		String query = "";
		if (sb.length() > 0) {
			query = sb.substring(1);
		}
		return query.length() == 0 ? null : query;
	}

	/**
	 * 按照词组查询销售客户资料
	 * 
	 * @param query
	 * @param limit
	 * @return
	 */
	public List<SaleData> querySaleDataWithWordPhrase(String query, int limit) {
		return querySaleDataWithWordPhrase(query, 0, limit);
	}

	public List<SaleData> querySaleDataWithWordPhrase(String query, int pageNum, int limit) {
		List<TinyseClient> randomList = saleClients;
		// shuffle
		if (randomList.size() > 1) {
			randomList = new ArrayList<TinyseClient>(randomList);
			Collections.shuffle(randomList);
		}
		for (TinyseClient sc : randomList) {
			try {
				List<SaleData> list = new ArrayList<SaleData>(0);
				TsHead tshead = new TsHead();
				String json = sc.query(tshead, query, pageNum, limit);
				if (json == null || json.length() == 0) {
					return list;
				}
				if (tshead.getDispNum() > 0) {
					list = gson.fromJson(json, listOfSaleData);
				}
				return list;
			} catch (IOException e) {
				log.error(sc.getQueryServer() + " querySaleData error. query:" + query, e);
			}
		}
		return new ArrayList<SaleData>(0);
	}

	public List<ShifenData> queryShifenDataWithWordPhrase(String query, int limit) {
		List<TinyseClient> randomList = shifenClients;
		// shuffle
		if (randomList.size() > 1) {
			randomList = new ArrayList<TinyseClient>(randomList);
			Collections.shuffle(randomList);
		}
		for (TinyseClient sc : randomList) {
			try {
				List<ShifenData> list = new ArrayList<ShifenData>(0);
				TsHead tshead = new TsHead();
				String json = sc.query(tshead, query, 0, limit);
				if (json == null || json.length() == 0) {
					return list;
				}
				if (tshead.getDispNum() > 0) {
					list = gson.fromJson(json, listOfShifenData);
				}
				return list;
			} catch (IOException e) {
				log.error(sc.getQueryServer() + " queryShifenData error. query:" + query, e);
			}
		}
		return new ArrayList<ShifenData>(0);
	}

	public List<TinyseClient> getSaleClients() {
		return saleClients;
	}

	public void setSaleClients(List<TinyseClient> saleClients) {
		this.saleClients = saleClients;
	}

	public List<TinyseClient> getShifenClients() {
		return shifenClients;
	}

	public void setShifenClients(List<TinyseClient> shifenClients) {
		this.shifenClients = shifenClients;
	}

	public void afterPropertiesSet() throws Exception {
		Set<TinyseClient> set = new TreeSet<TinyseClient>(new Comparator<TinyseClient>() {
			public int compare(TinyseClient o1, TinyseClient o2) {
				if (o1.getUpdateServer() != null) {
					return o1.getUpdateServer().compareTo(o2.getUpdateServer());
				} else {
					return o1.getQueryServer().compareTo(o2.getQueryServer());
				}
			}
		});
		if (saleClients != null && saleClients.size() > 0) {
			// 删除重复配置
			set.addAll(saleClients);
			saleClients = new ArrayList<TinyseClient>(set);
		} else {
			throw new RuntimeException("saleClients未配置");
		}
		set.clear();
		if (shifenClients != null && shifenClients.size() > 0) {
			// 初始化shifenClients
			set.addAll(shifenClients);
			shifenClients = new ArrayList<TinyseClient>(set);
		} else {
			throw new RuntimeException("saleClients未配置");
		}
	}

	public List<SaleData> querySeasonCustDataWithHold(String query, int limit) {
		return new ArrayList<SaleData>(0);
	}

	public String removeNoneCoreWord(String query) {
		return null;
	}

	public String removeAreaWord(String query, Long posId) {
		return null;
	}

	public boolean addSeasonCustData(Long seasonCustId, String companyname) {
		return true;
	}

	public boolean delSeasonCustData(List<Long> ids) {
		return true;
	}
}

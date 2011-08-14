package com.baidu.rigel.unique.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.baidu.rigel.platform.vo.Page;
import com.baidu.rigel.service.usercenter.bean.Position;
import com.baidu.rigel.service.usercenter.bean.User;
import com.baidu.rigel.unique.bo.UrlWhitelist;
import com.baidu.rigel.unique.common.BaseActionSupport;
import com.baidu.rigel.unique.common.ResearchAware;
import com.baidu.rigel.unique.service.UrlWhitelistService;
import com.baidu.rigel.unique.utils.Constant;
import com.baidu.rigel.unique.utils.URLUtils;

/**
 * URL白名单设置
 * 
 * @author YanBing
 * 
 */
@SuppressWarnings("serial")
public class UrlWhitelistAction extends BaseActionSupport implements ResearchAware {

	private static final String RESEARCH_MAP_KEY = "RESEARCH_MAP_KEY";

	// FE 运营单位列表
	private List<Map<String, String>> unitlist;
	private Long unitid;

	// 运营单位列表
	private List<Position> unitPos;
	private Set<Long> unitPosids;

	// 查询结果
	private List<Map<String, Object>> whitelist;

	// post参数
	private String url;
	private Long id;

	// 查询区--分页参数
	private String queryId;

	// 返回结果中的提示信息
	private boolean result = true;

	private UrlWhitelistService urlWhitelistService;

	/**
	 * 查询条件:URL主域
	 */
	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * 类型:增加时传值
	 */
	private Short typeAdded;

	/**
	 * 查询条件:url
	 */
	// private String urlCond;
	// public String getUrlCond() {
	// return urlCond;
	// }
	//
	// public void setUrlCond(String urlCond) {
	// this.urlCond = urlCond;
	// }
	public Short getTypeAdded() {
		return typeAdded;
	}

	public void setTypeAdded(Short typeAdded) {
		this.typeAdded = typeAdded;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	/**
	 * 备注:增加时传值
	 */
	private String desc;

	/**
	 * 查询条件:类型
	 */
	private Short type;

	/**
	 * 查询条件:设定人
	 */
	private Long userID;

	/**
	 * 设定人列表
	 */
	private List<Map<String, String>> userNames;

	public List<Map<String, String>> getUserNames() {
		return userNames;
	}

	public void setUserNames(List<Map<String, String>> userNames) {
		this.userNames = userNames;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	protected void initPage() {
		if (this.page == null) {
			this.page = new Page();
		}
		if (this.page.getCur_page_num() == null) {
			this.page.setCur_page_num(Long.valueOf(Constant.FIRST_PAGE_NUM));
		}
		if (this.page.getPage_size() == null) {
			this.page.setPage_size(Long.valueOf(Constant.ROW_COUNT_PER_PAGE));
		}
		if (this.page.getTotal_page_num() == null) {
			this.page.setTotal_page_num(NumberUtils.LONG_ZERO);
		}

		if (url == null) {
			url = "http://";
		}
	}

	private void initUnitPos() {

		Long posid = ucHelper.getCurrentPos().getPosid();

		if (unitPos == null) {
			unitPos = userMgr.getUnitPos(posid);
		}

		if (unitPosids == null) {
			unitPosids = new HashSet<Long>();
			for (Position p : unitPos) {
				unitPosids.add(p.getPosid());
			}
		}
	}

	private void initUnitList() {

		initUnitPos();

		if (unitid == null || !unitPosids.contains(unitid)) {
			if (unitPos != null && unitPos.size() > 0) {
				unitid = unitPos.get(0).getPosid();
			}
		}

		if (unitlist == null) {
			unitlist = new ArrayList<Map<String, String>>();
			for (Position p : unitPos) {
				Map<String, String> mp = new HashMap<String, String>();
				mp.put(Constant.NAME, p.getPosname());
				mp.put(Constant.ID, p.getPosid().toString());

				unitlist.add(mp);
			}
		}
	}

	/**
	 * 查询白名单
	 * 
	 * @return
	 */
	public String queryWhitelist() {

		initPage();
		initUnitList();
		// TODO Anders Zhu : 为什么要形成死循环
		// if (unitid == null || !unitPosids.contains(unitid)) {
		// result = false;
		// reason = this.getText("whitelist.unit.error");
		// return queryWhitelist();
		// }

		int curpage = 0;
		int pagesize = Constant.ROW_COUNT_PER_PAGE;

		if (page != null) {
			if (page.getCur_page_num() != null) {
				curpage = page.getCur_page_num().intValue() - 1;
			}
			if (page.getPage_size() != null) {
				pagesize = page.getPage_size().intValue();
			}
		}

		// whitelist = whitelistMgr.queryWhitelist(unitid, curpage, pagesize);
		// Long total = whitelistMgr.countWhitelist(unitid);
		// TODO Anders Zhu : mock
		// Map searchMap = (Map) ((Map) ActionContext.getContext().getSession().get(RESEARCH_MAP_KEY)).get(this.getQueryId());
		Map searchMap = null;
		if (searchMap == null) {
			searchMap = new HashMap();
		}
		if (StringUtils.isNotBlank(domain)) {
			String newdomain = domain;
			if (!domain.toLowerCase().startsWith("http://") && !domain.toLowerCase().startsWith("https://")) {
				newdomain = "http://" + domain;
			}
			if (URLUtils.getDomain(newdomain) != null) {
				newdomain = URLUtils.getDomain(newdomain);
				searchMap.put("domain", newdomain);
			}
		}
		searchMap.put("page", page);
		// searchMap.put("domain", domain="aaa");
		// searchMap.put("type",type=(short)1);
		searchMap.put("userID", userID);
		searchMap.put("unitid", unitid);
		// whitelist = whitelistMgr.queryWhitelist(searchMap);
		// TODO Anders Zhu : mock
		if (unitid == null)
			unitid = 11L;
		whitelist = urlWhitelistService.pageList(unitid, (String) searchMap.get("domain"), null, userID, page.getCurPageNum().intValue() - 1, page.getPageSize().intValue());
		page.setTotalRecNum(urlWhitelistService.pageCount(unitid, (String) searchMap.get("domain"), null, userID));

		List<Long> ucids = new ArrayList<Long>();
		for (Map<String, Object> mp : whitelist) {
			ucids.add((Long) mp.get("ucid"));
		}

		Map<Long, User> users = userMgr.getUserMap(ucids);

		for (Map<String, Object> mp : whitelist) {
			Long ucid = (Long) mp.get("ucid");
			String ucname = users.get(ucid) == null ? "-" : users.get(ucid).getUcname();
			mp.put("user", ucname);

		}

		// Set<Long> ucidset = new HashSet<Long>();
		// for (Map<String, Object> mp : whitelist) {
		// ucidset.add((Long) mp.get("ucid"));
		// }
		// Map ucMap = new HashMap();
		// ucMap.put("unitid", unitid);
		// List ucidList = whitelistMgr.getUCidList(ucMap);
		List<Long> ucidList = urlWhitelistService.selectDisUpdateIdByPosId(unitid);
		Map<Long, User> userset = userMgr.getUserMap(ucidList);
		users = userMgr.getUserMap(ucidList);
		userNames = new ArrayList<Map<String, String>>();
		Map tempMap = new HashMap();
		tempMap.put(Constant.ID, (short) 0);
		tempMap.put(Constant.NAME, "全部");
		userNames.add(tempMap);
		for (Entry<Long, User> entry : userset.entrySet()) {
			long ucid = entry.getKey();
			tempMap = new HashMap();
			String ucname = users.get(ucid) == null ? "-" : users.get(ucid).getUcname();
			tempMap.put(Constant.ID, ucid);
			tempMap.put(Constant.NAME, ucname);
			userNames.add(tempMap);
		}

		return SUCCESS;
	}

	/**
	 * 增加白名单
	 * 
	 * @return
	 */
	public String addWhitelist() {

		initUnitPos();

		Long ucid = ucHelper.getUser().getUcid();

		if (unitid == null || !unitPosids.contains(unitid)) {
			result = false;
			reason = this.getText("whitelist.args.error");
			return queryWhitelist();
		}

		if (url == null) {
			result = false;
			reason = this.getText("whitelist.url.null");
			return queryWhitelist();
		}
		// 转为小写
		url = url.toLowerCase();
		if (url.startsWith("http://") || url.startsWith("https://")) {

		} else {
			url = "http://" + url;
		}
		String domain = URLUtils.getDomain(url);
		if (domain == null) {
			result = false;
			reason = this.getText("whitelist.url.error");
			return queryWhitelist();
		}

		// boolean exist = whitelistMgr.isExist(domain, unitid);
		boolean exist = urlWhitelistService.isDomainAndPosIdExist(domain, unitid);
		if (exist) {
			result = false;
			reason = this.getText("whitelist.url.exist");
			return queryWhitelist();
		}

		UrlWhitelist wl = new UrlWhitelist();
		wl.setPosId(unitid);
		wl.setUrl(url);
		wl.setDomain(domain);
		wl.setcType(typeAdded);
		wl.setcDesc(desc);
		urlWhitelistService.addUrlWhitelist(wl, ucid);

		return queryWhitelist();
	}

	/**
	 * 修改白名单
	 * 
	 * @return
	 */
	public String modWhitelist() {

		initUnitPos();

		Long ucid = ucHelper.getUser().getUcid();

		if (id == null) {
			result = false;
			reason = this.getText("whitelist.args.error");
			return queryWhitelist();
		}

		if (url == null) {
			result = false;
			reason = this.getText("whitelist.url.null");
			return queryWhitelist();
		}

		// 转为小写
		url = url.toLowerCase();
		if (url.startsWith("http://") || url.startsWith("https://")) {

		} else {
			url = "http://" + url;
		}
		String domain = URLUtils.getDomain(url);
		if (domain == null) {
			result = false;
			reason = this.getText("whitelist.url.error");
			return queryWhitelist();
		}

		UrlWhitelist wl = urlWhitelistService.findById(id);
		if (wl == null) {
			result = false;
			reason = this.getText("whitelist.id.notexist");
			return queryWhitelist();
		}

		if (!domain.equals(wl.getDomain())) {
			boolean exist = urlWhitelistService.isDomainAndPosIdExist(domain, wl.getPosId());
			if (exist) {
				result = false;
				reason = this.getText("whitelist.url.exist");
				return queryWhitelist();
			}
		}

		if (!unitPosids.contains(wl.getPosId())) {
			result = false;
			reason = this.getText("whitelist.args.error");
			return queryWhitelist();
		}

		wl.setUrl(url);
		wl.setDomain(domain);
		wl.setcType(typeAdded);
		wl.setcDesc(desc);
		urlWhitelistService.updateUrlWhitelist(wl, ucid);

		return queryWhitelist();
	}

	/**
	 * 删除白名单
	 * 
	 * @return
	 */
	public String delWhitelist() {

		initUnitPos();

		Long ucid = ucHelper.getUser().getUcid();

		if (id == null) {
			result = false;
			reason = this.getText("whitelist.args.error");
			return queryWhitelist();
		}

		UrlWhitelist wl = urlWhitelistService.findById(id);

		if (wl == null) {
			return queryWhitelist();
		}

		if (!unitPosids.contains(wl.getPosId())) {
			result = false;
			reason = this.getText("whitelist.args.error");
			return queryWhitelist();
		}

		urlWhitelistService.deleteUrlWhitelist(wl, ucid);

		return queryWhitelist();
	}

	public String getPageName() {
		return "WHITE_LIST";
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String id) {
		this.queryId = id;
	}

	/**
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * @return the unitlist
	 */
	public List<Map<String, String>> getUnitlist() {
		return unitlist;
	}

	/**
	 * @param unitlist
	 *            the unitlist to set
	 */
	public void setUnitlist(List<Map<String, String>> unitlist) {
		this.unitlist = unitlist;
	}

	/**
	 * @return the unitid
	 */
	public Long getUnitid() {
		return unitid;
	}

	/**
	 * @param unitid
	 *            the unitid to set
	 */
	public void setUnitid(Long unitid) {
		this.unitid = unitid;
	}

	/**
	 * @return the whitelist
	 */
	public List<Map<String, Object>> getWhitelist() {
		return whitelist;
	}

	/**
	 * @param whitelist
	 *            the whitelist to set
	 */
	public void setWhitelist(List<Map<String, Object>> whitelist) {
		this.whitelist = whitelist;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the unitPos
	 */
	public List<Position> getUnitPos() {
		return unitPos;
	}

	/**
	 * @param unitPos
	 *            the unitPos to set
	 */
	public void setUnitPos(List<Position> unitPos) {
		this.unitPos = unitPos;
	}

	/**
	 * @return the unitPosids
	 */
	public Set<Long> getUnitPosids() {
		return unitPosids;
	}

	/**
	 * @param unitPosids
	 *            the unitPosids to set
	 */
	public void setUnitPosids(Set<Long> unitPosids) {
		this.unitPosids = unitPosids;
	}

	public UrlWhitelistService getUrlWhitelistService() {
		return urlWhitelistService;
	}

	public void setUrlWhitelistService(UrlWhitelistService urlWhitelistService) {
		this.urlWhitelistService = urlWhitelistService;
	}

}

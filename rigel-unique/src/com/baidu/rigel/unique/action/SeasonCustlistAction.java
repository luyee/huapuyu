package com.baidu.rigel.unique.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.baidu.rigel.platform.vo.Page;
import com.baidu.rigel.service.usercenter.bean.Position;
import com.baidu.rigel.service.usercenter.bean.User;
import com.baidu.rigel.unique.bo.SeasonCustList;
import com.baidu.rigel.unique.bo.SeasonCustListPhone;
import com.baidu.rigel.unique.common.BaseActionSupport;
import com.baidu.rigel.unique.common.ResearchAware;
import com.baidu.rigel.unique.service.SeasonCustListPhoneService;
import com.baidu.rigel.unique.service.SeasonCustListService;
import com.baidu.rigel.unique.service.SeasonCustListVoService;
import com.baidu.rigel.unique.service.xuanyuan.ShifenCustomerService;
import com.baidu.rigel.unique.tinyse.TinyseMgr;
import com.baidu.rigel.unique.utils.Constant;
import com.baidu.rigel.unique.utils.URLUtils;
import com.baidu.rigel.unique.vo.SeasonCustListVo;
import com.opensymphony.xwork2.ActionContext;

/**
 * 季节性备案客户名单
 * 
 * @author LiTao
 * 
 */
@SuppressWarnings("serial")
public class SeasonCustlistAction extends BaseActionSupport implements ResearchAware {

	private final static Logger logger = LogManager.getLogger(SeasonCustlistAction.class);

	public static final Long URL_MAX_LENGTH = 210L;
	public static final Long CUSTNAME_MAX_LENGTH = 200L;

	public static final String ID = "id";
	public static final String CREATETIME = "createTime";
	public static final String CUSTNAME = "custName";
	public static final String URL = "url";
	public static final String PHONELIST = "phoneList";
	public static final String CREATEID = "createId";

	public static final String PHONETYPE = "phoneType";
	public static final String PHONENUM = "phoneNum";
	public static final String PHONEAREACODE = "phoneAreacode";
	public static final String PHONEEXTEND = "phoneExtend";

	public static final Long MOBILE_PHONETYPE = 1L;
	public static final Long TELEPHONE_PHONETYPE = 0L;

	public static final Long MOBILE_PHONE_LENGTH = 11L;

	// 老客户名称 添加
	private String addCustName;
	// URL 添加
	private String addUrl;
	private List<Map<String, Object>> phoneList;

	// 老客户名称
	private String custName;
	// URL
	private String url;
	// 设定人
	private List<Map<String, Object>> createIdList;
	// 当前选中的设定人
	private Long createId;

	// 信息列表
	private List<Map<String, Object>> seasonCustlist;

	private Long id;
	private String[] phoneAreacode;
	private String[] phoneNum;
	private String[] phoneExtend;
	private String[] mobilephone;

	// 查询区--分页参数
	private Page page;
	private String queryId;

	private SeasonCustListService seasonCustListService;
	private SeasonCustListPhoneService seasonCustListPhoneService;
	private SeasonCustListVoService seasonCustListVoService;
	private ShifenCustomerService shifenCustomerService;
	private String errorMessage;
	private List<Map<String, Object>> unitDeptList = new ArrayList<Map<String, Object>>();
	private Long unitPosId;

	public Date getInvalidate() {
		return invalidate;
	}

	public void setInvalidate(Date invalidate) {
		this.invalidate = invalidate;
	}

	public Date getBeginInvalidate() {
		return beginInvalidate;
	}

	public void setBeginInvalidate(Date beginInvalidate) {
		this.beginInvalidate = beginInvalidate;
	}

	public Date getEndInvalidate() {
		return endInvalidate;
	}

	public void setEndInvalidate(Date endInvalidate) {
		this.endInvalidate = endInvalidate;
	}

	public Short getUsedInvalidate() {
		return usedInvalidate;
	}

	public void setUsedInvalidate(Short usedInvalidate) {
		this.usedInvalidate = usedInvalidate;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	/**
	 * 到期日期
	 */
	private Date invalidate;
	/**
	 * 到期起始日期
	 */
	private Date beginInvalidate;

	/**
	 * 到期起始日期
	 */
	private Date endInvalidate;

	/**
	 * 是否使用到期时间
	 */
	private Short usedInvalidate;

	/**
	 * 联系电话
	 */
	private String telphone;

	// 岗位id
	private List<Long> posIds;
	TinyseMgr tinyseMgr;

	private void initPosids() {
		if (posIds == null) {
			posIds = new ArrayList<Long>();
			Long[] unitPosIds = userMgr.getUnitPosId(ucHelper.getCurrentPos().getPosid());
			if (unitPosIds != null) {
				if (unitPosIds != null) {
					for (int i = 0; i < unitPosIds.length; i++) {
						posIds.add(unitPosIds[i]);
					}
				}
			}
		}
	}

	private void initPage() {
		if (this.page == null) {
			this.page = new Page();
		}
		if (this.page.getCur_page_num() == null) {
			this.page.setCur_page_num(Long.valueOf(Constant.FIRST_PAGE_NUM));
		}
		if (this.page.getPage_size() == null) {
			this.page.setPage_size(Long.valueOf(15));
		}
		if (this.page.getTotal_page_num() == null) {
			this.page.setTotal_page_num(NumberUtils.LONG_ZERO);
		}
	}

	private void initUnitDeptList() {
		List<Position> pos = userMgr.getUnitPos(ucHelper.getCurrentPos().getPosid());
		if (unitDeptList == null) {
			unitDeptList = new ArrayList<Map<String, Object>>();
		}
		if (unitDeptList.size() == 0) {
			for (int i = 0; i < pos.size(); i++) {
				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put(Constant.ID, pos.get(i).getPosid());
				tmp.put(Constant.NAME, pos.get(i).getPosname());
				unitDeptList.add(tmp);
			}
		}
	}

	private void initCreateIdList() {
		// createId
		// createIdList
		// Long posid = ucHelper.getCurrentPos().getPosid();
		if (createIdList == null) {
			List<Long> idlist = seasonCustListService.selectDisCreateIdByPosIdList(posIds);
			List<User> userList = userMgr.getUserList(idlist);
			// userList = NormalUserUtil.getNormalUser(userList, userMgr);

			createIdList = new ArrayList<Map<String, Object>>();
			Map<String, Object> tmp = new HashMap<String, Object>();
			tmp.put(Constant.ID, Constant.PLEASE_SELECT_VALUE);
			tmp.put(Constant.NAME, Constant.SELECT_ALL_NAME);
			createIdList.add(tmp);
			for (int i = 0; i < userList.size(); i++) {
				tmp = new HashMap<String, Object>();
				tmp.put(Constant.ID, userList.get(i).getUcid());
				tmp.put(Constant.NAME, userList.get(i).getUcname());
				createIdList.add(tmp);
			}
		}
		if (createId == null) {
			if (createIdList != null && createIdList.size() > 0) {
				createId = (Long) createIdList.get(0).get(Constant.ID);
			}
		}
	}

	/**
	 * 查询季节性备案客户名单
	 * 
	 * @return
	 */
	public String querySeasonCustlist() {

		initPage();
		initPosids();
		initCreateIdList();
		Long localCreateId = null;
		if (!createId.equals(Constant.PLEASE_SELECT_VALUE)) {
			localCreateId = createId;
		}
		// Long posid = ucHelper.getCurrentPos().getPosid();
		// List<Position> posList = userMgr.getUnitPos(posid);
		// posList.get(0).getPosid();

		// List<SeasonCustlistAll> scs = seasonCustlistMgr.query(custName, url,
		// localCreateId, posIds, curpage, pagesize);
		//
		// total = seasonCustlistMgr.count(custName, url, localCreateId, posIds,
		// curpage, pagesize);
		// TODO Anders zhu : mock以便代码能够运行
		// Map searchMap = (Map) ((Map) ActionContext.getContext().getSession().get("RESEARCH_MAP_KEY")).get(this.getQueryId());
		Map searchMap = null;
		if (searchMap == null) {
			searchMap = new HashMap();
		}
		Long count = seasonCustListVoService.pageCount((String) searchMap.get("custName"), (String) searchMap.get("url"), localCreateId, telphone, (Short) searchMap.get("usedInvalidate"), (Date) searchMap.get("beginInvalidate"), (Date) searchMap.get("endInvalidate"), posIds);
		page.setTotalRecNum(count);

		List<SeasonCustListVo> scs = seasonCustListVoService.pageList((String) searchMap.get("custName"), (String) searchMap.get("url"), localCreateId, telphone, (Short) searchMap.get("usedInvalidate"), (Date) searchMap.get("beginInvalidate"), (Date) searchMap.get("endInvalidate"), posIds, (page.getCurPageNum().intValue() - 1) * page.getPageSize().intValue(), page.getPageSize().intValue());

		searchMap.put("page", page);
		searchMap.put("posIds", posIds);
		searchMap.put("localCreateId", localCreateId);
		searchMap.put("telphone", telphone);
		// List<SeasonCustListVo> scs = seasonCustlistMgr.query(searchMap);

		seasonCustlist = new ArrayList<Map<String, Object>>();
		for (SeasonCustListVo sc : scs) {
			Map<String, Object> tmp = new HashMap<String, Object>();
			SeasonCustList seasonCust = sc.getSeasonCustList();
			if (seasonCust != null) {
				tmp.put(ID, seasonCust.getId());
				tmp.put(CREATETIME, seasonCust.getCreateTime());
				tmp.put(CUSTNAME, seasonCust.getCustName());
				tmp.put(URL, seasonCust.getUrl());
				User user = userMgr.getUserById(seasonCust.getCreateId());
				if (user != null)
					tmp.put(CREATEID, user.getUcname());
			}
			List<SeasonCustListPhone> phonelist = sc.getSeasonCustListPhoneList();
			if (phonelist != null) {
				StringBuffer strb = new StringBuffer();
				for (SeasonCustListPhone ph : phonelist) {
					strb.append(ph.getPhonenum() + ",");
				}
				String str = new String();
				if (strb != null && strb.length() > 0)
					str = strb.substring(0, strb.length() - 1);
				tmp.put(PHONELIST, str);
			}
			tmp.put("invalidate", seasonCust.getInvalidate());
			seasonCustlist.add(tmp);
		}
		// if (scs == null || scs.size() == 0) {
		// page.setTotalRecNum(0L);
		// } else {
		// if (total == null)
		// total = Long.valueOf(0L);
		// page.setTotalRecNum(total);
		// }

		return SUCCESS;
	}

	private boolean isValidateMobileNum(String ph) {
		if (ph != null && ph.length() == MOBILE_PHONE_LENGTH) {
			if (!(ph.charAt(0) == '1'))
				return false;
			return NumberUtils.isNumber(ph);
		}
		return false;
	}

	private String getValidatePhoneNum(String phoneAreacode, String phoneNum, String phoneExtend) {
		StringBuffer strb = new StringBuffer();
		// 区号3、4位数字 以0开头
		if (phoneAreacode != null && (phoneAreacode.length() == 3 || phoneAreacode.length() == 4)) {
			if (!(phoneAreacode.charAt(0) == '0'))
				return null;
			if (!NumberUtils.isNumber(phoneAreacode)) {
				return null;
			}
			strb.append(phoneAreacode + "-");
			// 电话号码为7、8位的数字
			if (phoneNum != null && (phoneNum.length() == 7 || phoneNum.length() == 8)) {
				if (!NumberUtils.isNumber(phoneNum)) {
					return null;
				}
				strb.append(phoneNum);
				// 分机号为空，或者为1-8位的数字
				if (phoneExtend != null && phoneExtend.length() > 0) {
					if (phoneExtend.length() > 8) {
						return null;
					}
					if (!NumberUtils.isNumber(phoneExtend)) {
						return null;
					}
					strb.append("-" + phoneExtend);
				}
				return strb.toString();
			}
		}

		return null;
	}

	/*
	 * 回填返回结果
	 */
	private String validateFail() {
		phoneList = new ArrayList<Map<String, Object>>();
		Map<String, Object> tmp = null;
		for (String ph : mobilephone) {
			tmp = new HashMap<String, Object>();
			tmp.put(ID, "");
			tmp.put(PHONETYPE, MOBILE_PHONETYPE);
			tmp.put(PHONEAREACODE, "");
			tmp.put(PHONENUM, ph);
			tmp.put(PHONEEXTEND, "");
			phoneList.add(tmp);
		}
		for (int i = 0; i < phoneNum.length; i++) {
			tmp = new HashMap<String, Object>();
			tmp.put(ID, "");
			tmp.put(PHONETYPE, TELEPHONE_PHONETYPE);
			tmp.put(PHONEAREACODE, phoneAreacode[i]);
			tmp.put(PHONENUM, phoneNum[i]);
			tmp.put(PHONEEXTEND, phoneExtend[i]);
			phoneList.add(tmp);
		}
		return querySeasonCustlist();
	}

	/*
	 * 回填返回结果
	 */
	private String modSuccess() {
		addCustName = null;
		addUrl = null;
		// url=null;
		// id=null;
		phoneList = null;

		Map researchMap = (Map) ActionContext.getContext().getSession().get("RESEARCH_MAP_KEY");
		if (researchMap != null) {
			Map map = (Map) researchMap.get(this.getQueryId());
			if (map != null) {
				map.put("addCustName", null);
				map.put("addUrl", null);
				map.put("phoneList", null);
			}
		}

		return querySeasonCustlist();
	}

	/**
	 * 添加季节性备案客户名单
	 * 
	 * @return
	 */
	public String addSeasonCustlist() {

		Long ucid = ucHelper.getUser().getUcid();

		if (addCustName == null || addCustName.length() == 0 || addCustName.length() > CUSTNAME_MAX_LENGTH) {
			errorMessage = this.getText("SeasonCustlist.args.error");
			logger.info(errorMessage + "addCustName: " + addCustName);
			return validateFail();
		}
		if (addUrl == null || addUrl.length() == 0 || addUrl.length() > URL_MAX_LENGTH) {
			errorMessage = this.getText("SeasonCustlist.args.error");
			logger.info(errorMessage + "addUrl: " + addUrl);
			return validateFail();
		}

		if (unitPosId == null) {
			errorMessage = this.getText("SeasonCustlist.args.error");
			logger.info(errorMessage + "unitPosId==null");
			return validateFail();
		}

		// 是否是老客户
		// List<Long> custids = sfCustMgr.exactMatchAllCustUrlCustName(addUrl, addCustName);
		List<Long> custids = shifenCustomerService.selectCustIdByCompanyNameSiteUrl(addCustName, addUrl);
		if (custids == null || custids.size() == 0) {
			errorMessage = this.getText("ShifenCust.url.notexist");
			logger.info(errorMessage + "addUrl: " + addUrl + "addCustName: " + addCustName);
			return validateFail();
		}
		// 若url不是以http://或https://开头，自动添加http://
		String localUrl = null;
		if (addUrl != null) {
			if (addUrl.startsWith("http://") || addUrl.startsWith("https://"))
				localUrl = addUrl;
			else if (addUrl.length() > 0) {
				localUrl = "http://" + addUrl;
			} else {
				localUrl = addUrl;
			}
		}
		Long custid = custids.get(0);
		// 表内是否已存在此url
		List<Long> posList = new ArrayList<Long>();
		posList.add(unitPosId);
		List<SeasonCustList> scl = seasonCustListService.equalUrl(localUrl, posList);
		if (scl == null || scl.size() == 0) {

		} else {
			errorMessage = this.getText("SeasonCustlistMgr.url.exist");
			logger.info(errorMessage + "addUrl: " + localUrl);
			return validateFail();
		}

		SeasonCustList sc = new SeasonCustList();
		sc.setCreateId(ucid);
		Date now = new Date();
		sc.setCreateTime(now);
		sc.setCustId(custid);
		sc.setCustName(addCustName);
		sc.setDelFlag((short) 0);
		sc.setPosId(unitPosId);
		sc.setUpdateId(ucid);
		sc.setUpdateTime(now);
		sc.setUrl(localUrl);
		sc.setDomain(URLUtils.getDomain(localUrl));
		sc.setInvalidate(invalidate);
		sc = seasonCustListService.saveOrUpdate(sc);

		for (String ph : mobilephone) {
			if (isValidateMobileNum(ph)) {
				SeasonCustListPhone scph = new SeasonCustListPhone();
				scph.setDelFlag((short) 0);
				scph.setPhonenum(ph);
				scph.setSeasonCustlistId(sc.getId());
				seasonCustListPhoneService.saveOrUpdate(scph);
			}
		}
		for (int i = 0; i < phoneNum.length; i++) {
			String ph = getValidatePhoneNum(phoneAreacode[i], phoneNum[i], phoneExtend[i]);
			if (ph != null) {
				SeasonCustListPhone scph = new SeasonCustListPhone();
				scph.setDelFlag((short) 0);
				scph.setPhonenum(ph);
				scph.setSeasonCustlistId(sc.getId());
				seasonCustListPhoneService.saveOrUpdate(scph);
			}
		}

		// 向tinyse中添加内容
		tinyseMgr.addSeasonCustData(sc.getId(), sc.getCustName());

		return modSuccess();
	}

	/**
	 * 删除季节性备案客户名单记录
	 * 
	 * @return
	 */
	public String delSeasonCustlist() {

		initPosids();
		Long ucid = ucHelper.getUser().getUcid();

		if (id == null) {
			errorMessage = this.getText("SeasonCustlist.args.error");
			logger.info(errorMessage + "id: " + id);
			return querySeasonCustlist();
		}
		logger.info("ucid: " + ucid + "delSeasonCustlist id:" + id);
		SeasonCustList sc = seasonCustListService.findById(id);
		// Long posid = ucHelper.getCurrentPos().getPosid();
		if (sc == null) {
			logger.info("ucid: " + ucid + "delSeasonCustlist id not validate id:" + id);
			return querySeasonCustlist();
		}
		boolean found = false;
		for (Long posId : posIds) {
			if (sc.getPosId().equals(posId)) {
				found = true;
				break;
			}
		}
		if (found) {
			// 更新最后更新者信息
			sc.setUpdateId(ucid);
			sc.setUpdateTime(new Date());
			seasonCustListService.saveOrUpdate(sc);

			seasonCustListVoService.delete(id);

			// 删除tinyse中的数据
			List<Long> ids = new ArrayList<Long>();
			ids.add(id);
			tinyseMgr.delSeasonCustData(ids);
		} else {
			logger.info("ucid: " + ucid + "delSeasonCustlist id not validate id:" + id + "posIds: " + posIds);
		}
		return querySeasonCustlist();
	}

	public String getPageName() {
		return "SEASON_CUST_WHITE_LIST";
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String id) {
		this.queryId = id;
	}

	/**
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public List<Map<String, Object>> getCreateIdList() {
		return createIdList;
	}

	public void setCreateIdList(List<Map<String, Object>> createIdList) {
		this.createIdList = createIdList;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
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

	public List<Map<String, Object>> getSeasonCustlist() {
		return seasonCustlist;
	}

	public void setSeasonCustlist(List<Map<String, Object>> seasonCustlist) {
		this.seasonCustlist = seasonCustlist;
	}

	public String[] getPhoneAreacode() {
		return phoneAreacode;
	}

	public void setPhoneAreacode(String[] phoneAreacode) {
		this.phoneAreacode = phoneAreacode;
	}

	public String[] getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String[] phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String[] getPhoneExtend() {
		return phoneExtend;
	}

	public void setPhoneExtend(String[] phoneExtend) {
		this.phoneExtend = phoneExtend;
	}

	public String[] getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String[] mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getAddCustName() {
		return addCustName;
	}

	public void setAddCustName(String addCustName) {
		this.addCustName = addCustName;
	}

	public String getAddUrl() {
		return addUrl;
	}

	public void setAddUrl(String addUrl) {
		this.addUrl = addUrl;
	}

	public List<Map<String, Object>> getUnitDeptList() {
		initUnitDeptList();
		return unitDeptList;
	}

	public void setUnitDeptList(List<Map<String, Object>> unitDeptList) {
		this.unitDeptList = unitDeptList;
	}

	public Long getUnitPosId() {
		return unitPosId;
	}

	public void setUnitPosId(Long unitPosId) {
		this.unitPosId = unitPosId;
	}

	public List<Long> getPosIds() {
		return posIds;
	}

	public void setPosIds(List<Long> posIds) {
		this.posIds = posIds;
	}

	public List<Map<String, Object>> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<Map<String, Object>> phoneList) {
		this.phoneList = phoneList;
	}

	public TinyseMgr getTinyseMgr() {
		return tinyseMgr;
	}

	public void setTinyseMgr(TinyseMgr tinyseMgr) {
		this.tinyseMgr = tinyseMgr;
	}

	public SeasonCustListService getSeasonCustListService() {
		return seasonCustListService;
	}

	public void setSeasonCustListService(SeasonCustListService seasonCustListService) {
		this.seasonCustListService = seasonCustListService;
	}

	public SeasonCustListPhoneService getSeasonCustListPhoneService() {
		return seasonCustListPhoneService;
	}

	public void setSeasonCustListPhoneService(SeasonCustListPhoneService seasonCustListPhoneService) {
		this.seasonCustListPhoneService = seasonCustListPhoneService;
	}

	public SeasonCustListVoService getSeasonCustListVoService() {
		return seasonCustListVoService;
	}

	public void setSeasonCustListVoService(SeasonCustListVoService seasonCustListVoService) {
		this.seasonCustListVoService = seasonCustListVoService;
	}

	public ShifenCustomerService getShifenCustomerService() {
		return shifenCustomerService;
	}

	public void setShifenCustomerService(ShifenCustomerService shifenCustomerService) {
		this.shifenCustomerService = shifenCustomerService;
	}

}

package com.baidu.rigel.unique.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.baidu.rigel.platform.vo.Page;
import com.baidu.rigel.service.usercenter.bean.User;
import com.baidu.rigel.unique.bo.Blacklist;
import com.baidu.rigel.unique.bo.BlacklistPhone;
import com.baidu.rigel.unique.common.BaseActionSupport;
import com.baidu.rigel.unique.common.CalloutConstant;
import com.baidu.rigel.unique.common.Constant;
import com.baidu.rigel.unique.common.ResearchAware;
import com.baidu.rigel.unique.common.SystemConstant;
import com.baidu.rigel.unique.service.BlacklistPhoneService;
import com.baidu.rigel.unique.service.BlacklistService;
import com.baidu.rigel.unique.service.BlacklistVoService;
import com.baidu.rigel.unique.utils.Utils;
import com.baidu.rigel.unique.vo.BlacklistVo;
import com.opensymphony.xwork2.ActionContext;

/**
 * 黑名单
 * 
 * @author LiTao
 * 
 */
@SuppressWarnings("serial")
public class BlacklistAction extends BaseActionSupport implements ResearchAware {

	private final static Logger logger = LogManager.getLogger(BlacklistAction.class);

	public static final Long URL_MAX_LENGTH = 200L;
	public static final Long CUSTNAME_MAX_LENGTH = 200L;

	public static final String ID = "id";
	public static final String CREATETIME = "createTime";
	public static final String CUSTNAME = "custName";
	public static final String URL = "url";
	public static final String PHONELIST = "phoneList";
	public static final String CREATEID = "createId";
	public static final String SRC = "src";

	public static final String PHONETYPE = "phoneType";
	public static final String PHONENUM = "phoneNum";
	public static final String PHONEAREACODE = "phoneAreacode";
	public static final String PHONEEXTEND = "phoneExtend";

	public static final Long MOBILE_PHONETYPE = 1L;
	public static final Long TELEPHONE_PHONETYPE = 0L;

	public static final Long MOBILE_PHONE_LENGTH = 11L;

	// 黑名单 添加区域
	private String addCustName;
	private String addUrl;
	private Short addSrc;
	private String[] phoneAreacode;
	private String[] phoneNum;
	private String[] phoneExtend;
	private String[] mobilephone;

	private List<Map<String, Object>> phoneList;

	// 查询区域
	private String custName;
	private String url;
	// 设定人
	private List<Map<String, Object>> createIdList;
	// 当前选中的设定人
	private Long createId;
	private Short selectSrc;
	// 信息列表
	private List<Map<String, Object>> blackList;
	private Long id;

	// 查询区--分页参数
	private String queryId;
	private String errorMessage;

	private BlacklistVoService blacklistVoService;
	private BlacklistService blacklistService;
	private BlacklistPhoneService blacklistPhoneService;

	private void initPage() {
		if (this.page == null) {
			this.page = new Page();
		}
		if (this.page.getCur_page_num() == null) {
			this.page.setCur_page_num(Long.valueOf(SystemConstant.FIRST_PAGE_NUM));
		}
		if (this.page.getPage_size() == null) {
			this.page.setPage_size(Long.valueOf(SystemConstant.PAGE_NUM));
		}
		if (this.page.getTotal_page_num() == null) {
			this.page.setTotal_page_num(SystemConstant.ZERO);
		}
	}

	private void initCreateIdList() {
		if (createIdList == null) {
			List<Long> idlist = blacklistService.selectDisCreatorIdList();
			List<User> userList = userMgr.getUserList(idlist);
			// userList = NormalUserUtil.getNormalUser(userList, userMgr);

			createIdList = new ArrayList<Map<String, Object>>();
			Map<String, Object> tmp = new HashMap<String, Object>();
			tmp.put(CalloutConstant.ID, CalloutConstant.PLEASE_SELECT_VALUE);
			tmp.put(CalloutConstant.NAME, Constant.SELECT_ALL_NAME);
			createIdList.add(tmp);
			for (int i = 0; i < userList.size(); i++) {
				tmp = new HashMap<String, Object>();
				tmp.put(CalloutConstant.ID, userList.get(i).getUcid());
				tmp.put(CalloutConstant.NAME, userList.get(i).getUcname());
				createIdList.add(tmp);
			}
		}
		if (createId == null) {
			if (createIdList != null && createIdList.size() > 0) {
				createId = (Long) createIdList.get(0).get(CalloutConstant.ID);
			}
		}
	}

	/**
	 * 查询季节性备案客户名单
	 * 
	 * @return
	 */
	public String queryBlacklist() {

		initPage();
		initCreateIdList();

		int curpage = 0;
		int pagesize = CalloutConstant.PAGE_NUM;

		if (page != null) {
			if (page.getCur_page_num() != null) {
				curpage = page.getCur_page_num().intValue() - 1;
			}
			if (page.getPage_size() != null) {
				pagesize = page.getPage_size().intValue();
			}
		}
		Long total = Long.valueOf(0L);

		Long localCreateId = null;
		if (!createId.equals(CalloutConstant.PLEASE_SELECT_VALUE)) {
			localCreateId = createId;
		}

		List<Short> src = new ArrayList<Short>();
		if (selectSrc != null && selectSrc.intValue() != -1) {
			src.add(selectSrc);
		} else {
			src.add((short) 0);
			src.add((short) 1);
		}

		List<BlacklistVo> bls = blacklistVoService.pageList(custName, url, localCreateId, src, curpage, pagesize);

		total = blacklistVoService.pageCount(custName, url, localCreateId, src);

		blackList = new ArrayList<Map<String, Object>>();
		for (BlacklistVo bl : bls) {
			Map<String, Object> tmp = new HashMap<String, Object>();
			Blacklist blacklist = bl.getBlacklist();
			if (blacklist != null) {
				tmp.put(ID, blacklist.getBlacklistId());
				tmp.put(CREATETIME, blacklist.getCreateTime());
				tmp.put(CUSTNAME, blacklist.getCompanyName());
				if (blacklist.getUrl() == null || blacklist.getUrl().length() == 0) {
					tmp.put(URL, "-");
				} else {
					tmp.put(URL, blacklist.getUrl());
				}

				if (blacklist.getSrc() != null && blacklist.getSrc().intValue() == 1) {
					tmp.put(SRC, "AD白名单");
				} else {
					tmp.put(SRC, "公司指定");
				}
				User user = userMgr.getUserById(blacklist.getCreatorId());
				if (user != null)
					tmp.put(CREATEID, user.getUcname());
				else
					tmp.put(CREATEID, "-");
			}
			List<BlacklistPhone> phonelist = bl.getBlacklistPhoneList();
			if (phonelist != null) {
				StringBuffer strb = new StringBuffer();
				for (BlacklistPhone ph : phonelist) {
					strb.append(ph.getPhonenum() + ",");
				}
				String str = new String();
				if (strb != null && strb.length() > 0)
					str = strb.substring(0, strb.length() - 1);
				if (str == null || str.length() == 0) {
					str = "-";
				}
				tmp.put(PHONELIST, str);
			}

			blackList.add(tmp);
		}
		if (bls == null || bls.size() == 0) {
			page.setTotalRecNum(0L);
		} else {
			if (total == null)
				total = Long.valueOf(0L);
			page.setTotalRecNum(total);
		}

		addSrc = 0;
		return SUCCESS;
	}

	private boolean isValidateMobileNum(String ph) {
		if (ph != null && ph.length() == MOBILE_PHONE_LENGTH) {
			if (!(ph.charAt(0) == '1'))
				return false;
			return Utils.isNumber(ph);
		}
		return false;
	}

	private String getValidatePhoneNum(String phoneAreacode, String phoneNum, String phoneExtend) {
		StringBuffer strb = new StringBuffer();
		// 区号3、4位数字 以0开头
		if (phoneAreacode != null && (phoneAreacode.length() == 3 || phoneAreacode.length() == 4)) {
			if (!(phoneAreacode.charAt(0) == '0'))
				return null;
			if (!Utils.isNumber(phoneAreacode)) {
				return null;
			}
			strb.append(phoneAreacode + "-");
			// 电话号码为7、8位的数字
			if (phoneNum != null && (phoneNum.length() == 7 || phoneNum.length() == 8)) {
				if (!Utils.isNumber(phoneNum)) {
					return null;
				}
				strb.append(phoneNum);
				// 分机号为空，或者为1-8位的数字
				if (phoneExtend != null && phoneExtend.length() > 0) {
					if (phoneExtend.length() > 8) {
						return null;
					}
					if (!Utils.isNumber(phoneExtend)) {
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
		return queryBlacklist();
		// return SUCCESS;
	}

	/*
	 * 回填返回结果
	 */
	private String modSuccess() {
		addCustName = null;
		addUrl = null;
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
		return queryBlacklist();
	}

	/**
	 * 添加季节性备案客户名单
	 * 
	 * @return
	 */
	public String addBlacklist() {

		// TODO Anders Zhu : mock测试
		// Long ucid = ucHelper.getUser().getUcid();
		Long ucid = 0L;

		if (addCustName == null || addCustName.length() == 0 || addCustName.length() > CUSTNAME_MAX_LENGTH) {
			errorMessage = this.getText("Blacklist.args.error");
			logger.info(errorMessage + "addCustName: " + addCustName);
			return validateFail();
		}
		if (addUrl != null && addUrl.length() > URL_MAX_LENGTH) {
			errorMessage = this.getText("Blacklist.args.error");
			logger.info(errorMessage + "addUrl: " + addUrl);
			return validateFail();
		}

		if (addSrc == null) {
			errorMessage = this.getText("Blacklist.args.error");
			logger.info(errorMessage + "addSrc==null");
			return validateFail();
		}

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

		if (localUrl != null && localUrl.length() > 0) {
			// 表内是否已存在此url
			List<Long> ids = blacklistService.equalUrl(localUrl);
			if (ids == null || ids.size() == 0) {

			} else {
				errorMessage = this.getText("Blacklist.url.exist");
				logger.info(errorMessage + "addUrl: " + addUrl);
				return validateFail();
			}
		}

		Blacklist bl = new Blacklist();
		bl.setCompanyName(addCustName);
		Date now = new Date();
		bl.setCreateTime(now);
		bl.setCreatorId(ucid);
		bl.setDelFlag((short) 0);
		bl.setSrc(addSrc);
		bl.setUpdateId(ucid);
		bl.setUpdateTime(now);
		if (localUrl != null) {
			bl.setUrl(localUrl);
		} else {
			bl.setUrl("");
		}
		bl.setCompanyPhone("");
		bl = blacklistService.saveOrUpdate(bl);

		for (String ph : mobilephone) {
			if (isValidateMobileNum(ph)) {
				BlacklistPhone blph = new BlacklistPhone();
				blph.setDelFlag((short) 0);
				blph.setPhonenum(ph);
				blph.setBlacklistId(bl.getBlacklistId());
				blacklistPhoneService.saveOrUpdate(blph);
			}
		}
		for (int i = 0; i < phoneNum.length; i++) {
			String ph = getValidatePhoneNum(phoneAreacode[i], phoneNum[i], phoneExtend[i]);
			if (ph != null) {
				BlacklistPhone blph = new BlacklistPhone();
				blph.setDelFlag((short) 0);
				blph.setPhonenum(ph);
				blph.setBlacklistId(bl.getBlacklistId());
				blacklistPhoneService.saveOrUpdate(blph);
			}
		}

		return modSuccess();
	}

	/**
	 * 删除黑名单记录
	 * 
	 * @return
	 */
	public String delBlacklist() {

		// TODO Anders Zhu : mock测试
		// Long ucid = ucHelper.getUser().getUcid();
		Long ucid = 0L;

		if (id == null) {
			errorMessage = this.getText("Blacklist.args.error");
			logger.info(errorMessage + "id: " + id);
			return queryBlacklist();
		}
		logger.info("ucid: " + ucid + "delBlacklist id:" + id);
		Blacklist blacklist = blacklistService.findById(id);
		if (blacklist == null) {
			logger.info("ucid: " + ucid + "delBlacklist id not validate id:" + id);
			return queryBlacklist();
		}
		blacklist.setUpdateId(ucid);
		blacklist.setUpdateTime(new Date());
		blacklistService.saveOrUpdate(blacklist);

		blacklistVoService.delete(id);

		return queryBlacklist();
	}

	public String getPageName() {
		return "_BLACK_LIST_";
	}

	// Getters and Setters

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

	public Short getAddSrc() {
		return addSrc;
	}

	public void setAddSrc(Short addSrc) {
		this.addSrc = addSrc;
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

	public List<Map<String, Object>> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<Map<String, Object>> phoneList) {
		this.phoneList = phoneList;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Short getSelectSrc() {
		return selectSrc;
	}

	public void setSelectSrc(Short selectSrc) {
		this.selectSrc = selectSrc;
	}

	public List<Map<String, Object>> getBlackList() {
		return blackList;
	}

	public void setBlackList(List<Map<String, Object>> blackList) {
		this.blackList = blackList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BlacklistVoService getBlacklistVoService() {
		return blacklistVoService;
	}

	public void setBlacklistVoService(BlacklistVoService blacklistVoService) {
		this.blacklistVoService = blacklistVoService;
	}

	public BlacklistService getBlacklistService() {
		return blacklistService;
	}

	public void setBlacklistService(BlacklistService blacklistService) {
		this.blacklistService = blacklistService;
	}

	public BlacklistPhoneService getBlacklistPhoneService() {
		return blacklistPhoneService;
	}

	public void setBlacklistPhoneService(BlacklistPhoneService blacklistPhoneService) {
		this.blacklistPhoneService = blacklistPhoneService;
	}

}

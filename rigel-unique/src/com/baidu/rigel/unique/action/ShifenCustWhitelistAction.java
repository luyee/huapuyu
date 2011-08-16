package com.baidu.rigel.unique.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.baidu.rigel.platform.vo.Page;
import com.baidu.rigel.service.usercenter.bean.Position;
import com.baidu.rigel.service.usercenter.bean.User;
import com.baidu.rigel.unique.bo.ShifenCustWhiteList;
import com.baidu.rigel.unique.common.BaseActionSupport;
import com.baidu.rigel.unique.common.ResearchAware;
import com.baidu.rigel.unique.service.ShifenCustWhiteListService;
import com.baidu.rigel.unique.service.xuanyuan.ShifenCustomerService;
import com.baidu.rigel.unique.utils.BusiUtils;
import com.baidu.rigel.unique.utils.Constant;
import com.baidu.rigel.unique.utils.ReadConfig;
import com.baidu.rigel.unique.utils.Utils;

/**
 * URL白名单设置
 * 
 * @author LiTao
 * 
 */
@SuppressWarnings("serial")
public class ShifenCustWhitelistAction extends BaseActionSupport implements ResearchAware {

	private final static Logger logger = LogManager.getLogger(ShifenCustWhitelistAction.class);

	public static final Long URL_MAX_LENGTH = 210L;
	public static final Long CUSTNAME_MAX_LENGTH = 200L;
	public static final String ID = "id";
	public static final String CREATETIME = "createTime";
	public static final String CUSTNAME = "custName";
	public static final String URL = "url";
	public static final String USERID = "userId";
	public static final String POSID = "posId";
	public static final String CREATEID = "createId";

	// 添加区域
	// 老客户名称
	private String addCustName;
	// URL
	private String addUrl;
	// 所属部门 添加区域
	private List<Map<String, Object>> addPosIdList;
	// 当前选中的所属部门
	private Long addPosId;
	// 指定销售 添加区域
	private List<Map<String, String>> addUserIdList;
	// 当前选中的指定销售
	private Long addUserId;

	// 查询区域
	// 老客户名称
	private String custName;
	// URL
	private String url;
	// 指定销售
	private List<Map<String, String>> userIdList;
	// 当前选中的指定销售
	private Long userId;
	// 设定人
	private List<Map<String, String>> createIdList;
	// 当前选中的设定人
	private Long createId;

	// 信息列表
	private List<Map<String, Object>> shifenCustWhitelist;

	private Long id;

	// 所属部门 异步请求
	private Long curDept;
	private List<Map<String, String>> listOpt;

	// 查询区--分页参数
	private Page page;
	private String queryId;

	private ShifenCustWhiteListService shifenCustWhiteListService;
	private ShifenCustomerService shifenCustomerService;
	private ReadConfig readConfig;
	// 运营单位列表
	private Set<Long> addPosIdSet;
	// 指定销售
	private Set<Long> addUserSet;

	private String errorMessage;

	// 岗位id
	private List<Long> posIds;

	// FE 运营单位列表
	// private List<Map<String, String>> unitlist;
	// private Long unitid;

	// 运营单位列表
	// private List<Position> unitPos;
	// private Set<Long> unitPosids;

	// 查询结果
	// private List<Map<String, Object>> whitelist;

	// post参数
	// private String url;
	// private Long id;

	// 返回结果中的提示信息
	// private boolean result = true;

	// private WhitelistMgr whitelistMgr;

	private void initPage() {
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
	}

	private void initAddPosIdList() {

		Long posid = userCenterHelper.getCurrentPos().getPosid();
		Long[] unitPosids = userMgr.getUnitPosId(posid);
		if (unitPosids != null && unitPosids.length <= 1) {
			// TODO Anders Zhu : mock 以便后续代码运行
			// posid = unitPosids[0];
			posid = 0L;
		}
		if (addPosIdList == null) {
			addPosIdList = Utils.trans2FeList(posid, userMgr.getSubTreeByPos(posid), readConfig.getLowestPosType());
			if (addPosIdSet == null) {
				addPosIdSet = new HashSet<Long>();
				for (Map<String, Object> pos : addPosIdList) {
					addPosIdSet.add((Long) pos.get(Constant.ID));
				}
			}
		}

		if (addPosId == null) {
			if (addPosIdList != null && addPosIdList.size() > 0) {
				addPosId = (Long) addPosIdList.get(0).get(Constant.ID);
			}
		}
	}

	private void initAddUserIdList() {

		initAddPosIdList();

		if (addUserIdList == null && addPosId != null) {
			// 部分人员查询列表
			List<User> userList = userMgr.getUserList(userMgr.getAllUserIdsByPosAndRole(addPosId, "SALE_ROLE_TAG"));
			userList = BusiUtils.getNormalUser(userList, userMgr);
			addUserIdList = new ArrayList<Map<String, String>>();

			Map<String, String> tmp = new HashMap<String, String>();
			tmp.put(Constant.ID, Constant.PLEASE_SELECT_VALUE.toString());
			tmp.put(Constant.NAME, "请选择");
			addUserIdList.add(tmp);

			for (int i = 0; i < userList.size(); i++) {
				tmp = new HashMap<String, String>();
				tmp.put(Constant.ID, userList.get(i).getUcid().toString());
				tmp.put(Constant.NAME, userList.get(i).getUcname());
				addUserIdList.add(tmp);
			}
			if (addUserSet == null) {
				addUserSet = new HashSet<Long>();
				for (int i = 0; i < userList.size(); i++) {
					addUserSet.add(userList.get(i).getUcid());
				}
			}
		}
		if (addUserId == null) {
			if (addUserIdList != null && addUserIdList.size() > 0) {
				addUserId = Long.valueOf(addUserIdList.get(0).get(Constant.ID));
			}
		}
	}

	private void initUserIdList() {
		if (userIdList == null) {
			List<Long> idlist = shifenCustWhiteListService.selectDisUserIdByPosIdList(posIds);
			List<User> userList = userMgr.getUserList(idlist);
			// userList = NormalUserUtil.getNormalUser(userList, userMgr);

			userIdList = new ArrayList<Map<String, String>>();
			Map<String, String> tmp = new HashMap<String, String>();
			tmp.put(Constant.ID, Constant.PLEASE_SELECT_VALUE.toString());
			tmp.put(Constant.NAME, Constant.SELECT_ALL_NAME);
			userIdList.add(tmp);
			for (int i = 0; i < userList.size(); i++) {
				tmp = new HashMap<String, String>();
				tmp.put(Constant.ID, userList.get(i).getUcid().toString());
				tmp.put(Constant.NAME, userList.get(i).getUcname());
				userIdList.add(tmp);
			}
		}
		if (userId == null) {
			if (userIdList != null && userIdList.size() > 0) {
				userId = Long.valueOf(userIdList.get(0).get(Constant.ID));
			}
		}
	}

	private void initCreateIdList() {
		if (createIdList == null) {
			List<Long> idlist = shifenCustWhiteListService.selectDisCreateIdByPosIdList(posIds);
			List<User> userList = userMgr.getUserList(idlist);
			// userList = NormalUserUtil.getNormalUser(userList, userMgr);

			createIdList = new ArrayList<Map<String, String>>();
			Map<String, String> tmp = new HashMap<String, String>();
			tmp.put(Constant.ID, Constant.PLEASE_SELECT_VALUE.toString());
			tmp.put(Constant.NAME, Constant.SELECT_ALL_NAME);
			createIdList.add(tmp);
			for (int i = 0; i < userList.size(); i++) {
				tmp = new HashMap<String, String>();
				tmp.put(Constant.ID, userList.get(i).getUcid().toString());
				tmp.put(Constant.NAME, userList.get(i).getUcname());
				createIdList.add(tmp);
			}
		}
		if (createId == null) {
			if (createIdList != null && createIdList.size() > 0) {
				createId = Long.valueOf(createIdList.get(0).get(Constant.ID));
			}
		}
	}

	private void initPosids() {
		if (posIds == null) {
			posIds = new ArrayList<Long>();
			Long[] unitPosIds = userMgr.getUnitPosId(userCenterHelper.getCurrentPos().getPosid());
			if (unitPosIds != null) {
				for (int i = 0; i < unitPosIds.length; i++) {
					posIds.add(unitPosIds[i]);
				}
			}
		}
	}

	/**
	 * 查询白名单
	 * 
	 * @return
	 */
	public String queryShifenCustWhitelist() {

		initPage();

		initAddPosIdList();
		initAddUserIdList();

		initPosids();
		initUserIdList();
		initCreateIdList();

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
		Long total = Long.valueOf(0L);
		Long localCreateId = null;
		Long localUserId = null;
		if (!createId.equals(Constant.PLEASE_SELECT_VALUE)) {
			localCreateId = createId;
		}
		if (!userId.equals(Constant.PLEASE_SELECT_VALUE)) {
			localUserId = userId;
		}
		// List<Long> posids = new ArrayList<Long>();
		// Long[] unitPosIds = userMgr.getUnitPosId(ucHelper.getCurrentPos()
		// .getPosid());
		// if (unitPosIds != null) {
		// for (int i = 0; i < unitPosIds.length; i++) {
		// List<Position> posList = userMgr.getSubTreeByPos(unitPosIds[i]);
		// for (int j = 0; j < posList.size(); j++) {
		// posids.add(posList.get(j).getPosid());
		// }
		// }
		// }
		List<ShifenCustWhiteList> sfcwl = shifenCustWhiteListService.pageList(custName, url, localUserId, localCreateId, posIds, curpage, pagesize);
		total = shifenCustWhiteListService.pageCount(custName, url, localUserId, localCreateId, posIds);

		shifenCustWhitelist = new ArrayList<Map<String, Object>>();

		List<Long> ucids = new ArrayList<Long>();

		for (ShifenCustWhiteList sfcw : sfcwl) {
			ucids.add(sfcw.getUserId());
		}

		// TODO Anders Zhu : usermgr没有的方法
		// Map<Long, Position> posMap = userMgr.getPosNameMapByUser(ucids, "CALLOUT_ROOT_TAG");
		Map<Long, Position> posMap = new HashMap<Long, Position>();

		for (ShifenCustWhiteList sfcw : sfcwl) {
			Map<String, Object> tmp = new HashMap<String, Object>();
			tmp.put(ID, sfcw.getId());
			tmp.put(CREATETIME, sfcw.getCreateTime());
			tmp.put(CUSTNAME, sfcw.getCustName());
			tmp.put(URL, sfcw.getUrl());
			User user = userMgr.getUserById(sfcw.getUserId());
			if (user != null) {
				tmp.put(USERID, user.getUcname());
			}
			ucids.add(sfcw.getUserId());
			Position pos = posMap.get(sfcw.getUserId());
			if (pos != null) {
				tmp.put(POSID, pos.getPosname());
			}
			user = userMgr.getUserById(sfcw.getCreateId());
			if (user != null)
				tmp.put(CREATEID, user.getUcname());
			shifenCustWhitelist.add(tmp);
		}
		if (sfcwl == null || sfcwl.size() == 0) {
			page.setTotalRecNum(0L);
		} else {
			if (total == null)
				total = Long.valueOf(0L);
			page.setTotalRecNum(total);
		}

		return SUCCESS;
	}

	/**
	 * 增加白名单
	 * 
	 * @return
	 */
	public String addShifenCustWhitelist() {

		initAddPosIdList();
		initAddUserIdList();

		Long ucid = userCenterHelper.getUser().getUcid();

		if (addCustName == null || addCustName.length() == 0 || addCustName.length() > CUSTNAME_MAX_LENGTH) {
			errorMessage = this.getText("ShifenCustWhitelist.args.error");
			logger.info(errorMessage + "addCustName: " + addCustName);
			return queryShifenCustWhitelist();
		}
		if (addUrl == null || addUrl.length() == 0 || addUrl.length() > URL_MAX_LENGTH) {
			errorMessage = this.getText("ShifenCustWhitelist.args.error");
			logger.info(errorMessage + "addUrl: " + addUrl);
			return queryShifenCustWhitelist();
		}
		if (addUserId == null || !addUserSet.contains(addUserId)) {
			errorMessage = this.getText("ShifenCustWhitelist.args.error");
			logger.info(errorMessage + "addUserId: " + addUserId);
			return queryShifenCustWhitelist();
		}
		if (addPosId == null || !addPosIdSet.contains(addPosId)) {
			errorMessage = this.getText("ShifenCustWhitelist.args.error");
			logger.info(errorMessage + "addPosId: " + addPosId);
			return queryShifenCustWhitelist();
		}

		// 是否是老客户
		List<Map<String, Object>> sfdata = shifenCustomerService.selectCustIdNamesBySiteUrl(addUrl);
		if (sfdata == null || sfdata.size() == 0) {
			errorMessage = this.getText("ShifenCust.url.notexist");
			logger.info(errorMessage + "addUrl: " + addUrl);
			return queryShifenCustWhitelist();
		}

		// 表内是否已存在此url
		ShifenCustWhiteList sf = shifenCustWhiteListService.equalUrl(addUrl);
		if (sf != null) {
			errorMessage = this.getText("ShifenCustWhitelist.url.exist");
			logger.info(errorMessage + "addUrl: " + addUrl);
			return queryShifenCustWhitelist();
		}

		ShifenCustWhiteList shifenCustWhitelist = new ShifenCustWhiteList();
		shifenCustWhitelist.setCreateId(ucid);
		Date now = new Date();
		shifenCustWhitelist.setCreateTime(now);
		shifenCustWhitelist.setCustId((Long) sfdata.get(0).get("customerd"));// 客户id
		shifenCustWhitelist.setCustName(addCustName);
		shifenCustWhitelist.setUrl(addUrl);
		shifenCustWhitelist.setDelFlag((short) 0);
		shifenCustWhitelist.setUserId(addUserId);

		List<Position> poslist = userMgr.getUnitPos(addPosId);
		if (poslist != null && poslist.size() > 0) {
			// 运营单位id
			shifenCustWhitelist.setPosId(poslist.get(0).getPosid());
		}
		// shifenCustWhitelist.setPosId(addPosId);
		shifenCustWhitelist.setUpdateId(ucid);
		shifenCustWhitelist.setUpdateTime(now);

		shifenCustWhiteListService.saveOrUpdate(shifenCustWhitelist);

		//
		addCustName = "";
		addUrl = "";
		addPosId = null;
		addUserId = null;
		// initAddPosIdList();
		// initAddUserIdList();

		return queryShifenCustWhitelist();
	}

	/**
	 * 删除白名单
	 * 
	 * @return
	 */
	public String delShifenCustWhitelist() {

		initAddPosIdList();

		Long ucid = userCenterHelper.getUser().getUcid();

		if (id == null) {
			errorMessage = this.getText("ShifenCustWhitelist.args.error");
			logger.info(errorMessage + "id: " + id);
			return queryShifenCustWhitelist();
		}
		logger.info("ucid: " + ucid + "delShifenCustWhitelist id:" + id);
		ShifenCustWhiteList sf = shifenCustWhiteListService.findById(id);
		if (sf != null) {
			// 更新最后更新者信息
			sf.setUpdateId(ucid);
			sf.setUpdateTime(new Date());
			shifenCustWhiteListService.saveOrUpdate(sf);
			shifenCustWhiteListService.deleteShifenCustWhiteList(id);
		} else {
			logger.info("ucid: " + ucid + "delShifenCustWhitelist id not validate id:" + id);
		}

		return queryShifenCustWhitelist();
	}

	public String deptlist() {
		initAddPosIdList();

		listOpt = new ArrayList<Map<String, String>>();

		Map<String, String> tmp = new HashMap<String, String>();
		tmp.put(Constant.ID, Constant.PLEASE_SELECT_VALUE.toString());
		tmp.put(Constant.NAME, "请选择");
		listOpt.add(tmp);

		if (curDept == null || !addPosIdSet.contains(curDept)) {
			errorMessage = this.getText("ShifenCustWhitelist.args.error");
			logger.info(errorMessage + "curDept: " + curDept);
			return SUCCESS;
		}

		List<User> userList = userMgr.getUserList(userMgr.getAllUserIdsByPosAndRole(curDept, "SALE_ROLE_TAG"));
		userList = BusiUtils.getNormalUser(userList, userMgr);

		for (int i = 0; i < userList.size(); i++) {
			tmp = new HashMap<String, String>();
			tmp.put(Constant.ID, userList.get(i).getUcid().toString());
			tmp.put(Constant.NAME, userList.get(i).getUcname());
			listOpt.add(tmp);
		}
		return SUCCESS;
	}

	public String getPageName() {
		return "SHIFEN_CUST_WHITE_LIST";
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

	public List<Map<String, Object>> getAddPosIdList() {
		return addPosIdList;
	}

	public void setAddPosIdList(List<Map<String, Object>> addPosIdList) {
		this.addPosIdList = addPosIdList;
	}

	public Long getAddPosId() {
		return addPosId;
	}

	public void setAddPosId(Long addPosId) {
		this.addPosId = addPosId;
	}

	public List<Map<String, String>> getAddUserIdList() {
		return addUserIdList;
	}

	public void setAddUserIdList(List<Map<String, String>> addUserIdList) {
		this.addUserIdList = addUserIdList;
	}

	public List<Map<String, String>> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Map<String, String>> userIdList) {
		this.userIdList = userIdList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Map<String, String>> getCreateIdList() {
		return createIdList;
	}

	public void setCreateIdList(List<Map<String, String>> createIdList) {
		this.createIdList = createIdList;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Long getCurDept() {
		return curDept;
	}

	public void setCurDept(Long curDept) {
		this.curDept = curDept;
	}

	public List<Map<String, String>> getListOpt() {
		return listOpt;
	}

	public void setListOpt(List<Map<String, String>> listOpt) {
		this.listOpt = listOpt;
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

	public Set<Long> getAddPosIdSet() {
		return addPosIdSet;
	}

	public void setAddPosIdSet(Set<Long> addPosIdSet) {
		this.addPosIdSet = addPosIdSet;
	}

	public Set<Long> getAddUserSet() {
		return addUserSet;
	}

	public void setAddUserSet(Set<Long> addUserSet) {
		this.addUserSet = addUserSet;
	}

	/**
	 * @param unitPosids
	 *            the unitPosids to set
	 */

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

	public Long getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	public List<Map<String, Object>> getShifenCustWhitelist() {
		return shifenCustWhitelist;
	}

	public void setShifenCustWhitelist(List<Map<String, Object>> shifenCustWhitelist) {
		this.shifenCustWhitelist = shifenCustWhitelist;
	}

	public List<Long> getPosIds() {
		return posIds;
	}

	public void setPosIds(List<Long> posIds) {
		this.posIds = posIds;
	}

	public ShifenCustWhiteListService getShifenCustWhiteListService() {
		return shifenCustWhiteListService;
	}

	public void setShifenCustWhiteListService(ShifenCustWhiteListService shifenCustWhiteListService) {
		this.shifenCustWhiteListService = shifenCustWhiteListService;
	}

	public ShifenCustomerService getShifenCustomerService() {
		return shifenCustomerService;
	}

	public void setShifenCustomerService(ShifenCustomerService shifenCustomerService) {
		this.shifenCustomerService = shifenCustomerService;
	}

}

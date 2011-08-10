package com.baidu.rigel.unique.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.baidu.rigel.unique.bo.SeasonCustList;
import com.baidu.rigel.unique.bo.SeasonCustListPhone;
import com.baidu.rigel.unique.common.BaseActionSupport;
import com.baidu.rigel.unique.service.SeasonCustListPhoneService;
import com.baidu.rigel.unique.service.SeasonCustListService;
import com.baidu.rigel.unique.service.SeasonCustListVoService;
import com.baidu.rigel.unique.service.ShifenCustomerService;
import com.baidu.rigel.unique.utils.Utils;
import com.baidu.rigel.unique.vo.SeasonCustListVo;

/**
 * 季节性备案客户名单修改
 * 
 * @author LiTao
 * 
 */
@SuppressWarnings("serial")
public class SeasonCustlistModAction extends BaseActionSupport {

	private final static Logger logger = LogManager.getLogger(SeasonCustlistModAction.class);

	public static final Long URL_MAX_LENGTH = 210L;
	public static final Long CUSTNAME_MAX_LENGTH = 200L;

	public static final String ID = "id";
	public static final String PHONETYPE = "phoneType";
	public static final String PHONENUM = "phoneNum";
	public static final String PHONEAREACODE = "phoneAreacode";
	public static final String PHONEEXTEND = "phoneExtend";

	public static final Long MOBILE_PHONETYPE = 1L;
	public static final Long TELEPHONE_PHONETYPE = 0L;

	public static final Long MOBILE_PHONE_LENGTH = 11L;

	// 老客户名称
	private String custName;
	// URL
	private String url;
	// 联系电话
	private List<Map<String, Object>> phoneList;

	private Long id;
	private String[] phoneAreacode;
	private String[] phoneNum;
	private String[] phoneExtend;
	private String[] mobilephone;
	private String[] idPhones;
	private String[] idMobiles;

	/**
	 * 到期日期
	 */
	private Date invalidate;

	public Date getInvalidate() {
		return invalidate;
	}

	public void setInvalidate(Date invalidate) {
		this.invalidate = invalidate;
	}

	private SeasonCustListVoService seasonCustListVoService;
	private SeasonCustListService seasonCustListService;
	private SeasonCustListPhoneService seasonCustListPhoneService;
	private ShifenCustomerService shifenCustomerService;

	// 删除电话 异步请求 (带错误提示文本errorMsg变量跳转到delPhone.json.vm文件中)
	private String errorMsg;
	private String errorMessage;

	private String modMessage;

	// 岗位id
	private List<Long> posIds;

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

	private boolean isMobilePhone(String fullPhonenum) {
		if (fullPhonenum != null)
			if (fullPhonenum.charAt(0) == '1')
				return true;
		return false;
	}

	private String getPhoneAreacode(String fullPhonenum) {
		if (fullPhonenum != null) {
			int i = fullPhonenum.indexOf('-');
			if (i > 0)
				return fullPhonenum.substring(0, i);
		}
		return "";
	}

	private String getPhonenum(String fullPhonenum) {
		if (fullPhonenum != null) {
			int i = fullPhonenum.indexOf('-');
			int j = fullPhonenum.indexOf('-', i + 1);
			if (j > 0)
				return fullPhonenum.substring(i + 1, j);
			else
				return fullPhonenum.substring(i + 1);
		}
		return "";
	}

	private String getPhoneExtend(String fullPhonenum) {
		if (fullPhonenum != null) {
			int i = fullPhonenum.indexOf('-');
			int j = fullPhonenum.indexOf('-', i + 1);
			if (j > 0) {
				return fullPhonenum.substring(j + 1);
			}
		}
		return "";
	}

	/**
	 * 查询季节性备案客户名单
	 * 
	 * @return
	 */
	public String initSeasonCustlist() {

		initPosids();
		SeasonCustListVo sca = seasonCustListVoService.findSeasonCustListVo(id);
		if (sca == null) {
			errorMessage = this.getText("SeasonCustlist.args.error");
			logger.info(errorMessage + "querySeasonCustlist return null,id: " + id);
			return SUCCESS;
		}
		SeasonCustList sc = sca.getSeasonCustList();
		List<SeasonCustListPhone> phones = sca.getSeasonCustListPhoneList();
		if (sc == null) {
			errorMessage = this.getText("SeasonCustlist.args.error");
			logger.info(errorMessage + "sc==null");
			return SUCCESS;
		}

		boolean found = false;
		for (Long posId : posIds) {
			if (sc.getPosId().equals(posId)) {
				found = true;
				break;
			}
		}
		// Long posid = ucHelper.getCurrentPos().getPosid();
		if (!found) {
			errorMessage = this.getText("SeasonCustlist.args.error");
			logger.info(errorMessage + "posid not validate");
			return SUCCESS;
		}
		custName = sc.getCustName();
		url = sc.getUrl();
		invalidate = sc.getInvalidate();
		phoneList = new ArrayList<Map<String, Object>>();
		for (SeasonCustListPhone phone : phones) {
			Map<String, Object> tmp = new HashMap<String, Object>();
			tmp.put(ID, phone.getId());
			String phonenum = phone.getPhonenum();
			if (isMobilePhone(phonenum)) {
				tmp.put(PHONETYPE, MOBILE_PHONETYPE);
				tmp.put(PHONENUM, phonenum);
			} else {
				tmp.put(PHONETYPE, TELEPHONE_PHONETYPE);
				tmp.put(PHONEAREACODE, getPhoneAreacode(phonenum));
				tmp.put(PHONENUM, getPhonenum(phonenum));
				tmp.put(PHONEEXTEND, getPhoneExtend(phonenum));
			}
			phoneList.add(tmp);
		}
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

	private String getPhoneNum(String mobilephone, String phoneAreacode, String phoneNum, String phoneExtend) {
		if (isValidateMobileNum(mobilephone)) {
			return mobilephone;
		}
		return getValidatePhoneNum(phoneAreacode, phoneNum, phoneExtend);
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
		if (idPhones != null) {
			for (int i = 0; i < idPhones.length; i++) {
				tmp = new HashMap<String, Object>();
				tmp.put(ID, idPhones[i]);
				tmp.put(PHONETYPE, TELEPHONE_PHONETYPE);
				tmp.put(PHONEAREACODE, phoneAreacode[i]);
				tmp.put(PHONENUM, phoneNum[i]);
				tmp.put(PHONEEXTEND, phoneExtend[i]);
				phoneList.add(tmp);
			}
		}
		if (idMobiles != null) {
			for (int i = 0; i < idMobiles.length; i++) {
				tmp = new HashMap<String, Object>();
				tmp.put(ID, idMobiles[i]);
				tmp.put(PHONETYPE, MOBILE_PHONETYPE);
				tmp.put(PHONEAREACODE, "");
				tmp.put(PHONENUM, mobilephone[i]);
				tmp.put(PHONEEXTEND, "");
				phoneList.add(tmp);
			}
		}
		return SUCCESS;
	}

	/*
	 * 回填返回结果
	 */
	private String modSuccess() {
		// custName=null;
		// url=null;
		// id=null;
		phoneList = null;
		return SUCCESS;
	}

	/**
	 * 更新信息季节性备案客户名单,仅修改联系电话
	 * 
	 * @return
	 */
	public String modSeasonCustlist() {

		initPosids();
		Long ucid = ucHelper.getUser().getUcid();

		if (id == null) {
			errorMessage = this.getText("SeasonCustlist.args.error");
			logger.info(errorMessage + "id==null");
			return validateFail();
		}

		SeasonCustList sc = seasonCustListService.findById(id);
		if (sc == null) {
			errorMessage = this.getText("SeasonCustlistMgr.url.notexist");
			logger.info(errorMessage + "id: " + id);
			return validateFail();
		}

		Date now = new Date();
		sc.setUpdateId(ucid);
		sc.setUpdateTime(now);
		sc.setInvalidate(invalidate);
		seasonCustListService.addSeasonCustList(sc);

		if (idPhones != null) {
			for (int i = 0; i < idPhones.length; i++) {
				if (idPhones[i] == null || idPhones[i].length() == 0)// 新增电话
				{
					String ph = getValidatePhoneNum(phoneAreacode[i], phoneNum[i], phoneExtend[i]);
					if (ph != null) {
						SeasonCustListPhone scph = new SeasonCustListPhone();
						scph.setDelFlag((short) 0);
						scph.setPhonenum(ph);
						scph.setSeasonCustlistId(sc.getId());

						seasonCustListPhoneService.addSeasonCustListPhone(scph);
					}

				} else// 原有电话
				{
					String ph = getValidatePhoneNum(phoneAreacode[i], phoneNum[i], phoneExtend[i]);
					if (ph != null) {
						SeasonCustListPhone scph = new SeasonCustListPhone();
						scph.setDelFlag((short) 0);
						scph.setPhonenum(ph);
						scph.setSeasonCustlistId(sc.getId());
						scph.setId(Long.valueOf(idPhones[i]));
						seasonCustListPhoneService.addSeasonCustListPhone(scph);
					}
				}
			}
		}
		if (idMobiles != null) {
			for (int i = 0; i < idMobiles.length; i++) {
				if (idMobiles[i] == null || idMobiles[i].length() == 0)// 新增电话
				{
					if (isValidateMobileNum(mobilephone[i])) {
						SeasonCustListPhone scph = new SeasonCustListPhone();
						scph.setDelFlag((short) 0);
						scph.setPhonenum(mobilephone[i]);
						scph.setSeasonCustlistId(sc.getId());

						seasonCustListPhoneService.addSeasonCustListPhone(scph);
					}

				} else// 原有电话
				{
					if (isValidateMobileNum(mobilephone[i])) {
						SeasonCustListPhone scph = new SeasonCustListPhone();
						scph.setDelFlag((short) 0);
						scph.setPhonenum(mobilephone[i]);
						scph.setSeasonCustlistId(sc.getId());
						scph.setId(Long.valueOf(idMobiles[i]));
						seasonCustListPhoneService.addSeasonCustListPhone(scph);
					}
				}
			}
		}
		setModMessage("修改已成功!");
		return modSuccess();
	}

	/**
	 * 更新信息季节性备案客户名单，客户名、url、联系电话均修改
	 * 
	 * @return
	 */
	public String modSeasonCustlistOld() {

		initPosids();
		Long ucid = ucHelper.getUser().getUcid();
		// Long posid = ucHelper.getCurrentPos().getPosid();

		if (custName == null || custName.length() == 0 || custName.length() > CUSTNAME_MAX_LENGTH) {
			errorMessage = this.getText("SeasonCustlist.args.error");
			logger.info(errorMessage + "custName: " + custName);
			return validateFail();
		}
		if (url == null || url.length() == 0 || url.length() > URL_MAX_LENGTH) {
			errorMessage = this.getText("SeasonCustlist.args.error");
			logger.info(errorMessage + "url: " + url);
			return validateFail();
		}

		if (id == null) {
			errorMessage = this.getText("SeasonCustlist.args.error");
			logger.info(errorMessage + "id==null");
			return validateFail();
		}
		// 是否是老客户
		List<Long> custids = shifenCustomerService.selectCustIdByCompanyNameSiteUrl(custName, url);
		if (custids == null || custids.size() == 0) {
			errorMessage = this.getText("ShifenCust.url.notexist");
			logger.info(errorMessage + "Url: " + url + "CustName: " + custName);
			return validateFail();
		}
		// Long custid = custids.get(0);
		// 表内是否已存在此url 或者是当前要修改的记录

		List<SeasonCustList> scls = seasonCustListService.equalUrl(url, posIds);
		if (scls != null && scls.size() > 0) {
			SeasonCustList scl = scls.get(0);
			if (!scl.getId().equals(id)) {
				errorMessage = this.getText("SeasonCustlistMgr.url.exist");
				logger.info(errorMessage + "url: " + url);
				return validateFail();
			}
		}

		SeasonCustList sc = seasonCustListService.findById(id);
		if (sc == null) {
			errorMessage = this.getText("SeasonCustlistMgr.url.notexist");
			logger.info(errorMessage + "id: " + id);
			return validateFail();
		}

		Date now = new Date();
		sc.setCustName(custName);
		sc.setDelFlag((short) 0);
		sc.setUpdateId(ucid);
		sc.setUpdateTime(now);
		sc.setUrl(url);

		seasonCustListService.addSeasonCustList(sc);

		if (idPhones != null) {
			for (int i = 0; i < idPhones.length; i++) {
				if (idPhones[i] == null || idPhones[i].length() == 0)// 新增电话
				{
					String ph = getValidatePhoneNum(phoneAreacode[i], phoneNum[i], phoneExtend[i]);
					if (ph != null) {
						SeasonCustListPhone scph = new SeasonCustListPhone();
						scph.setDelFlag((short) 0);
						scph.setPhonenum(ph);
						scph.setSeasonCustlistId(sc.getId());

						seasonCustListPhoneService.addSeasonCustListPhone(scph);
					}

				} else// 原有电话
				{
					String ph = getValidatePhoneNum(phoneAreacode[i], phoneNum[i], phoneExtend[i]);
					if (ph != null) {
						SeasonCustListPhone scph = new SeasonCustListPhone();
						scph.setDelFlag((short) 0);
						scph.setPhonenum(ph);
						scph.setSeasonCustlistId(sc.getId());
						scph.setId(Long.valueOf(idPhones[i]));
						seasonCustListPhoneService.addSeasonCustListPhone(scph);
					}
				}
			}
		}
		if (idMobiles != null) {
			for (int i = 0; i < idMobiles.length; i++) {
				if (idMobiles[i] == null || idMobiles[i].length() == 0)// 新增电话
				{
					if (isValidateMobileNum(mobilephone[i])) {
						SeasonCustListPhone scph = new SeasonCustListPhone();
						scph.setDelFlag((short) 0);
						scph.setPhonenum(mobilephone[i]);
						scph.setSeasonCustlistId(sc.getId());

						seasonCustListPhoneService.addSeasonCustListPhone(scph);
					}

				} else// 原有电话
				{
					if (isValidateMobileNum(mobilephone[i])) {
						SeasonCustListPhone scph = new SeasonCustListPhone();
						scph.setDelFlag((short) 0);
						scph.setPhonenum(mobilephone[i]);
						scph.setSeasonCustlistId(sc.getId());
						scph.setId(Long.valueOf(idMobiles[i]));
						seasonCustListPhoneService.addSeasonCustListPhone(scph);
					}
				}
			}
		}
		setModMessage("修改已成功!");
		return modSuccess();
	}

	/**
	 * 删除电话 异步请求 (带错误提示文本errorMsg变量跳转到delPhone.json.vm文件中)
	 * 
	 * @return
	 */
	public String delSeasonCustlist() {

		Long ucid = ucHelper.getUser().getUcid();
		// Long posid = ucHelper.getCurrentPos().getPosid();
		if (id == null) {
			errorMsg = this.getText("SeasonCustlist.args.error");
			logger.info(errorMsg + "id: " + id);
			return SUCCESS;
		}
		logger.info("ucid: " + ucid + "delShifenCustWhitelist id:" + id);
		seasonCustListPhoneService.deleteSeasonCustListPhone(id);
		return SUCCESS;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrornMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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

	public List<Map<String, Object>> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<Map<String, Object>> phoneList) {
		this.phoneList = phoneList;
	}

	public String[] getIdPhones() {
		return idPhones;
	}

	public void setIdPhones(String[] idPhones) {
		this.idPhones = idPhones;
	}

	public String[] getIdMobiles() {
		return idMobiles;
	}

	public void setIdMobiles(String[] idMobiles) {
		this.idMobiles = idMobiles;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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

	public String getModMessage() {
		return modMessage;
	}

	public void setModMessage(String modMessage) {
		this.modMessage = modMessage;
	}

	public List<Long> getPosIds() {
		initPosids();
		return posIds;
	}

	public void setPosIds(List<Long> posIds) {
		this.posIds = posIds;
	}
}

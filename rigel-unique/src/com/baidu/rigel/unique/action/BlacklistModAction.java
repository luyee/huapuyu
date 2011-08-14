package com.baidu.rigel.unique.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.baidu.rigel.unique.bo.Blacklist;
import com.baidu.rigel.unique.bo.BlacklistPhone;
import com.baidu.rigel.unique.common.BaseActionSupport;
import com.baidu.rigel.unique.service.BlacklistPhoneService;
import com.baidu.rigel.unique.service.BlacklistService;
import com.baidu.rigel.unique.service.BlacklistVoService;
import com.baidu.rigel.unique.vo.BlacklistVo;

/**
 * 黑名单修改
 * 
 * @author LiTao
 * 
 */
@SuppressWarnings("serial")
public class BlacklistModAction extends BaseActionSupport {

	private final static Logger logger = LogManager.getLogger(BlacklistModAction.class);

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

	// 客户名称
	private String custName;
	// URL
	private String url;
	// 联系电话
	private List<Map<String, Object>> phoneList;
	// 来源
	private Short src;

	private Long id;
	private String errorMsg;

	private String[] phoneAreacode;
	private String[] phoneNum;
	private String[] phoneExtend;
	private String[] mobilephone;
	private String[] idPhones;
	private String[] idMobiles;

	private String errorMessage;
	private String modMessage;

	private BlacklistVoService blacklistVoService;
	private BlacklistService blacklistService;
	private BlacklistPhoneService blacklistPhoneService;

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
	 * 页面访问黑名单
	 * 
	 * @return
	 */
	public String initBlacklist() {

		BlacklistVo bla = blacklistVoService.findBlacklistVo(id);
		if (bla == null) {
			errorMessage = this.getText("Blacklist.args.error");
			logger.info(errorMessage + "queryBlacklistAll return null,id: " + id);
			return SUCCESS;
		}
		Blacklist bl = bla.getBlacklist();
		List<BlacklistPhone> phones = bla.getBlacklistPhoneList();

		if (bl == null) {
			errorMessage = this.getText("Blacklist.args.error");
			logger.info(errorMessage + "bl==null");
			return SUCCESS;
		}
		custName = bl.getCompanyName();
		url = bl.getUrl();
		phoneList = new ArrayList<Map<String, Object>>();
		src = bl.getSrc();
		for (BlacklistPhone phone : phones) {
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
	 * 更新黑名单
	 * 
	 * @return
	 */
	public String modBlacklist() {

		// TODO Anders Zhu : mock 测试
		// Long ucid = ucHelper.getUser().getUcid();
		Long ucid = 0L;

		if (id == null) {
			errorMessage = this.getText("Blacklist.args.error");
			logger.info(errorMessage + "id==null");
			return validateFail();
		}
		if (src == null) {
			errorMessage = this.getText("Blacklist.args.error");
			logger.info(errorMessage + "src==null");
			return validateFail();
		}
		Blacklist bl = blacklistService.findById(id);
		if (bl == null) {
			errorMessage = this.getText("Blacklist.args.error");
			logger.info(errorMessage + "id: " + id);
			return validateFail();
		}

		Date now = new Date();
		bl.setUpdateId(ucid);
		bl.setUpdateTime(now);
		bl.setSrc(src);
		blacklistService.saveOrUpdate(bl);

		if (idPhones != null) {
			for (int i = 0; i < idPhones.length; i++) {
				if (idPhones[i] == null || idPhones[i].length() == 0)// 新增电话
				{
					String ph = getValidatePhoneNum(phoneAreacode[i], phoneNum[i], phoneExtend[i]);
					if (ph != null) {
						BlacklistPhone blph = new BlacklistPhone();
						blph.setDelFlag((short) 0);
						blph.setPhonenum(ph);
						blph.setBlacklistId(bl.getBlacklistId());
						blacklistPhoneService.saveOrUpdate(blph);
					}

				} else// 原有电话
				{
					String ph = getValidatePhoneNum(phoneAreacode[i], phoneNum[i], phoneExtend[i]);
					if (ph != null) {
						BlacklistPhone blph = new BlacklistPhone();
						blph.setDelFlag((short) 0);
						blph.setPhonenum(ph);
						blph.setBlacklistId(bl.getBlacklistId());
						blph.setId(Long.valueOf(idPhones[i]));
						blacklistPhoneService.saveOrUpdate(blph);
					}
				}
			}
		}
		if (idMobiles != null) {
			for (int i = 0; i < idMobiles.length; i++) {
				if (idMobiles[i] == null || idMobiles[i].length() == 0)// 新增电话
				{
					if (isValidateMobileNum(mobilephone[i])) {
						BlacklistPhone blph = new BlacklistPhone();
						blph.setDelFlag((short) 0);
						blph.setPhonenum(mobilephone[i]);
						blph.setBlacklistId(bl.getBlacklistId());
						blacklistPhoneService.saveOrUpdate(blph);
					}

				} else// 原有电话
				{
					if (isValidateMobileNum(mobilephone[i])) {
						BlacklistPhone blph = new BlacklistPhone();
						blph.setDelFlag((short) 0);
						blph.setPhonenum(mobilephone[i]);
						blph.setBlacklistId(bl.getBlacklistId());
						blph.setId(Long.valueOf(idMobiles[i]));
						blacklistPhoneService.saveOrUpdate(blph);
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
	public String delPhone() {

		// TODO Anders Zhu : mock 测试
		// Long ucid = ucHelper.getUser().getUcid();
		Long ucid = 0L;

		if (id == null) {
			errorMsg = this.getText("Blacklist.args.error");
			logger.info(errorMsg + "id: " + id);
			return SUCCESS;
		}
		logger.info("ucid: " + ucid + "delPone id:" + id);
		blacklistPhoneService.deleteBlacklistPhone(id);
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

	public void setErrorMessage(String errorMessage) {
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

	public Short getSrc() {
		return src;
	}

	public void setSrc(Short src) {
		this.src = src;
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

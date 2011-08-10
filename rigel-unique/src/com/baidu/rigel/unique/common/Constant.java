package com.baidu.rigel.unique.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.baidu.rigel.unique.utils.AutoAuditType;

public class Constant extends DicConstant {
	/**
	 * 存放在HttpSession中的标记是否登录呼叫中心<Boolean>
	 */
	public static final String SESSION_KEY_IS_LOGIN_AGENT = "session_key_is_login_agent";
	/**
	 * 存放在HttpSession中的agent<Agent>
	 */
	public static final String SESSION_KEY_AGENT_INFO = "session_key_agent_info";
	/**
	 * 存放在HttpSession中的修改资料列表ID(联系人不可以修改),存放的数据内容为List<Long>
	 */
	public static final String SESSION_KEY_UPDATE_CUST_ID_LIST = "session_key_update_cust_id_list";
	/**
	 * 存放在HttpSession中的查看客户资料列表ID,存放的数据内容为List<Long>
	 */
	public static final String SESSION_KEY_VIEW_CUST_ID_LIST = "session_key_view_cust_id_list";

	/**
	 * 存放在memcached中的地域词
	 */
	public static final String GLOBAL_KEY_AREAWORD_LIST = "common_global_key_areaword_list";
	/**
	 * 请选择默认值
	 */
	public static final Long PLEASE_SELECT_VALUE = -1L;
	public static final Long ZERO = 0L;
	/** 页面选择框有全选的时候，全选对应的value为-1 */
	public static final short SELECT_ALL = -1;
	/** 页面选择框有全选的时候，页面名称“全选” */
	public static final String SELECT_ALL_NAME = "全部";

	public static final Integer ALL_DATA_SELECTED = 0;

	/**
	 * 删除标记：已删除
	 */
	public static final Short DELETE_FLAG_Y = 1;
	/**
	 * 删除标记：未删除
	 */
	public static final Short DELETE_FLAG_N = 0;
	/**
	 * 电话标记-是否是收件人：不是收件人(即联系人)
	 */
	public static final Short RECIPIENT_FLAG_N = 0;
	/**
	 * 电话标记-是否是收件人：是收件人
	 */
	public static final Short RECIPIENT_FLAG_Y = 1;
	/**
	 * 黑名单标记 0:黑名单
	 */
	public static final Short BLACK_FLAG_Y = 0;
	/**
	 * 黑名单标记 1:正常
	 */
	public static final Short BLACK_FLAG_N = 1;
	/**
	 * 资料来源 ：0:销售录入 //TODO 统一使用CUST_INPUT_TYPE_0
	 */
	public static final Short INPUT_TYPE_SALE = 0;
	/**
	 * 资料来源 ：1:批量导入 //TODO 统一使用CUST_INPUT_TYPE_1
	 */
	public static final Short INPUT_TYPE_BATCH = 1;
	/**
	 * 资料来源 ：2:网上注册 //TODO 统一使用CUST_INPUT_TYPE_2
	 */
	public static final Short INPUT_TYPE_REGISTER = 2;
	/**
	 * 资料来源 ：3:来电咨询 //TODO 统一使用CUST_INPUT_TYPE_3
	 */
	public static final Short INPUT_TYPE_CALLIN = 3;
	/**
	 * 资料提交方式：0:优先审核
	 */
	public static final short CUST_PRIORITY_0 = 0;

	public static final String CUST_PRIORITY_0_NAME = "优先审核";
	/**
	 * 资料提交方式：1:入自有库
	 */
	public static final short CUST_PRIORITY_1 = 1;

	public static final String CUST_PRIORITY_1_NAME = "入自有库";

	/**
	 * 资料提交方式：2:入公共库
	 */
	public static final short CUST_PRIORITY_2 = 2;

	public static final String CUST_PRIORITY_2_NAME = "入公共库";

	/***/
	public static final Map<Short, String> CUST_PRIORITY_MAP = new HashMap<Short, String>();
	static {
		CUST_PRIORITY_MAP.put(CUST_PRIORITY_0, CUST_PRIORITY_0_NAME);
		CUST_PRIORITY_MAP.put(CUST_PRIORITY_1, CUST_PRIORITY_1_NAME);
		CUST_PRIORITY_MAP.put(CUST_PRIORITY_2, CUST_PRIORITY_2_NAME);
	}

	/**
	 * 前台需要传递给FE的值,提交值
	 */
	public static final String ID = "value";
	/**
	 * 前台需要传递给FE的值,显示值
	 */
	public static final String NAME = "text";
	/**
	 * 前台需要传递给FE的值,其他值
	 */
	public static final String REMARK = "remark";
	/**
	 * 前台需要传递给FE的值,是否被选中
	 */
	public static final String SELECT = "isChecked";
	/** 客户一级状态码 0：新提交 */
	public static final short CUST_STAT_1_0 = 0;
	/** 客户一级状态码 1：确认中 */
	public static final short CUST_STAT_1_1 = 1;
	/** 客户一级状态码 2：待分配 */
	public static final short CUST_STAT_1_2 = 2;
	/** 客户一级状态码 3：跟进中 */
	public static final short CUST_STAT_1_3 = 3;
	/** 客户一级状态码 4：订单审核中 */
	public static final short CUST_STAT_1_4 = 4;
	/** 客户一级状态码 5：已成单 */
	public static final short CUST_STAT_1_5 = 5;
	/** 客户一级状态码 6：放弃 */
	public static final short CUST_STAT_1_6 = 6;
	/** 客户一级状态码 7：废弃 */
	public static final short CUST_STAT_1_7 = 7;
	/** 客户一级状态码 8：分配到申领池 */
	public static final short CUST_STAT_1_8 = 8;
	// 呼入模块添加的一级状态
	/** 客户一级状态码 9：待下发 */
	public static final short CUST_STAT_1_9 = 9;
	/** 客户一级状态码 10：下发前废弃 */
	// public static final short CUST_STAT_1_10 = 10;
	/** 客户一级状态码 11：已下发 */
	public static final short CUST_STAT_1_11 = 11;

	/** 客户二级状态码 10：确认中 */
	public static final short CUST_STAT_2_10 = 10;
	/** 客户二级状态码 11：待审核 */
	public static final short CUST_STAT_2_11 = 11;
	/** 客户二级状态码 12：待清洗 */
	public static final short CUST_STAT_2_12 = 12;
	/** 客户二级状态码 13：待审核需清洗 */
	public static final short CUST_STAT_2_13 = 13;
	/** 客户二级状态码 14：已审核待清洗 */
	public static final short CUST_STAT_2_14 = 14;
	/** 客户二级状态码 20：已审核 */
	public static final short CUST_STAT_2_20 = 20;
	/** 客户二级状态码 21：已清洗 */
	public static final short CUST_STAT_2_21 = 21;
	/** 客户二级状态码 22：搁置期满 */
	public static final short CUST_STAT_2_22 = 22;
	/** 客户二级状态码 23: 不审核不清洗 */
	// TODO 注意新增的状态
	public static final short CUST_STAT_2_23 = 23;

	/** 客户二级状态码 30：跟进初始状态 */
	public static final short CUST_STAT_2_30 = 30;
	/** 客户二级状态码 40：订单审核中 */
	public static final short CUST_STAT_2_40 = 40;
	/** 客户二级状态码 50：成单 */
	public static final short CUST_STAT_2_50 = 50;
	/** 客户二级状态码 60：客户置换 */
	public static final short CUST_STAT_2_60 = 60;
	/** 客户二级状态码 61：申请废弃 */
	public static final short CUST_STAT_2_61 = 61;
	/** 客户二级状态码 70：审核废弃 */
	public static final short CUST_STAT_2_70 = 70;
	public static final String CUST_STAT_2_70_NAME = "审核废弃";
	/** 客户二级状态码 71：清洗废弃 */
	public static final short CUST_STAT_2_71 = 71;
	public static final String CUST_STAT_2_71_NAME = "清洗废弃";
	/** 客户二级状态码 72：管理废弃 */
	public static final short CUST_STAT_2_72 = 72;
	public static final String CUST_STAT_2_72_NAME = "管理废弃";
	/** 客户二级状态码 73：代理商废弃 housz callin */
	public static final short CUST_STAT_2_73 = 73;
	public static final String CUST_STAT_2_73_NAME = "代理商废弃";
	/** 客户二级状态码 74：置换放弃废弃 */
	public static final short CUST_STAT_2_74 = 74;
	public static final String CUST_STAT_2_74_NAME = "置换放弃废弃";
	/** 客户二级状态码 75：跟进中修改审核废弃 */
	public static final short CUST_STAT_2_75 = 75;
	public static final String CUST_STAT_2_75_NAME = "跟进中修改审核废弃";
	/** 客户二级状态码 76：离职管理废弃 */
	public static final short CUST_STAT_2_76 = 76;
	public static final String CUST_STAT_2_76_NAME = "离职管理废弃";
	/** 客户二级状态码 77：销售自己废弃 */
	public static final short CUST_STAT_2_77 = 77;
	public static final String CUST_STAT_2_77_NAME = "销售自己废弃";
	/** 客户二级状态码 78：电子商务客户资料管理废弃 */
	public static final short CUST_STAT_2_78 = 78;
	public static final String CUST_STAT_2_78_NAME = "电商管理废弃";
	/** 客户二级状态码 79：CSC下发前废弃 */
	public static final short CUST_STAT_2_79 = 79;
	public static final String CUST_STAT_2_79_NAME = "CSC下发前废弃";
	/** 客户二级状态码 81：CSC驳回管理废弃 */
	public static final short CUST_STAT_2_81 = 81;
	public static final String CUST_STAT_2_81_NAME = "CSC驳回管理废弃";
	/** 客户二级状态码 80：分配到申领池 */
	public static final short CUST_STAT_2_80 = 80;
	/** 客户二级状态码 99：公共库清理废弃 */
	public static final short CUST_STAT_2_99 = 99;
	public static final String CUST_STAT_2_99_NAME = "公共库清理废弃";
	/** 客户二级状态码 98：申请废弃 */
	public static final short CUST_STAT_2_98 = 98;
	public static final String CUST_STAT_2_98_NAME = "申请废弃";

	public static final Map<Short, String> CUST_STAT_1_MAP = new TreeMap<Short, String>();
	static {
		CUST_STAT_1_MAP.put(Constant.CUST_STAT_1_0, "新提交");
		CUST_STAT_1_MAP.put(Constant.CUST_STAT_1_1, "确认中");
		CUST_STAT_1_MAP.put(Constant.CUST_STAT_1_2, "待分配 ");
		CUST_STAT_1_MAP.put(Constant.CUST_STAT_1_3, "跟进中");
		CUST_STAT_1_MAP.put(Constant.CUST_STAT_1_4, "订单审核中");
		CUST_STAT_1_MAP.put(Constant.CUST_STAT_1_5, "已成单");
		CUST_STAT_1_MAP.put(Constant.CUST_STAT_1_6, "已放弃");
		CUST_STAT_1_MAP.put(Constant.CUST_STAT_1_7, "废弃");
		CUST_STAT_1_MAP.put(Constant.CUST_STAT_1_8, "分配到申领池");
	}
	/**
	 * 所有废弃类型-MAP
	 */
	public static final Map<Short, String> CUST_STAT_7_MAP = new HashMap<Short, String>();
	static {
		CUST_STAT_7_MAP.put(CUST_STAT_2_70, CUST_STAT_2_70_NAME);
		CUST_STAT_7_MAP.put(CUST_STAT_2_71, CUST_STAT_2_71_NAME);
		CUST_STAT_7_MAP.put(CUST_STAT_2_72, CUST_STAT_2_72_NAME);
		CUST_STAT_7_MAP.put(CUST_STAT_2_73, CUST_STAT_2_73_NAME);
		CUST_STAT_7_MAP.put(CUST_STAT_2_74, CUST_STAT_2_74_NAME);
		CUST_STAT_7_MAP.put(CUST_STAT_2_75, CUST_STAT_2_75_NAME);
		CUST_STAT_7_MAP.put(CUST_STAT_2_76, CUST_STAT_2_76_NAME);
		CUST_STAT_7_MAP.put(CUST_STAT_2_77, CUST_STAT_2_77_NAME);
		CUST_STAT_7_MAP.put(CUST_STAT_2_78, CUST_STAT_2_78_NAME);
		CUST_STAT_7_MAP.put(CUST_STAT_2_79, CUST_STAT_2_79_NAME);
		CUST_STAT_7_MAP.put(CUST_STAT_2_81, CUST_STAT_2_81_NAME);
		CUST_STAT_7_MAP.put(CUST_STAT_2_99, CUST_STAT_2_99_NAME);
		CUST_STAT_7_MAP.put(CUST_STAT_2_98, CUST_STAT_2_98_NAME);
	}
	/**
	 * 所有废弃类型-FE接口形式
	 */
	public static final List<Map<String, Object>> CUST_STAT_7_LIST = new ArrayList<Map<String, Object>>();
	static {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.ID, (short) -1);
		map.put(Constant.NAME, "全部");
		CUST_STAT_7_LIST.add(map);
		Iterator<Short> it = CUST_STAT_7_MAP.keySet().iterator();
		while (it.hasNext()) {
			Short type = it.next();
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put(Constant.ID, type);
			temp.put(Constant.NAME, CUST_STAT_7_MAP.get(type));
			CUST_STAT_7_LIST.add(temp);
		}
	}

	// 呼入模块添加的二级状态
	/** 客户二级状态码 55：已下发 呼出模块已使用100以上的二级状态，故使用55 */
	public static final short CUST_STAT_2_55 = 55;
	/** 客户二级状态码 90: 待下发 */
	public static final short CUST_STAT_2_90 = 90;
	/** 客户二级状态码 35: 下发前废弃 呼出模块已使用100以上的二级状态，故使用35 */
	// public static final short CUST_STAT_2_35 = 35;

	/** 所有级别的行业级别 */
	public static final int TRADE_LEVEL_ALL = 0;
	/** 一级行业级别 */
	public static final int TRADE_FIRST_LEVEL = 1;
	/** 二级行业级别 */
	public static final int TRADE_SECOND_LEVEL = 2;

	/** 客户级别--需要指定预期成单时间 */
	public static final short CUST_LEVEL_ORDERTIME_Y = 1;
	/** 客户级别--不需要指定预期成单时间 */
	public static final short CUST_LEVEL_ORDERTIME_N = 0;

	/** 客户级别--需要提单申请 */
	public static final short CUST_LEVEL_ORDERAPPLY_Y = 1;
	/** 客户级别--不需要提单申请 */
	public static final short CUST_LEVEL_ORDERAPPLY_N = 0;

	/** 地域-全部 */
	public static final int AREA_ALL = -1;
	/** 地域-省级 */
	public static final int AREA_PROVINCE = 0;
	/** 地域-市级 */
	public static final int AREA_CITY = 1;
	/** 地域-区级 */
	public static final int AREA_DISTRICT = 2;

	/** 每页数据条数 */
	public static final int PAGE_NUM = 80;
	/** 每页数据条数 */
	public static final int FIRST_PAGE_NUM = 1;
	/** 树形结构中根节点的父节点id */
	public static final long ROOT_PARENT = 0;
	/** 排序方式-升序 */
	public static final int SORT_TYPE_ASC = 1;
	/** 排序方式-降序序 */
	public static final int SORT_TYPE_DESC = 2;

	/** 名字长度限制 */
	public static final int NAME_LENGTH = 20;
	/** 备注长度限制 */
	public static final int REMARK_LENGTH = 200;
	/** 批注类型-跟进 */
	public static final int POSTIL_FOLLOWUP = 0;
	public static final String POSTIL_FOLLOWUP_NAME = "跟进批注";
	/** 批注类型-置换 */
	public static final int POSTIL_EXCHANGE = 1;
	public static final String POSTIL_EXCHANGE_NAME = "客户置换原因";
	/** 批注类型-放弃 */
	public static final int POSTIL_GIVEUP = 2;
	public static final String POSTIL_GIVEUP_NAME = "申请废弃原因";
	/** 批注类型-名称对应map */
	public static final Map<Integer, String> POSTIL_NAME_MAP = new HashMap<Integer, String>();
	static {
		POSTIL_NAME_MAP.put(POSTIL_FOLLOWUP, POSTIL_FOLLOWUP_NAME);
		POSTIL_NAME_MAP.put(POSTIL_EXCHANGE, POSTIL_EXCHANGE_NAME);
		POSTIL_NAME_MAP.put(POSTIL_GIVEUP, POSTIL_GIVEUP_NAME);
	}
	/** 客户类型 --普通企业--value */
	public static final int CUST_TYPE_COMMON = 0;
	/** 客户类型 --普通企业--name */
	public static final String CUST_TYPE_COMMON_NAME = "普通企业";
	/** 客户类型 --特殊企业--value */
	public static final int CUST_TYPE_SPECIAL = 1;
	/** 客户类型 --特殊企业--name */
	public static final String CUST_TYPE_SPECIAL_NAME = "特殊企业";
	/** 客户类型 --个人客户--value */
	public static final int CUST_TYPE_PERSON = 2;
	/** 客户类型 --个人客户--name */
	public static final String CUST_TYPE_PERSON_NAME = "个人客户";
	/** 客户类型 --广告代理公司--value */
	public static final int CUST_TYPE_AD_PROXY = 3;
	/** 客户类型 --广告代理公司--name */
	public static final String CUST_TYPE_AD_PROXY_NAME = "广告代理公司";
	public static final Map<Integer, String> CUST_TYPE_MAP = new HashMap<Integer, String>();
	static {
		CUST_TYPE_MAP.put(CUST_TYPE_COMMON, CUST_TYPE_COMMON_NAME);
		CUST_TYPE_MAP.put(CUST_TYPE_SPECIAL, CUST_TYPE_SPECIAL_NAME);
		CUST_TYPE_MAP.put(CUST_TYPE_PERSON, CUST_TYPE_PERSON_NAME);
		CUST_TYPE_MAP.put(CUST_TYPE_AD_PROXY, CUST_TYPE_AD_PROXY_NAME);
	}

	/**
	 * 资料来源 ：0:销售录入
	 */
	public static final int CUST_INPUT_TYPE_0 = 0;
	public static final String CUST_INPUT_TYPE_0_NAME = "销售录入";
	/**
	 * 资料来源 ：1:批量导入
	 */
	public static final int CUST_INPUT_TYPE_1 = 1;
	public static final String CUST_INPUT_TYPE_1_NAME = "批量导入";
	/**
	 * 资料来源 ：2:网上注册
	 */
	public static final int CUST_INPUT_TYPE_2 = 2;
	public static final String CUST_INPUT_TYPE_2_NAME = "网上申请";
	/**
	 * 资料来源 ：3:来电咨询
	 */
	public static final int CUST_INPUT_TYPE_3 = 3;
	public static final String CUST_INPUT_TYPE_3_NAME = "来电咨询";
	/**
	 * 资料来源 ：4：来电留言
	 */
	public static final int CUST_INPUT_TYPE_4 = 4;
	public static final String CUST_INPUT_TYPE_4_NAME = "来电留言";
	/**
	 * 资料来源 ：5：IM沟通
	 */
	public static final int CUST_INPUT_TYPE_5 = 5;
	public static final String CUST_INPUT_TYPE_5_NAME = "IM沟通";
	/**
	 * 资料来源 ：6：www2
	 */
	public static final int CUST_INPUT_TYPE_6 = 6;
	public static final String CUST_INPUT_TYPE_6_NAME = "www2注册";
	/**
	 * 资料来源 ：7：其他
	 */
	public static final int CUST_INPUT_TYPE_7 = 7;
	public static final String CUST_INPUT_TYPE_7_NAME = "其他来源";
	/**
	 * 资料来源 ：8：线索转化
	 */
	public static final int CUST_INPUT_TYPE_8 = 8;
	public static final String CUST_INPUT_TYPE_8_NAME = "线索转化";

	/**
	 * 客户资料来源-MAP
	 */
	public static final Map<Integer, String> CUST_INPUT_TYPE_MAP = new HashMap<Integer, String>();
	static {
		CUST_INPUT_TYPE_MAP.put(CUST_INPUT_TYPE_0, CUST_INPUT_TYPE_0_NAME);
		CUST_INPUT_TYPE_MAP.put(CUST_INPUT_TYPE_1, CUST_INPUT_TYPE_1_NAME);
		CUST_INPUT_TYPE_MAP.put(CUST_INPUT_TYPE_2, CUST_INPUT_TYPE_2_NAME);
		CUST_INPUT_TYPE_MAP.put(CUST_INPUT_TYPE_3, CUST_INPUT_TYPE_3_NAME);
		CUST_INPUT_TYPE_MAP.put(CUST_INPUT_TYPE_4, CUST_INPUT_TYPE_4_NAME);
		CUST_INPUT_TYPE_MAP.put(CUST_INPUT_TYPE_5, CUST_INPUT_TYPE_5_NAME);
		CUST_INPUT_TYPE_MAP.put(CUST_INPUT_TYPE_6, CUST_INPUT_TYPE_6_NAME);
		CUST_INPUT_TYPE_MAP.put(CUST_INPUT_TYPE_7, CUST_INPUT_TYPE_7_NAME);
		CUST_INPUT_TYPE_MAP.put(CUST_INPUT_TYPE_8, CUST_INPUT_TYPE_8_NAME);
	}

	public static boolean isCallout(Number custInputType) {
		if (custInputType == null) {
			return false;
		} else {
			if (custInputType.intValue() == CUST_INPUT_TYPE_0 || custInputType.intValue() == CUST_INPUT_TYPE_1 || custInputType.intValue() == CUST_INPUT_TYPE_8) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static boolean isCallin(Number custInputType) {
		if (custInputType == null) {
			return false;
		} else {
			if (custInputType.intValue() == CUST_INPUT_TYPE_2 || custInputType.intValue() == CUST_INPUT_TYPE_3 || custInputType.intValue() == CUST_INPUT_TYPE_4 || custInputType.intValue() == CUST_INPUT_TYPE_5 || custInputType.intValue() == CUST_INPUT_TYPE_6 || custInputType.intValue() == CUST_INPUT_TYPE_7) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 客户资料来源-FE接口形式
	 */
	public static final List<Map<String, Object>> CUST_INPUT_TYPE_LIST = new ArrayList<Map<String, Object>>();
	static {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.ID, (short) -1);
		map.put(Constant.NAME, "全部");
		CUST_INPUT_TYPE_LIST.add(map);
		Iterator<Integer> it = CUST_INPUT_TYPE_MAP.keySet().iterator();
		while (it.hasNext()) {
			Integer type = it.next();
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put(Constant.ID, type);
			temp.put(Constant.NAME, CUST_INPUT_TYPE_MAP.get(type));
			CUST_INPUT_TYPE_LIST.add(temp);
		}
	}
	/**
	 * 跟进超时,定时任务更新状态时USREID
	 */
	public static final Long SYSTEM_USER_ID = 0L;
	/**
	 * 跟进超时,定时任务更新状态时岗位ID
	 */
	public static final Long SYSTEM_POSI_ID = 0L;

	/** 销售电话表显示-无记录时主键值 */
	public static final Long SALES_PHONE_ID_NONE = 0L;

	/** 数据库十位对应无符号整数型的最大值 */
	public static final long TEN_BIT_UNSIGNED_MAX_VALUE = 4294967295L;
	/** 数据库十位对应有符号整数的最大值 */
	public static final long TEN_BIT_SIGNED_MAX_VALUE = 2147483647;
	/**
	 * 存放在HttpSession中可以提单的custId对应的key
	 */
	public static final String SESSION_ATTRIBUTE_CUSTOMER_ID = "session_attribute_customer_id";

	/**
	 * 在导出excel格式数据时,每个sheet页里最多的数据条数(不包含标题行)
	 */
	public static final int EXCEL_MAX_DATA_NUM = 60000;
	// ==========housz Excel单元格格式常量定义
	/** 单元格常规格式 */
	public static final int CELL_FORMAT_NORMAL = 1;
	public static final String CELL_FORMAT_NORMAL_VALUE = "normal";
	/** 单元格文本格式 */
	public static final int CELL_FORMAT_TEXT = 2;
	public static final String CELL_FORMAT_TEXT_VALUE = "text";

	/**
	 * 存放在memcache中的需要审核清洗自动分配的客户资料列表ID,存放的数据内容为String,多个id用逗号分隔
	 */
	public static final String SESSION_KEY_CUST_AUDIT_ASSIGN_ID_STRING = "session_key_cust_audit_assign_id_string";

	/**
	 * 跟进记录-跟进方式，0： 客户来电咨询
	 */
	public static final Long CUST_POSTIL_METHOD_CALLIN = new Long(0);
	/**
	 * 跟进记录-跟进方式，1： 经电话确认
	 */
	public static final Long CUST_POSTIL_METHOD_CALLOUT = new Long(1);
	/**
	 * 跟进记录-跟进方式，2： 未经电话确认
	 */
	public static final Long CUST_POSTIL_METHOD_NO_CALLOUT = new Long(2);

	/**
	 * 跟进记录-跟进方式，3： CSC跟进记录
	 */
	public static final Long CUST_POSTIL_METHOD_CSC = new Long(3);
	/**
	 * 多渠道客户资料录入
	 */
	public static final Long CUST_POSTIL_METHOD_CSC_INPUT = new Long(4);
	/**
	 * 跟进记录-跟进方式，4： 留言跟进记录
	 */
	public static final Long CUST_POSTIL_METHOD_MSG = Long.valueOf(5);

	public static final String CUST_POSTIL_METHOD_CALLIN_TEXT = "客户来电咨询";
	public static final String CUST_POSTIL_METHOD_CALLOUT_TEXT = "经电话确认";
	public static final String CUST_POSTIL_METHOD_NO_CALLOUT_TEXT = "未经电话确认";
	public static final String CUST_POSTIL_METHOD_CSC_TEXT = "CSC跟进记录";
	public static final String CUST_POSTIL_METHOD_CSC_INPUT_TEXT = "多渠道客户资料";
	public static final String CUST_POSTIL_METHOD_MSG_TEXT = "留言电话确认";
	/**
	 * 跟进方式取值与显示文字的映射表
	 */
	public static final Map<Long, String> POSTIL_METHOD_TEXT_MAP = new HashMap<Long, String>();
	static {
		POSTIL_METHOD_TEXT_MAP.put(CUST_POSTIL_METHOD_CALLIN, CUST_POSTIL_METHOD_CALLIN_TEXT);
		POSTIL_METHOD_TEXT_MAP.put(CUST_POSTIL_METHOD_CALLOUT, CUST_POSTIL_METHOD_CALLOUT_TEXT);
		POSTIL_METHOD_TEXT_MAP.put(CUST_POSTIL_METHOD_NO_CALLOUT, CUST_POSTIL_METHOD_NO_CALLOUT_TEXT);
		POSTIL_METHOD_TEXT_MAP.put(CUST_POSTIL_METHOD_CSC, CUST_POSTIL_METHOD_CSC_TEXT);
		POSTIL_METHOD_TEXT_MAP.put(CUST_POSTIL_METHOD_CSC_INPUT, CUST_POSTIL_METHOD_CSC_INPUT_TEXT);
		POSTIL_METHOD_TEXT_MAP.put(CUST_POSTIL_METHOD_MSG, CUST_POSTIL_METHOD_MSG_TEXT);
	}

	/**
	 * 跟进记录-跟进类型，0： 集中呼入咨询
	 */
	public static final Long CUST_POSTIL_TYPE_CALLIN = new Long(0);
	/**
	 * 跟进记录-跟进类型，1： 网单确认
	 */
	public static final Long CUST_POSTIL_TYPE_CALLOUT = new Long(1);
	/**
	 * 跟进记录-跟进类型，2： CSC跟进
	 */
	public static final Long CUST_POSTIL_TYPE_CSC = new Long(2);

	public static final Long CUST_POSTIL_TYPE_CSC_INPUT = new Long(3);

	public static final String CUST_POSTIL_TYPE_CALLIN_TEXT = "集中呼入咨询";
	public static final String CUST_POSTIL_TYPE_CALLOUT_TEXT = "网单确认";
	public static final String CUST_POSTIL_TYPE_CSC_TEXT = "CSC跟进";
	public static final String CUST_POSTIL_TYPE_CSC_INPUT_TEXT = "多渠道客户资料录入跟进";
	public static final String CUST_POSTIL_TYPE_MSG_TEXT = "留言确认";
	/**
	 * 跟进记录-跟进类型，3： 留言确认
	 */
	public static final Long CUST_POSTIL_TYPE_MSG = Long.valueOf(4);

	/**
	 * 跟进类型取值与显示文字的映射表
	 */
	public static final Map<Long, String> POSTIL_TYPE_TEXT_MAP = new HashMap<Long, String>();
	static {
		POSTIL_TYPE_TEXT_MAP.put(CUST_POSTIL_TYPE_CALLIN, CUST_POSTIL_TYPE_CALLIN_TEXT);
		POSTIL_TYPE_TEXT_MAP.put(CUST_POSTIL_TYPE_CALLOUT, CUST_POSTIL_TYPE_CALLOUT_TEXT);
		POSTIL_TYPE_TEXT_MAP.put(CUST_POSTIL_TYPE_CSC, CUST_POSTIL_TYPE_CSC_TEXT);
		POSTIL_TYPE_TEXT_MAP.put(CUST_POSTIL_TYPE_CSC_INPUT, CUST_POSTIL_TYPE_CSC_INPUT_TEXT);
		POSTIL_TYPE_TEXT_MAP.put(CUST_POSTIL_TYPE_MSG, CUST_POSTIL_TYPE_MSG_TEXT);
	}

	// 呼出二期 add by 陆庆润
	/**
	 * 自动审核类型-MAP
	 */
	public static final Map<Short, String> AUTO_AUDIT_TYPE_MAP = new HashMap<Short, String>();
	static {
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.PASS.getValue(), "通过");
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.OPPUGN.getValue(), "质疑");
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.OLD_TO_NEW.getValue(), "老户新开");
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.ONE_TO_MANY.getValue(), "一户多开");
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.SEASON_FILE.getValue(), "季节性备案");
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.CSC_EXIST.getValue(), "CSC下发疑似重复");
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.COMPANY_NAME_OPPUGN.getValue(), "公司名质疑");
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.EXIST.getValue(), "重复");
		// AUTO_AUDIT_TYPE_MAP.put(AbstractCustomer.AUTO_AUDIT_TYPE_NEXTSTEP,
		// "下一步");
	}
	/**
	 * 自动审核类型-FE接口形式
	 */
	public static final List<Map<String, Object>> AUTO_AUDIT_TYPE_LIST = new ArrayList<Map<String, Object>>();
	static {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.ID, (short) -1);
		map.put(Constant.NAME, "全部");
		AUTO_AUDIT_TYPE_LIST.add(map);
		Iterator<Short> it = AUTO_AUDIT_TYPE_MAP.keySet().iterator();
		while (it.hasNext()) {
			Short type = it.next();
			if (type.equals(AutoAuditType.EXIST.getValue())) {
				continue;
			}
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put(Constant.ID, type);
			temp.put(Constant.NAME, AUTO_AUDIT_TYPE_MAP.get(type));
			AUTO_AUDIT_TYPE_LIST.add(temp);
		}
	}

	/** add by ljq */
	/**
	 * 非核心词 - 数字 1
	 */
	public static final Long CORE_WORD_RULE_TYPE_NUMBER = 1L;
	/**
	 * 非核心词 - 字母 2
	 */
	public static final Long CORE_WORD_RULE_TYPE_CHARACTER = 2L;
	/**
	 * 非核心词 - 特定词 3
	 */
	public static final Long CORE_WORD_RULE_TYPE_WORD = 3L;
	/**
	 * 非核心词显示 - 数字 1
	 */
	public static final String CORE_WORD_RULE_TYPE_NUMBER_NAME = "数字";
	/**
	 * 非核心词显示 - 字母 2
	 */
	public static final String CORE_WORD_RULE_TYPE_CHARACTER_NAME = "字母";
	/**
	 * 非核心词显示 - 特定词 3
	 */
	public static final String CORE_WORD_RULE_TYPE_WORD_NAME = "特定词";

	/**
	 * 非核心词正则表达式内容 - 数字 [0-9]
	 */
	public static final String CORE_WORD_RULE_TYPE_NUMBER_VALUE = "[0-9]";
	/**
	 * 非核心词正则表达式内容 - 字母 [a-zA-Z]
	 */
	public static final String CORE_WORD_RULE_TYPE_CHARACTER_VALUE = "[a-zA-Z]";
	/** add by ljq end */
}
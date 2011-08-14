package com.baidu.rigel.unique.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 常量定义
 * 
 * @author Anders Zhu
 */
public class Constant {

	/**
	 * 客户编号
	 */
	public static final String CUST_ID = "custId";
	/**
	 * 客户名称
	 */
	public static final String CUST_NAME = "custName";
	/**
	 * 数据来源
	 */
	public static final String CUST_SOURCE = "custSource";

	public static final String AUTO_AUDIT_INFO = "autoAuditInfo";
	/**
	 * 连字符（-）
	 */
	public static final String HYPHEN = "-";
	/**
	 * 百分号、SQL通配符（%）
	 */
	public static final String PERCENT_SIGN = "%";
	/**
	 * 百分号转义字符（\\\\%）
	 */
	public static final String PERCENT_SIGN_ESCAPE = "\\\\%";
	/**
	 * 下划线、SQL通配符（_）
	 */
	public static final String UNDERLINE = "_";
	/**
	 * 下划线转义字符（\\\\_）
	 */
	public static final String UNDERLINE_ESCAPE = "\\\\_";
	/**
	 * 点（.）
	 */
	public static final String DOT = ".";
	/**
	 * 空格（“ ”）
	 */
	public static final String BLANK = " ";

	/**
	 * 用于过滤个人称谓
	 */
	public static final String TITLE = "经理,行长,副科长,老板,秘书,某特助,主任,文书,将军,总经理,分析师,财务,总裁,大师,网管,老师,技师,司长,小姐,研究员,某工,校长,教练,技术员,组长,店长,会计师,先生,培训师,代理,副总,总监,教授,部长,培训师,副总裁,司长,助理,会长,总裁助理,科长,团长,同学,协会长,司仪,医生,社长,大夫,馆长,书记,局长,政委,律师,指导员,师傅,工程师";

	/**
	 * 盘古客户开始编号
	 */
	public static final int PANGU_START_CUST_ID = 10000000;
	// TODO Anders Zhu : 什么意思
	public static final String PANGU_STAT1_CONTRACT_07 = "07";

	/**
	 * 验证内容（URL，电话号码和客户名称）
	 */
	public enum ValidType {
		/**
		 * URL
		 */
		URL,
		/**
		 * 电话号码
		 */
		PHONE,
		/**
		 * 客户名称
		 */
		CUSTNAME,
		/**
		 * 主域
		 */
		DOMAIN
	};

	public static final int TINYSE_QUERY_LIMIT = 10;

	public static final short RETURN_MAX_SIZE = 10;

	// 已经开户状态，一级状态为5
	// TODO Anders Zhu ： 考虑是否要重构
	public static final int SALE_SIGN = 5;

	// 呼出二期 add by 陆庆润
	/**
	 * 自动审核类型-MAP
	 */
	public static final Map<Short, String> AUTO_AUDIT_TYPE_MAP = new HashMap<Short, String>();
	static {
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.PASS.getValue(), AutoAuditType.PASS.getLabel());
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.OPPUGN.getValue(), AutoAuditType.OPPUGN.getLabel());
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.OLD_TO_NEW.getValue(), AutoAuditType.OLD_TO_NEW.getLabel());
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.ONE_TO_MANY.getValue(), AutoAuditType.ONE_TO_MANY.getLabel());
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.EXIST.getValue(), AutoAuditType.EXIST.getLabel());
		// AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.NEXT_STEP.getValue(), AutoAuditType.NEXT_STEP.getLabel());
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.SEASON_FILE.getValue(), AutoAuditType.SEASON_FILE.getLabel());
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.CSC_EXIST.getValue(), AutoAuditType.CSC_EXIST.getLabel());
		AUTO_AUDIT_TYPE_MAP.put(AutoAuditType.COMPANY_NAME_OPPUGN.getValue(), AutoAuditType.COMPANY_NAME_OPPUGN.getLabel());
	}

	public static final Map<Integer, String> CUST_TYPE_MAP = new HashMap<Integer, String>();
	static {
		CUST_TYPE_MAP.put(CustType.GENERAL_ENTERPRISE.getValue(), CustType.GENERAL_ENTERPRISE.getLabel());
		CUST_TYPE_MAP.put(CustType.SPECIAL_ENTERPRISE.getValue(), CustType.SPECIAL_ENTERPRISE.getLabel());
		CUST_TYPE_MAP.put(CustType.PERSONAL_CUSTOMER.getValue(), CustType.PERSONAL_CUSTOMER.getLabel());
		CUST_TYPE_MAP.put(CustType.ADVERTISING_AGENCY.getValue(), CustType.ADVERTISING_AGENCY.getLabel());
	}

	// TODO Anders Zhu ：考虑是否用枚举代替
	/* 字典表group id 定义 */
	/** 字典表：一级状态 组id */
	public static final String GROUP_ID_CUST_STAT_1 = "1";
	/** 字典表：二级状态 组id */
	public static final String GROUP_ID_CUST_STAT_2 = "2";
	/** 字典表：资料来源 组id */
	public static final String GROUP_ID_CUST_INPUT_TYPE = "3";
	/** 字典表：入库方式 组id */
	public static final String GROUP_ID_CUST_PRIORITY = "4";
	/** 字典表：审核类型 组id */
	public static final String GROUP_ID_AUDIT_CLEANOUT = "5";
	/** 字典表：审核清洗分配方式 组id */
	public static final String GROUP_ID_AUDIT_ASSIGN_METHOD = "6";
	/** 字典表：跟进分配方式 组id */
	public static final String GROUP_ID_FOLLOW_ASSIGN_METHOD = "7";
	/** 字典表：自动审核类型 组id */
	public static final String GROUP_ID_AUTO_AUDIT_TYPE = "8";
	/** 字典表：客户类型 组id */
	public static final String GROUP_ID_CUST_TYPE = "9";
	/** 字典表：代理商废弃原因 组id */
	public static final String GROUP_ID_AGENT_ABAND_REASON = "10";
	/** 字典表：网单操作类型 组id */
	public static final String GROUP_ID_NET_ORDER_OPERATOR_TYPE = "11";

	// TODO Anders Zhu ：考虑是否用枚举代替
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

	// TODO Anders Zhu :以下常量需要重构
	/**
	 * 黑名单标记 0:黑名单
	 */
	public static final Short BLACK_FLAG_Y = 0;
	/**
	 * 黑名单标记 1:正常
	 */
	public static final Short BLACK_FLAG_N = 1;
	/**
	 * "null"
	 */
	public static final String NULL_STR = "null";
	/** 每页数据条数 */
	public static final int ROW_COUNT_PER_PAGE = 80;
	/** 每页数据条数 */
	public static final int FIRST_PAGE_NUM = 1;

	/** 页面选择框有全选的时候，页面名称“全选” */
	public static final String SELECT_ALL_NAME = "全部";
	/**
	 * 前台需要传递给FE的值,提交值
	 */
	public static final String ID = "value";
	/**
	 * 前台需要传递给FE的值,显示值
	 */
	public static final String NAME = "text";
	/**
	 * 请选择默认值
	 */
	public static final Long PLEASE_SELECT_VALUE = -1L;
	/** 页面选择框有全选的时候，全选对应的value为-1 */
	public static final short SELECT_ALL = -1;

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
	 * 前台需要传递给FE的值,是否被选中
	 */
	public static final String SELECT = "isChecked";
}

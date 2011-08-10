package com.baidu.rigel.unique.common;

import java.util.HashMap;
import java.util.Map;

public class CalloutConstant extends Constant {
	/**
	 * 存放在HttpSession中的废弃客户资料列表ID,存放的数据内容为List<Long>
	 */
	public static final String SESSION_KEY_ABAND_CUST_ID_LIST = "session_key_aband_cust_id_list";
	/**
	 * 存放在HttpSession中的需要审核、清洗客户资料列表ID,存放的数据内容为List<Long>
	 */
	public static final String SESSION_KEY_AUDIT_CUST_ID_LIST = "session_key_audit_cust_id_list";
	/**
	 * 存放在HttpSession中的跟进客户资料列表ID,存放的数据内容为List<Long>
	 */
	public static final String SESSION_KEY_FOLLOW_CUST_ID_LIST = "session_key_follow_cust_id_list";
	/**
	 * 存放在HttpSession中的修改==更紧客户资料列表ID,存放的数据内容为List<Long>
	 */
	public static final String SESSION_KEY_UPDATE_CUST_ID_LIST = SESSION_KEY_FOLLOW_CUST_ID_LIST;
	/**
	 * 支持模块存放在Memcache中需要修改的客户资料ID,呼出模块中需要获取,存放的数据内容为List<Long>
	 */
	public static final String SESSION_KEY_HT_SUPPORT_UPDATE_CUST_ID_LIST = "session_key_ht_support_update_cust_id_list";
	/**
	 * 存放在HttpSession中的删除客户联系人ID,存放的数据内容为List<Long>
	 */
	public static final String SESSION_KEY_DELETE_CONTACT_ID_LIST = "session_key_delete_contact_id_list";
	/**
	 * 存放在HttpSession中的删除客户收件人ID,存放的数据内容为List<Long>
	 */
	public static final String SESSION_KEY_DELETE_RECIP_ID_LIST = "session_key_delete_recip_id_list";
	/**
	 * 存放在HttpSession中的查询条件ID,存放的数据内容为Long<Long>
	 */
	public static final String SESSION_KEY_QUERY_ID = "session_key_query_id";
	/******************************** chen long add *************************/
	public static final String SESSION_KEY_REJECTAUDIT_LIST = "session_key_rejectaudit_list";
	/**
	 * 使用基于MEMCACHED的数据共享，key必须使用以下Prefix
	 */
	public static final String MEMCACHED_GLOBAL_KEY_PREFIX = "callout_global_key_";
	/**
	 * 存放系统中的标签与废弃规则,类型为Map<long discardRuleID,DiscardRule>
	 */
	public static final String GLOBAL_KEY_DISCARD_RULE = MEMCACHED_GLOBAL_KEY_PREFIX + "discard_rule_map";

	/**
	 * 存放系统中的标签与废弃规则,类型为Map<long userID,HintRetainReply>
	 */
	public static final String GLOBAL_KEY_HINTRETAINREPLY_MAP = MEMCACHED_GLOBAL_KEY_PREFIX + "hint_retain_reply_map";

	public static final int DEFAULT_HINT_RETAIN_RETAINNUM = 30;

	public static final int DEFAULT_HINT_RETAIN_RETAINDAYS = 2;

	/**
	 * 存放系统中的销售保有数与保有时限,类型为HintRetainReply
	 */
	public static final String GLOBAL_KEY_HINTRETAINREPLY = MEMCACHED_GLOBAL_KEY_PREFIX + "hint_retain_reply";

	/******************************** chen long end *************************/
	/******************** add by he_jian 2009-06-15 *********************/
	/**
	 * 存放在HttpSession中的查看客户资料列表ID,存放的数据内容为List<Long>
	 */
	public static final String SESSION_KEY_VIEW_NETORDER_CUST_ID_LIST_FOR_CALLIN = "callin_session_key_view_netorder_cust_id_list";
	public static final String SESSION_KEY_VIEW_CALLIN_CUST_ID_LIST_FOR_CALLIN = "callin_session_key_view_callin_cust_id_list";
	public static final String SESSION_KEY_VIEW_CALLLIST_CUST_ID_LIST_FOR_CALLIN = "callin_session_key_view_calllist_cust_id_list";
	public static final String SESSION_KEY_VIEW_STAFF_NETORDER_CUST_ID_LIST_FOR_CALLIN = "callin_session_key_view_staff_netorder_cust_id_list";
	public static final String SESSION_KEY_VIEW_STAFF_CALLIN_CUST_ID_LIST_FOR_CALLIN = "callin_session_key_view_staff_callin_cust_id_list";
	/******************** add by he_jian END 2009-06-15 *********************/
	/** 审核清洗分配：0 只审核 */
	public static final short CUST_AUDIT_CLEAN_0 = 0;
	/** 审核清洗分配：1 只清洗 */
	public static final short CUST_AUDIT_CLEAN_1 = 1;
	/** 审核清洗分配：2 审核需清洗 */
	public static final short CUST_AUDIT_CLEAN_2 = 2;
	/** 审核清洗分配：3 不审核不清洗 */
	public static final short CUST_AUDIT_CLEAN_3 = 3;
	/** 审核清洗分配：4 跟进修改审核 陈龙添加 */
	public static final short CUST_AUDIT_CLEAN_10 = 10;

	/** 审核清洗：0 需审核 */
	public static final short CUST_NEED_AUDIT = 0;
	/** 审核清洗：1 需清洗 */
	public static final short CUST_NEED_CLEAN = 1;

	/** 分配任务类型/分配方式：0 审核清洗 */
	public static final short ASSIGN_TASK_0 = 0;
	/** 分配任务类型/分配方式：1 分配 */
	public static final short ASSIGN_TASK_1 = 1;
	/**** 跟进中修改类型 ***/
	public static final short ASSIGN_TASK_2 = 2;
	/**
	 * 电商规则
	 */
	public static final int ASSIGN_RULE_TYPE_EC = 3;
	/**
	 * 申请废弃自动分配规则
	 */
	public static final short ASSIGN_TASK_4 = 4;

	/** 分配方式：0 随机全部 */
	public static final short ASSIGN_TYPE_0 = 0;
	/** 分配方式：1 随机部分 */
	public static final short ASSIGN_TYPE_1 = 1;
	/** 分配方式：2 优先分配 */
	public static final short ASSIGN_TYPE_2 = 2;
	/** 分配方式：3 分配到池 */
	public static final short ASSIGN_TYPE_3 = 3;

	/** 分配任务状态：0 等待运行 */
	public static final short ASSIGN_TASK_STATUS_0 = 0;
	/** 分配任务状态：1 运行中 */
	public static final short ASSIGN_TASK_STATUS_1 = 1;

	/** 查询数据结果集id在session中的key */
	public static final String SCH_RESULT_SESSION_KEY = "SCH_RESULT_SESSION_KEY";

	/** 查询的页面显示数据结果集id在session中的key */
	public static final String SHOW_RESULT_SESSION_KEY = "SHOW_RESULT_SESSION_KEY";

	/** 派单ID列表在SESSION中的KEY，用于进行权限验证 */
	public static final String SEND_ORDER_HANDLE_SEND_ORDER_ID_LIST_SESSION_KEY = "SEND_ORDER_HANDLE_LIST_SESSION_KEY";

	/** 客户资料列表查询 显示方式--全部 */
	public static final short SHOW_STAT_ALL = -1;
	/** 客户资料列表查询 显示方式--待确认 */
	public static final short SHOW_TOBE_SURE = 0;
	/** 客户资料列表查询 显示方式--确认中 */
	public static final short SHOW_BEING_SURE = 1;
	/** 客户资料列表查询 显示方式--已废弃 */
	public static final short SHOW_ABANDON = 2;
	/** 派单类型--上门派单--值 */
	public static final short SEND_ORDER_TYPE_1 = 1;
	/** 派单类型--上门派单--名字 */
	public static final String SEND_ORDER_TYPE_1_NAME = "上门辅助";
	/** 派单状态--新提交--值 */
	public static final short SEND_ORDER_STATUS_NEW_SUBMIT = 0;
	/** 派单状态--新提交--名字 */
	public static final String SEND_ORDER_STATUS_NEW_SUBMIT_NAME = "新提交";
	/** 派单状态--待处理--值 */
	public static final short SEND_ORDER_STATUS_WILL_HANDLE = 1;
	/** 派单状态--待处理--名字 */
	public static final String SEND_ORDER_STATUS_WILL_HANDLE_NAME = "待处理";
	/** 派单状态--处理中--值 */
	public static final short SEND_ORDER_STATUS_HANDLING = 2;
	/** 派单状态--处理中--名字 */
	public static final String SEND_ORDER_STATUS_HANDLING_NAME = "处理中";
	/** 派单状态--已完成--值 */
	public static final short SEND_ORDER_STATUS_DONE = 3;
	/** 派单状态--已完成--名字 */
	public static final String SEND_ORDER_STATUS_DONE_NAME = "已完成";
	/** 派单状态--已驳回--值 */
	public static final short SEND_ORDER_STATUS_REJECT = 4;
	/** 派单状态--已驳回--名字 */
	public static final String SEND_ORDER_STATUS_REJECT_NAME = "已驳回";
	/** 派单状态--未定义 */
	public static final String SEND_ORDER_STATUS_UNDEFINED = "-";

	public static final Long SEND_ORDER_DEFAULT_CUR_PAGE = 0L;

	public static final Long SEND_ORDER_DEFAULT_PAGE_SIZE = 80L;

	// 下次跟进时间与当前日期差值为多少的时候飘红
	public static final int NEXT_FOLLOW_REMIND = 0;
	// 离跟进保有期限到期多久时候飘红
	public static final int RETAIN_REMIND = 3;

	public static final Map<Short, String> SEND_ORDER_STATUS_MAP;;
	static {
		SEND_ORDER_STATUS_MAP = new HashMap<Short, String>();
		SEND_ORDER_STATUS_MAP.put(Short.valueOf(SEND_ORDER_STATUS_NEW_SUBMIT), SEND_ORDER_STATUS_NEW_SUBMIT_NAME);
		SEND_ORDER_STATUS_MAP.put(Short.valueOf(SEND_ORDER_STATUS_WILL_HANDLE), SEND_ORDER_STATUS_WILL_HANDLE_NAME);
		SEND_ORDER_STATUS_MAP.put(Short.valueOf(SEND_ORDER_STATUS_HANDLING), SEND_ORDER_STATUS_HANDLING_NAME);
		SEND_ORDER_STATUS_MAP.put(Short.valueOf(SEND_ORDER_STATUS_DONE), SEND_ORDER_STATUS_DONE_NAME);
		SEND_ORDER_STATUS_MAP.put(Short.valueOf(SEND_ORDER_STATUS_REJECT), SEND_ORDER_STATUS_REJECT_NAME);
	}

	/** 派单sendorderlist在SESSION中的KEY */
	public static final String SEND_ORDER_FOR_ASSIGN_SEND_ORDER_LIST_SESSION_KEY = "SEND_ORDER_FOR_ASSIGN_SEND_ORDER_LIST_SESSION_KEY";
	/**
	 * 修改状态：清洗修改
	 */
	public static final Short UPDATE_FLAG_CLEANOUT = 0;
	/**
	 * 修改状态：跟进修改
	 */
	public static final Short UPDATE_FLAG_FOLLOW = 1;

	/**
	 * 修改状态：跟进修改
	 */
	public static final Short UPDATE_FLAG_CSC_REJECT = 2;

	/** 审核通过 */
	public static final short AUDIT_CLEANOUT_RECORD_PASS_PASSED = 0;
	/** 废弃 */
	public static final short AUDIT_CLEANOUT_RECORD_PASS_ABAND = 1;
	/** 管理废弃 */
	public static final short AUDIT_CLEANOUT_RECORD_PASS_MANAGE_ABAND = 2;

	// ===代理商管理
	/**
	 * 存放在HttpSession中的代理商的跟进客户资料列表ID,存放的数据内容为List<Long> housz callin
	 */
	public static final String SESSION_KEY_FOLLOW_CUST_ID_LIST_FOR_AGENT = "session_key_follow_cust_id_list_for_agent";
	public static final String SESSION_KEY_FOLLOW_CUST_ID_LIST_FOR_AGENT_OTHER_PART = "session_key_follow_cust_id_list_for_agent_other_part";
	public static final String SESSION_KEY_FOLLOW_CUST_ID_LIST_FOR_AGENT_2_SORT_TYPE = "FALSE";
	/**
	 * 存放在HttpSession中的代理商的查看客户资料列表ID,存放的数据内容为List<Long> housz callin
	 */
	public static final String SESSION_KEY_VIEW_CUST_ID_LIST_FOR_AGENT = "session_key_view_cust_id_list_for_agent";

	/** add by housz begin 代理商废弃原因 */
	public static final int AGENT_ABAND_DISSUCC_ORDER = 1;
	public static final String AGENT_ABAND_DISSUCC_ORDER_NAME = "不成单";
	public static final int AGENT_ABAND_INACCURATE_INFORMATION = 2;
	public static final String AGENT_ABAND_INACCURATE_INFORMATION_NAME = "资料不准确";
	public static final int AGENT_ABAND_NO_INTENTION = 3;
	public static final String AGENT_ABAND_NO_INTENTION_NAME = "无意向";
	public static final int AGENT_ABAND_OTHERS = 7;
	public static final String AGENT_ABAND_OTHERS_NAME = "其它";
	public static final Map<Integer, String> AGENT_ABAND_REASON = new HashMap<Integer, String>();
	static {
		AGENT_ABAND_REASON.put(AGENT_ABAND_DISSUCC_ORDER, AGENT_ABAND_DISSUCC_ORDER_NAME);
		AGENT_ABAND_REASON.put(AGENT_ABAND_INACCURATE_INFORMATION, AGENT_ABAND_INACCURATE_INFORMATION_NAME);
		AGENT_ABAND_REASON.put(AGENT_ABAND_NO_INTENTION, AGENT_ABAND_NO_INTENTION_NAME);
		AGENT_ABAND_REASON.put(AGENT_ABAND_OTHERS, AGENT_ABAND_OTHERS_NAME);
	}
	/** add by housz end */

	/**** 陈龙添加 *****/
	public static final Short FOLLOW_MODIFY_AUDIT_ASSIGN_TYPE = Short.valueOf("10");
	/**
	 * 申请废弃
	 */
	public static final Short QUIT_APPLY_AUDIT_ASSIGN_TYPE = Short.valueOf("11");
	/**
	 * 历史状态为跟进中修改，需要审核的记录
	 */
	public static final Short FOLLOW_MODIFY_TYPE_1010 = 1010;

	/**
	 * FollowMgr及Dao中的默认值 -1 add by ljq
	 */
	public static final int FOLLOW_DEFAULT_FROM_TYPR = -1;

	/**
	 * 特殊的跟进中的，指定到某个人的,未分配状态 chenlong add
	 */

	public static final long ESPECIAL_FOLLOW_STATE = 1011;
	/**
	 * 特殊的跟进中的，指定到某个人的,已分配状态 chenlong add
	 */
	public static final long ESPECIAL_FOLLOW_STATE_HAS = 1012;
	/**
	 * 电商申请驳回状态,电商专用
	 */
	public static final long EC_REJECT_FOLLOW_STATE = 1013;

	/**
	 * chenlong add
	 */
	/** 每页数据条数 */
	public static final int PAGE_NUM_15 = 15;

	/**
	 * 每页数据值 - 很大
	 */
	public static final int PAGE_NUM_AMOUNT = 10000;

	public static final String GLOBAL_MSG = "globalMsg";
	public static final Short CUST_WEB_SITE_TYPE_0 = 0;
	public static final Short CUST_WEB_SITE_TYPE_1 = 1;
	public static final Short CUST_WEB_SITE_TYPE_2 = 2;

	public static final Map<Short, String> CUST_WEB_SITE_TYPE_MAP = new HashMap<Short, String>();
	static {
		CUST_WEB_SITE_TYPE_MAP.put(CUST_WEB_SITE_TYPE_0, "已有网站");
		CUST_WEB_SITE_TYPE_MAP.put(CUST_WEB_SITE_TYPE_1, "暂无网站");
		CUST_WEB_SITE_TYPE_MAP.put(CUST_WEB_SITE_TYPE_2, "未知");
	}

	/** 部门树传入的参数 */
	public static final int DEPTTREE_CHILD = 1;
	// ===housz add begin
	// tb_user_quitcust_n系列表中的操作类型 1:申请废弃 2:置换 3:跟进超时 4：其它
	public static final int OP_TYPE_QUIT = 1;
	public static final int OP_TYPE_REPLACE = 2;
	public static final int OP_TYPE_FOLLOW_TIME_OUT = 3;
	// ===housz add end

	/**
	 * 客户资料查询结果集id在session中的key add by Junquan 2010.05.24
	 */
	public static final String CUSTID_RESULTALL_SESSION_KEY = "CUSTID_RESULTALL_SESSION_KEY";

	public static final int CUST_LIST_PAGE_NUMBER = 15;
}

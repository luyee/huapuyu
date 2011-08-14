package com.baidu.rigel.unique.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.baidu.rigel.platform.vo.Page;
import com.baidu.rigel.unique.bo.NoncoreWord;
import com.baidu.rigel.unique.common.BaseActionSupport;
import com.baidu.rigel.unique.common.CoreWordQueryInfo;
import com.baidu.rigel.unique.common.ResearchAware;
import com.baidu.rigel.unique.service.NoncoreWordService;
import com.baidu.rigel.unique.utils.Constant;

public class CoreWordSetUpAction extends BaseActionSupport implements ResearchAware {
	private static final Logger logger = Logger.getLogger(CoreWordSetUpAction.class);

	// 特定词最大长度
	private static final int CORE_WORD_MAX_LENGTH = 20;

	private String errorMessage;

	// 特定词数量
	private Long coreWordCnt;
	// 特定词
	private String coreWord;
	// 特定词 - 修改或添加
	private String coreWordNew;
	// 特定词Id
	private Long coreWordId;
	// 设定人
	private Long addUserId;
	// 设定人列表
	private List<Map<String, Object>> addUserList = new ArrayList<Map<String, Object>>();
	// 特定词列表
	private List<NoncoreWord> coreWordList = new ArrayList<NoncoreWord>();

	// 查询条件集合
	private CoreWordQueryInfo queryCon;
	// 查询特定词数量
	private Long queryTotalNum;
	private int pageSize;

	// Mgr
	private NoncoreWordService noncoreWordService;

	@Override
	public String execute() throws Exception {
		int getPageNum = Constant.ROW_COUNT_PER_PAGE;

		// 初始化分页数据
		if (page == null) {
			page = new Page();
		}
		if (page.getPage_size() == null || page.getPage_size() < 0) {
			page.setPage_size(Long.valueOf(getPageNum));
		}
		if (page.getCur_page_num() == null || page.getCur_page_num() <= 0) {
			page.setCur_page_num(1l);
		}
		if (queryCon == null) {
			queryCon = new CoreWordQueryInfo();
		}
		// 查询条件
		if (coreWord != null && !"".equals(coreWord.trim())) {
			queryCon.setCoreWord(coreWord.trim());
		}
		if (addUserId != null && addUserId != Constant.SELECT_ALL) {
			queryCon.setAddUserId(addUserId);
		}
		// 设定人列表
		addUserList = noncoreWordService.getCoreWordUserListForFE();

		// 总数量
		coreWordCnt = noncoreWordService.getCoreWordCntForView(null);
		if (coreWordCnt == null || coreWordCnt == 0) {
			coreWordCnt = Long.valueOf(0);
			return SUCCESS;
		}
		// 本次查询总条数
		queryTotalNum = noncoreWordService.getCoreWordCntForView(queryCon);

		// 分页处理
		page.setTotalRecNum(queryTotalNum);
		pageSize = (int) Math.ceil((double) queryTotalNum / getPageNum);

		if (page.getCur_page_num() <= 0) {
			page.setCur_page_num(0L);
		}
		if (page.getCur_page_num() > pageSize) {
			page.setCur_page_num(Long.valueOf(pageSize));
		}

		int getQueryFrom = (int) (page.getCur_page_num() - 1) * getPageNum;
		if (getQueryFrom < 0) {
			getQueryFrom = 0;
		}
		queryCon.setDataLimitFrom(Long.valueOf(getQueryFrom));
		queryCon.setDataLimitLength(Long.valueOf(getPageNum));
		// 获取列表
		coreWordList = noncoreWordService.getCoreWordListForView(queryCon);

		return SUCCESS;
	}

	// 添加特定词
	public String add() throws Exception {
		if (coreWordNew == null || "".equals(coreWordNew.trim())) {
			// addFieldError("coreWordSetUp", "特定词不能为空");
			this.errorMessage = "特定词不能为空";
			logger.error("特定词不能为空");
			return execute();
		}
		coreWordNew = coreWordNew.trim();
		// 长度
		if (coreWordNew.length() >= CORE_WORD_MAX_LENGTH) {
			// addFieldError("coreWordSetUp", "特定词长度过大");
			this.errorMessage = "特定词长度过大";
			logger.error("特定词长度过大");
			return execute();
		}
		// 查找是否存在
		NoncoreWord word = null;
		word = noncoreWordService.selectNoncoreWordByWord(coreWordNew);
		if (word != null) {
			// addFieldError("coreWordSetUp", "特定词已经存在");
			this.errorMessage = "特定词已经存在";
			logger.error("特定词已经存在");
			return execute();
		}

		// 当前时间
		Date now = new Date();
		// 当前登录者
		Long loginUserId = ucHelper.getUser().getUcid();
		word = new NoncoreWord();
		word.setWord(coreWordNew.trim());
		word.setCreatorId(loginUserId);
		word.setCreateTime(now);
		word.setUpdateId(loginUserId);
		word.setUpdateTime(now);
		boolean b = noncoreWordService.saveNoncoreWord(word);
		if (!b) {
			// addFieldError("coreWordSetUp", "保存特定词失败");
			this.errorMessage = "保存特定词失败";
			logger.error("保存特定词失败");
		}

		return execute();
	}

	// 删除特定词
	public String delete() throws Exception {
		if (coreWordId == null || coreWordId <= 0) {
			// addFieldError("coreWordSetUp", "特定词ID不能为空");
			this.errorMessage = "特定词ID不能为空";
			logger.error("特定词ID不能为空");
			return execute();
		}
		NoncoreWord word = null;
		word = noncoreWordService.findById(coreWordId);
		if (word == null) {
			// addFieldError("coreWordSetUp", "不存在该特定词");
			this.errorMessage = "不存在该特定词";
			logger.error("不存在该特定词");
			return execute();
		}
		// 当前时间
		Date now = new Date();
		// 当前登录者
		Long loginUserId = ucHelper.getUser().getUcid();
		word.setUpdateId(loginUserId);
		word.setUpdateTime(now);
		// 设置del_flag=1
		Short s = 1;
		word.setDelFlag(s);
		boolean b = noncoreWordService.saveNoncoreWord(word);
		if (!b) {
			// addFieldError("coreWordSetUp", "保存特定词失败");
			this.errorMessage = "保存特定词失败";
			logger.error("保存特定词失败");
		}

		return execute();
	}

	// 修改特定词
	public String modify() throws Exception {
		if (coreWordId == null || coreWordId <= 0) {
			// addFieldError("coreWordSetUp", "特定词ID不能为空");
			this.errorMessage = "特定词ID不能为空";
			logger.error("特定词ID不能为空");
			return execute();
		}
		if (coreWordNew == null || "".equals(coreWordNew.trim())) {
			// addFieldError("coreWordSetUp", "特定词不能为空");
			this.errorMessage = "特定词不能为空";
			logger.error("特定词不能为空");
			return execute();
		}
		coreWordNew = coreWordNew.trim();
		// 长度
		if (coreWordNew.length() >= CORE_WORD_MAX_LENGTH) {
			// addFieldError("coreWordSetUp", "特定词长度过大");
			this.errorMessage = "特定词长度过大";
			logger.error("特定词长度过大");
			return execute();
		}
		NoncoreWord word = null;
		word = noncoreWordService.findById(coreWordId);
		if (word == null) {
			// addFieldError("coreWordSetUp", "不存在该特定词");
			this.errorMessage = "不存在该特定词";
			logger.error("不存在该特定词");
			return execute();
		}

		// 查找新名字是否存在
		NoncoreWord wordnew = null;
		wordnew = noncoreWordService.selectNoncoreWordByWord(coreWordNew);
		if (wordnew != null) {
			// addFieldError("coreWordSetUp", "特定词已经存在");
			this.errorMessage = "特定词已经存在";
			logger.error("特定词已经存在");
			return execute();
		}

		// 当前时间
		Date now = new Date();
		// 当前登录者
		Long loginUserId = ucHelper.getUser().getUcid();
		word.setUpdateId(loginUserId);
		word.setUpdateTime(now);
		word.setWord(coreWordNew);
		boolean b = noncoreWordService.saveNoncoreWord(word);
		if (!b) {
			// addFieldError("coreWordSetUp", "保存特定词失败");
			this.errorMessage = "保存特定词失败";
			logger.error("保存特定词失败");
		}

		return execute();
	}

	/**
	 * 分页相关
	 */
	// 分页数据
	private Page page;
	// 重置查询条件用id
	private String queryId;
	// page name
	private final static String PAGE_NAME = "CORE_WORD_SETUP_ACTION";

	public String getPageName() {
		return PAGE_NAME;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String id) {
		queryId = id;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Long getCoreWordCnt() {
		return coreWordCnt;
	}

	public void setCoreWordCnt(Long coreWordCnt) {
		this.coreWordCnt = coreWordCnt;
	}

	public String getCoreWord() {
		return coreWord;
	}

	public void setCoreWord(String coreWord) {
		this.coreWord = coreWord;
	}

	public Long getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	public List<Map<String, Object>> getAddUserList() {
		return addUserList;
	}

	public void setAddUserList(List<Map<String, Object>> addUserList) {
		this.addUserList = addUserList;
	}

	public List<NoncoreWord> getCoreWordList() {
		return coreWordList;
	}

	public void setCoreWordList(List<NoncoreWord> coreWordList) {
		this.coreWordList = coreWordList;
	}

	public CoreWordQueryInfo getQueryCon() {
		return queryCon;
	}

	public void setQueryCon(CoreWordQueryInfo queryCon) {
		this.queryCon = queryCon;
	}

	public Long getCoreWordId() {
		return coreWordId;
	}

	public void setCoreWordId(Long coreWordId) {
		this.coreWordId = coreWordId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCoreWordNew() {
		return coreWordNew;
	}

	public void setCoreWordNew(String coreWordNew) {
		this.coreWordNew = coreWordNew;
	}

}

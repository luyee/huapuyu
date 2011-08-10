package com.baidu.rigel.unique.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.service.usercenter.bean.User;
import com.baidu.rigel.service.usercenter.service.UserMgr;
import com.baidu.rigel.unique.bo.NoncoreWord;
import com.baidu.rigel.unique.common.Constant;
import com.baidu.rigel.unique.common.CoreWordQueryInfo;
import com.baidu.rigel.unique.dao.NoncoreWordDao;
import com.baidu.rigel.unique.service.NoncoreWordService;
import com.baidu.rigel.unique.utils.Utils;

@Service("noncoreWordService")
public class NoncoreWordServiceImpl extends GenericSqlMapServiceImpl<NoncoreWord, Long> implements NoncoreWordService {
	@Resource(name = "noncoreWordDao")
	private NoncoreWordDao noncoreWordDao;
	@Resource(name = "userMgr")
	private UserMgr userMgr;

	@Override
	public SqlMapDao<NoncoreWord, Long> getDao() {
		return noncoreWordDao;
	}

	// TODO Anders Zhu ： 需要重构
	public List<Map<String, Object>> getCoreWordUserListForFE() {
		// 结果集
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> tmpMap;

		// 增加全部
		tmpMap = new HashMap<String, Object>();
		tmpMap.put(Constant.ID, Constant.SELECT_ALL);
		tmpMap.put(Constant.NAME, Constant.SELECT_ALL_NAME);
		result.add(tmpMap);

		// 增加设定人列表
		List<Long> userList = noncoreWordDao.selectDisCreatorId();
		Map<Long, User> userMap = null;
		if (userList != null && userList.size() > 0) {
			userMap = userMgr.getUserMap(userList);
		}
		if (userMap != null) {
			User u;
			for (Long id : userList) {
				tmpMap = new HashMap<String, Object>();
				tmpMap.put(Constant.ID, id);
				u = userMap.get(id);
				tmpMap.put(Constant.NAME, u != null ? u.getUcname() : "-");
				result.add(tmpMap);
			}
		}

		return result;
	}

	public List<Long> selectDisCreatorId() {
		return noncoreWordDao.selectDisCreatorId();
	}

	public List<NoncoreWord> getCoreWordListForView(CoreWordQueryInfo queryInfo) {
		List<NoncoreWord> result;
		if (queryInfo != null) {
			result = pageList(queryInfo.getAddUserId(), queryInfo.getCoreWord(), queryInfo.getDataLimitFrom().intValue(), queryInfo.getDataLimitLength().intValue());
		} else {
			result = new ArrayList<NoncoreWord>(0);
		}
		if (result != null && result.size() > 0) {
			// 获取用户id列表
			List<Long> userIds = new ArrayList<Long>();
			for (NoncoreWord cw : result) {
				userIds.add(cw.getCreatorId());
			}
			// 获取userid与name对应map
			Map<Long, User> userMap = null;
			if (userIds != null && userIds.size() > 0) {
				userMap = userMgr.getUserMap(userIds);
			}

			// 填充姓名
			for (NoncoreWord cw : result) {
				User u = userMap.get(cw.getCreatorId());
				if (u != null) {
					cw.setCreatorName(u.getUcname());
				}
			}
		}

		return result;
	}

	public Long getCoreWordCntForView(CoreWordQueryInfo queryInfo) {
		if (queryInfo != null) {
			return pageCount(queryInfo.getAddUserId(), queryInfo.getCoreWord());
		} else {
			return 0L;
		}
	}

	public Long pageCount(Long creatorId, String word) {
		if (StringUtils.isBlank(word))
			word = null;
		else
			word = Utils.addWildcard(Utils.escapeWildcard(word));

		return noncoreWordDao.pageCount(creatorId, word);
	}

	public List<NoncoreWord> pageList(Long creatorId, String word, int curPage, int pageSize) {
		if (StringUtils.isBlank(word))
			word = null;
		else
			word = Utils.addWildcard(Utils.escapeWildcard(word));

		if (Utils.isLessThanZero(curPage))
			curPage = com.baidu.rigel.unique.utils.Constant.ZERO;

		return noncoreWordDao.pageList(creatorId, word, curPage, pageSize);
	}

	public NoncoreWord selectNoncoreWordByWord(String word) {
		if (StringUtils.isBlank(word))
			return null;
		return noncoreWordDao.selectNoncoreWordByWord(word);
	}

	// TODO Anders Zhu :重构
	public boolean saveNoncoreWord(NoncoreWord noncoreWord) {
		try {
			saveOrUpdate(noncoreWord);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public NoncoreWord findById(Long id) {
		if (Utils.isNull(id))
			return null;
		return noncoreWordDao.findById(id);
	}
}
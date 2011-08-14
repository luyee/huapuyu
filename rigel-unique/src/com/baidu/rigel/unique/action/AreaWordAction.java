package com.baidu.rigel.unique.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baidu.rigel.platform.vo.Page;
import com.baidu.rigel.service.usercenter.bean.Position;
import com.baidu.rigel.service.usercenter.bean.User;
import com.baidu.rigel.unique.bo.AreaWord;
import com.baidu.rigel.unique.common.BaseActionSupport;
import com.baidu.rigel.unique.service.AreaWordService;
import com.baidu.rigel.unique.utils.Constant;

/**
 * 地域词设定
 * 
 * @author cm
 * @created 2010-9-28 上午10:01:54
 * @lastModified
 * @history
 */
public class AreaWordAction extends BaseActionSupport {

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	private AreaWordService areaWordService;

	public AreaWordService getAreaWordService() {
		return areaWordService;
	}

	public void setAreaWordService(AreaWordService areaWordService) {
		this.areaWordService = areaWordService;
	}

	public List<Map<String, Object>> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<Map<String, Object>> unitList) {
		this.unitList = unitList;
	}

	public Long getPosId() {
		return posId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public List<AreaWord> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<AreaWord> areaList) {
		this.areaList = areaList;
	}

	/**
	 * 分公司列表：
	 */
	private List<Map<String, Object>> unitList;

	/**
	 * 当前选择分公司：
	 */
	private Long posId;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 地域词：
	 */
	private String word;

	/**
	 * 设定人： 设定时间： 分公司名称： 地域词： id：
	 */
	private List<AreaWord> areaList;

	private void initPage() {
		if (this.page == null) {
			this.page = new Page();
		}
		if (this.page.getCur_page_num() == null) {
			this.page.setCur_page_num(Long.valueOf(Constant.FIRST_PAGE_NUM));
		}
		if (this.page.getPage_size() == null) {
			this.page.setPage_size(10L);
		}
	}

	/**
	 * 得到分页数据
	 * 
	 * @return
	 * @author cm
	 * @created 2010-9-28 上午09:52:29
	 * @lastModified
	 * @history
	 */
	public String listAreaWord() {
		initPage();
		Position p = this.ucHelper.getCurrentPos();
		Long posId = p.getPosid();
		// 获取所官辖的运营单位的岗位
		List positions = userMgr.getUnitPos(posId);
		// 初始化管辖公司列表
		this.unitList = new ArrayList();

		for (int i = 0; i < positions.size(); i++) {
			Position tmp = (Position) positions.get(i);
			Map tmpMap = new HashMap();
			String name = (tmp.getPosname() == null && tmp.getPosname().equals("")) ? "-" : tmp.getPosname();
			tmpMap.put("text", name);
			tmpMap.put("value", tmp.getPosid());
			this.unitList.add(tmpMap);
		}
		// TODO Anders Zhu : mock 以便后续代码运行
		// if (positions.size() > 1) {
		// posId = 0L;
		// } else if (positions.size() == 1) {
		// posId = ((Position) positions.get(0)).getPosid();
		// } else if (positions.size() == 0) {
		// throw new IllegalArgumentException("您没有运营单位的权限!");
		// }

		List<AreaWord> tempList = areaWordService.selectAllOrderByCWord();
		if (posId.longValue() != 0) {
			for (Iterator<AreaWord> it = tempList.iterator(); it.hasNext();) {
				AreaWord word = it.next();
				if (!posId.equals(word.getPosId())) {
					it.remove();
				}
			}
		}
		areaList = tempList.subList((int) ((page.getCurPageNum() - 1) * page.getPageSize()), (int) ((page.getCurPageNum()) * page.getPageSize() > tempList.size() ? tempList.size() : (page.getCurPageNum()) * page.getPageSize()));
		page.setTotalRecNum((long) tempList.size());
		Set<Long> posids = new HashSet<Long>();
		for (AreaWord word : areaList) {
			if (word.getPosId() != null) {
				posids.add(word.getPosId());
			}
		}
		Set<Long> userIds = new HashSet<Long>();
		for (AreaWord word : areaList) {
			if (word.getAddeduser() != 0) {
				userIds.add(word.getAddeduser());
			}
		}
		// 部门名称
		Map<Long, Position> pos = userMgr.getPositionMapByIds(new ArrayList<Long>(posids));
		// 设定人
		Map<Long, User> users = userMgr.getUserMap(new ArrayList<Long>(userIds));
		for (AreaWord areaWord : areaList) {
			areaWord.setPosName(pos.get(areaWord.getPosId()) == null ? "-" : pos.get(areaWord.getPosId()).getPosname());
			areaWord.setAddedUserName(users.get(areaWord.getAddeduser()) == null ? "-" : users.get(areaWord.getAddeduser()).getUcname());
		}

		return SUCCESS;
	}

	public String addAreaWord() {
		AreaWord areaWord = new AreaWord();
		areaWord.setAddedtime(new Date());
		areaWord.setAddeduser(ucHelper.getUser().getUcid());
		areaWord.setPosId(posId);
		areaWord.setcWord(word);
		areaWordService.saveOrUpdate(areaWord);
		return SUCCESS;
	}

	public String deleteAreaWord() {
		areaWordService.deleteAreaWord(id);
		return SUCCESS;
	}

	public String updateAreaWord() {
		AreaWord areaWord = new AreaWord();
		areaWord.setAddedtime(new Date());
		areaWord.setAddeduser(ucHelper.getUser().getUcid());
		areaWord.setPosId(posId);
		areaWord.setcWord(word);
		areaWord.setcId(id);
		areaWordService.saveOrUpdate(areaWord);
		return SUCCESS;
	}

}

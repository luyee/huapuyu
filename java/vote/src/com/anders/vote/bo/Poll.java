package com.anders.vote.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * 投票BO
 * 
 * @author Anders Zhu
 * 
 */
public class Poll extends BaseBO<Long> {

	private static final long serialVersionUID = -4664518583879957397L;

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 投票选项集合
	 */
	private List<PollOption> pollOptions = new ArrayList<PollOption>();

	// getter and setter

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<PollOption> getPollOptions() {
		return pollOptions;
	}

	public void setPollOptions(List<PollOption> pollOptions) {
		this.pollOptions = pollOptions;
	}

}

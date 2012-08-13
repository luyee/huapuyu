package com.anders.vote.bo;

import java.util.ArrayList;
import java.util.List;

public class Poll extends BaseBO {

	private static final long serialVersionUID = -4664518583879957397L;

	private String title;

	private String remark;

	private List<PollItem> pollItems = new ArrayList<PollItem>();

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

	public List<PollItem> getPollItems() {
		return pollItems;
	}

	public void setPollItems(List<PollItem> pollItems) {
		this.pollItems = pollItems;
	}

}

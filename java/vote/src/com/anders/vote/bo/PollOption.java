package com.anders.vote.bo;

/**
 * 投票选项BO
 * 
 * @author Anders Zhu
 * 
 */
public class PollOption extends BaseBO<Long> {

	private static final long serialVersionUID = 628851298167014104L;

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 投票
	 */
	private Poll poll;

	// getter and setter

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

}

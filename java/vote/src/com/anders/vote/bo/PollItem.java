package com.anders.vote.bo;

public class PollItem extends BaseBO {

	private static final long serialVersionUID = 628851298167014104L;

	private String title;
	
	private Poll poll;

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

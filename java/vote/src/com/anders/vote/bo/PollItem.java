package com.anders.vote.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_poll_item")
public class PollItem implements Serializable {

	private static final long serialVersionUID = 628851298167014104L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String title;
	
	@Column(nullable = false)
	private Boolean enable = true;

	private Poll poll;
}

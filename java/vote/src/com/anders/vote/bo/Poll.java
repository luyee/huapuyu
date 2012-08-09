package com.anders.vote.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "tb_poll")
public class Poll implements Serializable {
	
	private static final long serialVersionUID = -4664518583879957397L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String title;
	
	@Column(nullable = false, length = 100)
	private String remark;
	
	@Column(name="create_time",nullable = false)
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(nullable = false)
	private Boolean enable = true;
	
	private List<PollItem> pollItems = new ArrayList<PollItem>();
}

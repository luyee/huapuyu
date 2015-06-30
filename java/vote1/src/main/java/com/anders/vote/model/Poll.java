package com.anders.vote.model;

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

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

@Entity
@Table(name = "tb_poll")
@Searchable
@XmlRootElement
public class Poll implements Serializable {
	
	private static final long serialVersionUID = -4664518583879957397L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SearchableId
	private Long id;
	
	@Column(nullable = false, length = 50)
    @SearchableProperty
	private String title;
	
	@Column(nullable = false, length = 100)
    @SearchableProperty
	private String remark;
	
	@Column(name="create_time",nullable = false)
    @SearchableProperty
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(nullable = false)
	@XmlTransient
	private Boolean enable = true;
	
	private List<PollItem> pollItems = new ArrayList<PollItem>();
}

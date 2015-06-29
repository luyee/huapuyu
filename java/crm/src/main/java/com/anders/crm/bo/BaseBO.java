package com.anders.crm.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.search.annotations.DocumentId;

/**
 * Base Business Object
 * 
 * @author Anders Zhu
 * 
 */
@Setter
@Getter
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseBO implements Serializable {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@DocumentId
	private Long id;
	/**
	 * 启用（true：启用；false：禁用）
	 */
	@Column(nullable = false)
	private Boolean enabled = true;
	/**
	 * 新增人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "add_user_id", nullable = true, updatable = false)
	private User addUser;
	/**
	 * 新增时间
	 */
	@Column(name = "add_time", nullable = false, updatable = false)
	private Date addTime = new Date();
	/**
	 * 更新人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "update_user_id")
	private User updateUser;
	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;
	/**
	 * 乐观锁
	 */
	@Version
	private Integer version = 0;
}

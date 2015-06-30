package com.anders.hibernate.model.maifang;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 区域配置
 * 
 * @author Anders
 * 
 */
@Entity
@Table(name = "cfg_area")
public class Area implements Serializable {
	private static final long serialVersionUID = -3838557037798985561L;

	public enum AreaType {
		/**
		 * 0：省、自治区、直辖市
		 */
		PROVINCE,
		/**
		 * 1：城市
		 */
		CITY,
		/**
		 * 2：区、县、市
		 */
		DISTRICT
	}

	/**
	 * 编号（主键）
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 名称
	 */
	@Column(nullable = false, length = 50)
	private String title;
	/**
	 * 类型（0：省、自治区、直辖市；1：城市；2：区、县、市）
	 */
	@Enumerated
	@Column(nullable = false)
	private AreaType type;
	/**
	 * 上级区域
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Area parentArea;
	/**
	 * 下级区域
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "parentArea")
	@OrderBy("id")
	private List<Area> sonAreas = new ArrayList<Area>(0);
	/**
	 * 启用符（1：启用；0：停用）
	 */
	@Column(nullable = false)
	private Boolean enable = true;
}

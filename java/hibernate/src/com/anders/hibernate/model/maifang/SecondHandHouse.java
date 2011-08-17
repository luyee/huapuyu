package com.anders.hibernate.model.maifang;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 二手房
 * 
 * @author Anders
 * 
 */
@Entity
@Table(name = "tb_second_hand_house")
public class SecondHandHouse implements Serializable {
	private static final long serialVersionUID = 4779933743319140372L;

	/**
	 * 编号（主键）
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 售价
	 */
	@Column(nullable = false, scale = 2)
	private BigDecimal price;
	/**
	 * 建筑面积
	 */
	@Column(nullable = false, scale = 2)
	private BigDecimal buildingArea;
	/**
	 * 使用面积
	 */
	@Column(nullable = false, scale = 2)
	private BigDecimal usableArea;
	/**
	 * 产权性质
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "property_right_id")
	private Data propertyRight;
	/**
	 * 住宅类别
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "residence_type_id")
	private Data residenceType;
	/**
	 * 建筑类别
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "construction_type_id")
	private Data constructionType;
	/**
	 * 房屋结构
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "building_structure_id")
	private Data buildingStructure;
	/**
	 * 看房时间
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_time_id")
	private Data visitTime;
	/**
	 * 房屋
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "house_id")
	// @OneToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "house_id")
	private House house;
}

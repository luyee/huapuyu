package com.bamboo.maifang.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 二手房
 * 
 * @author Anders
 * 
 */
@Entity
@Table(name = "tb_second_hand_house")
public class SecondHandHouse implements Serializable {

    private static final long serialVersionUID = -8981569640271697506L;
    
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

    /**
     * 有效期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiration;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getBuildingArea() {
		return buildingArea;
	}

	public void setBuildingArea(BigDecimal buildingArea) {
		this.buildingArea = buildingArea;
	}

	public BigDecimal getUsableArea() {
		return usableArea;
	}

	public void setUsableArea(BigDecimal usableArea) {
		this.usableArea = usableArea;
	}

	public Data getPropertyRight() {
		return propertyRight;
	}

	public void setPropertyRight(Data propertyRight) {
		this.propertyRight = propertyRight;
	}

	public Data getResidenceType() {
		return residenceType;
	}

	public void setResidenceType(Data residenceType) {
		this.residenceType = residenceType;
	}

	public Data getConstructionType() {
		return constructionType;
	}

	public void setConstructionType(Data constructionType) {
		this.constructionType = constructionType;
	}

	public Data getBuildingStructure() {
		return buildingStructure;
	}

	public void setBuildingStructure(Data buildingStructure) {
		this.buildingStructure = buildingStructure;
	}

	public Data getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Data visitTime) {
		this.visitTime = visitTime;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
    
    
}

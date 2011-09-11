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
 * 出租房
 * 
 * @author Anders
 * 
 */
@Entity
@Table(name = "tb_rent_house")
public class RentHouse implements Serializable {

    private static final long serialVersionUID = 780341474894787956L;
    /**
     * 编号（主键）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 租金
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * 支付方式
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paid_type_id", nullable = false)
    private Data paidType;

    /**
     * 出租面积
     */
    @Column(nullable = false)
    private BigDecimal area;

    /**
     * 租房方式
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_type_id", nullable = false)
    private Data rentType;

    /**
     * 合租要求
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_demand_id")
    private Data rentDemand;

    /**
     * 入住时间
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_in_time_id")
    private Data checkInTime;

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

	public Data getPaidType() {
		return paidType;
	}

	public void setPaidType(Data paidType) {
		this.paidType = paidType;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public Data getRentType() {
		return rentType;
	}

	public void setRentType(Data rentType) {
		this.rentType = rentType;
	}

	public Data getRentDemand() {
		return rentDemand;
	}

	public void setRentDemand(Data rentDemand) {
		this.rentDemand = rentDemand;
	}

	public Data getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Data checkInTime) {
		this.checkInTime = checkInTime;
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


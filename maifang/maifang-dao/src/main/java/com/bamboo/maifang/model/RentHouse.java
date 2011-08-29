package com.bamboo.maifang.model;

import java.io.Serializable;
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
     * 价格
     */
    @Column(nullable = false)
    private Integer price;

    /**
     * 支付方式
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paid_type_id", nullable = false)
    private Data paidType;

    /**
     * 面积
     */
    @Column(nullable = false)
    private Integer area;

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
     * 房屋结构
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_structure_id")
    private Data buildingStructure;

    /**
     * 入住时间
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "in_time_id")
    private Data inTime;

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
    private Date validityTime;
}


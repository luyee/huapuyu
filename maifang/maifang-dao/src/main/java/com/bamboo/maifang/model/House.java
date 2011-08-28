package com.bamboo.maifang.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 房屋
 * 
 * @author Anders
 * 
 */
@Entity
@Table(name = "tb_house")
public class House implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3452356102879578683L;
    /**
     * 编号（主键）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 楼盘名称
     */
    @Column(nullable = false, length = 50)
    private String title;
    /**
     * 省、自治区、直辖市编号（对应区域配置表类型0）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    private Area province;
    /**
     * 城市编号（对应区域配置表类型1）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private Area city;
    /**
     * 区、县、市编号（对应区域配置表类型2）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private Area district;
    /**
     * 详细地址
     */
    @Column(nullable = false, length = 100)
    private String address;
    /**
     * 室
     */
    @Column(nullable = false)
    private Byte bedroomCount;
    /**
     * 厅
     */
    @Column(nullable = false)
    private Byte livingRoomCount;
    /**
     * 厨
     */
    @Column(nullable = false)
    private Byte kitchenCount;
    /**
     * 卫
     */
    @Column(nullable = false)
    private Byte washroomCount;
    /**
     * 阳台
     */
    @Column(nullable = false)
    private Byte balconyCount;
    /**
     * 朝向
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orientation_id")
    private Data orientation;
    /**
     * 户型
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_type_id")
    private Data buildType;
    /**
     * 物业类型
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_type_id")
    private Data propertyType;
    /**
     * 建筑年代
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "construction_year_id")
    private Data constructionYear;
    /**
     * 装修程度
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decoration_id")
    private Data decoration;
    /**
     * 总楼层
     */
    @Column(nullable = false)
    private Byte totalFloor;
    /**
     * 所在楼层
     */
    @Column(nullable = false)
    private Byte floor;
    /**
     * 交通状况
     */
    @Column(length = 500)
    private String transport;
    /**
     * 周边配套
     */
    @Column(length = 500)
    private String environment;
    /**
     * 房源描述
     */
    @Column(length = 500)
    private String remark;
    /**
     * 配套设施
     */
    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "house")
    private Set<Facility> facilities = new HashSet<Facility>(0);
    /**
     * 房源特色
     */
    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "house")
    private Set<Feature> features = new HashSet<Feature>(0);
    /**
     * 二手房
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_hand_house_id")
    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "second_hand_house_id")
    private SecondHandHouse secondHandHouse;

    /**
     * 出租房
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_house_id")
    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "second_hand_house_id")
    private RentHouse rentHouse;
}

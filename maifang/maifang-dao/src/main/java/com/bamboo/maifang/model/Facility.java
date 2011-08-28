package com.bamboo.maifang.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 配套设施
 * 
 * @author Anders
 * 
 */
@Entity
@Table(name = "rlt_facility")
public class Facility implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5410307218485318011L;
    /**
     * 编号（主键）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 房屋编号
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;
    /**
     * 数据配置编号
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_id")
    private Data data;
}

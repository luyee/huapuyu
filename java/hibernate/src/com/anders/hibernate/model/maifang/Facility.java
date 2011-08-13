package com.anders.hibernate.model.maifang;

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
 * ÅäÌ×ÉèÊ©
 * 
 * @author Anders
 * 
 */
@Entity
@Table(name = "rlt_facility")
public class Facility implements Serializable {
	private static final long serialVersionUID = -3838557037798985561L;

	/**
	 * ±àºÅ£¨Ö÷¼ü£©
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * ·¿ÎÝ±àºÅ
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "house_id")
	private House house;
	/**
	 * Êý¾ÝÅäÖÃ±àºÅ
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "data_id")
	private Data data;
}

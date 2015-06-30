package com.anders.vote.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "tb_poll_item")
@Searchable
@XmlRootElement
public class PollItem implements Serializable {

	private static final long serialVersionUID = 628851298167014104L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SearchableId
	private Long id;
	
	@Column(nullable = false, length = 50)
    @SearchableProperty
	private String title;
	
	@Column(nullable = false)
	@XmlTransient
	private Boolean enable = true;

	private Poll poll;
}

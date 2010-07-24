package model.annotation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Status implements Serializable
{
	private static final long serialVersionUID = -8263943031966018118L;

	private Long id;
	private String name;
	private Boolean enable = true;

	public Status(Long id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public Status(Long id)
	{
		this.id = id;
	}

	public Status(String name)
	{
		this.name = name;
	}

	public Status()
	{
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@Column(nullable = false, unique = true)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column
	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}
}
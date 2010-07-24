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
public class Tag implements Serializable
{
	private static final long serialVersionUID = 5775198933010523333L;

	private Long id;
	private String name;
	private String description;
	private Boolean enable = true;

	public Tag()
	{
	}

	public Tag(Long id)
	{
		this.id = id;
	}

	public Tag(Long id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public Tag(String name)
	{
		this.name = name;
	}

	public Tag(Long id, String name, String description)
	{
		this.id = id;
		this.name = name;
		this.description = description;
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

	@Column(length = 1000)
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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
package model.annotation;

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

@Entity
@Table
public class Category implements Serializable
{
	private static final long serialVersionUID = 6297026643789122337L;

	private Long id;
	private Category parentCategory;
	private String name;
	private String description;
	private Boolean enable = true;

	private Set<Category> sonCategorys = new HashSet<Category>(0);

	public Category()
	{
	}

	public Category(Long id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public Category(Long id)
	{
		this.id = id;
	}

	public Category(Long id, String name, String description)
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARENT_ID")
	public Category getParentCategory()
	{
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory)
	{
		this.parentCategory = parentCategory;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parentCategory")
	public Set<Category> getSonCategorys()
	{
		return sonCategorys;
	}

	public void setSonCategorys(Set<Category> sonCategorys)
	{
		this.sonCategorys = sonCategorys;
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
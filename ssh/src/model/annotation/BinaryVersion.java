package model.annotation;

import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class BinaryVersion implements java.io.Serializable
{
	private static final long serialVersionUID = -8263943031966018118L;

	private Long id;
	private String name;
	private Date createDate = new Date();
	private Date updateDate = new Date();
	private String version;
	private String fileName;
	private String filePath;
	private BigDecimal fileSize;
	private String imagePath;
	private String description;
	private Long recommendOrder;
	private Date recommendBeginDate;
	private Date recommendEndDate;

	private Asset asset;
	private Status status;

	private Set<Category> categories = new HashSet<Category>(0);
	private Set<Tag> tags = new HashSet<Tag>(0);
	private Set<ScreenShots> screenShotses = new HashSet<ScreenShots>(0);
	private Set<Price> prices = new HashSet<Price>(0);

	public BinaryVersion()
	{
	}

	public BinaryVersion(Long id)
	{
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSET_ID")
	public Asset getAsset()
	{
		return asset;
	}

	public void setAsset(Asset asset)
	{
		this.asset = asset;
	}

	@Column
	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	@Column
	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	@Column
	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATUS_ID")
	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	@Column
	public BigDecimal getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(BigDecimal fileSize)
	{
		this.fileSize = fileSize;
	}

	@Column
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column
	public String getImagePath()
	{
		return imagePath;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}

	@Column(length = 4000)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@OneToMany(fetch = FetchType.EAGER)
	public Set<Category> getCategories()
	{
		return categories;
	}

	public void setCategories(Set<Category> categories)
	{
		this.categories = categories;
	}

	@OneToMany(fetch = FetchType.EAGER)
	public Set<Tag> getTags()
	{
		return tags;
	}

	public void setTags(Set<Tag> tags)
	{
		this.tags = tags;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "binaryVersion")
	public Set<ScreenShots> getScreenShotses()
	{
		return screenShotses;
	}

	public void setScreenShotses(Set<ScreenShots> screenShotses)
	{
		this.screenShotses = screenShotses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "binaryVersion")
	public Set<Price> getPrices()
	{
		return prices;
	}

	public void setPrices(Set<Price> prices)
	{
		this.prices = prices;
	}

	@Column
	public Long getRecommendOrder()
	{
		return recommendOrder;
	}

	public void setRecommendOrder(Long recommendOrder)
	{
		this.recommendOrder = recommendOrder;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getRecommendBeginDate()
	{
		return recommendBeginDate;
	}

	public void setRecommendBeginDate(Date recommendBeginDate)
	{
		this.recommendBeginDate = recommendBeginDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getRecommendEndDate()
	{
		return recommendEndDate;
	}

	public void setRecommendEndDate(Date recommendEndDate)
	{
		this.recommendEndDate = recommendEndDate;
	}
}
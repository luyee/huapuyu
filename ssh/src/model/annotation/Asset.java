package model.annotation;

import java.io.Serializable;
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
public class Asset implements Serializable
{
	private static final long serialVersionUID = -9215610077108665003L;

	private Long id;
	private String name;
	private String description;
	private Date createDate = new Date();
	private Date updateDate = new Date();
	private Double averageRating = 0D;
	private String currentVersion;
	private Long recommendOrder;
	private Date recommendBeginDate;
	private Date recommendEndDate;
	private String imagePath;

	private Status status;
	private Provider provider;

	private Set<ScreenShots> screenShotses = new HashSet<ScreenShots>(0);
	private Set<Rating> ratings = new HashSet<Rating>(0);
	private Set<Comments> commentses = new HashSet<Comments>(0);
	private Set<TagRelation> tagRelations = new HashSet<TagRelation>(0);
	private Set<Category> categories = new HashSet<Category>(0);
	private Set<Platform> platforms = new HashSet<Platform>(0);
	private Set<Price> prices = new HashSet<Price>(0);
	private Set<BinaryVersion> binaryVersions = new HashSet<BinaryVersion>(0);

	public Asset()
	{
	}

	public Asset(Long id)
	{
		this.id = id;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreateDate()
	{
		return this.createDate;
	}

	public void setCreateDate(Date createdate)
	{
		this.createDate = createdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATUS_ID", nullable = false)
	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
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

	@Column(length = 1000)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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

	@Column
	public String getCurrentVersion()
	{
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion)
	{
		this.currentVersion = currentVersion;
	}

	@Column
	public Double getAverageRating()
	{
		return averageRating;
	}

	public void setAverageRating(Double averageRating)
	{
		this.averageRating = averageRating;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROVIDER_ID")
	public Provider getProvider()
	{
		return provider;
	}

	public void setProvider(Provider provider)
	{
		this.provider = provider;
	}

	@Column(name = "RECOMMEND_ORDER")
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "asset")
	public Set<Rating> getRatings()
	{
		return ratings;
	}

	public void setRatings(Set<Rating> ratings)
	{
		this.ratings = ratings;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "asset")
	public Set<Comments> getCommentses()
	{
		return commentses;
	}

	public void setCommentses(Set<Comments> commentses)
	{
		this.commentses = commentses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "asset")
	public Set<TagRelation> getTagRelations()
	{
		return tagRelations;
	}

	public void setTagRelations(Set<TagRelation> tagRelations)
	{
		this.tagRelations = tagRelations;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "asset")
	public Set<BinaryVersion> getBinaryVersions()
	{
		return binaryVersions;
	}

	public void setBinaryVersions(Set<BinaryVersion> binaryVersions)
	{
		this.binaryVersions = binaryVersions;
	}

	@OneToMany(fetch = FetchType.EAGER)
	public Set<Platform> getPlatforms()
	{
		return platforms;
	}

	public void setPlatforms(Set<Platform> platforms)
	{
		this.platforms = platforms;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "asset")
	public Set<Price> getPrices()
	{
		return prices;
	}

	public void setPrices(Set<Price> prices)
	{
		this.prices = prices;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "asset")
	public Set<ScreenShots> getScreenShotses()
	{
		return screenShotses;
	}

	public void setScreenShotses(Set<ScreenShots> screenShotses)
	{
		this.screenShotses = screenShotses;
	}
}
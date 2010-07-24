package model.annotation;

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

@Entity
@Table
public class ScreenShots implements Serializable
{
	private static final long serialVersionUID = 7621737358523227527L;

	private Long id;
	private String path;
	private String mediaType;
	private String description;
	private Date createDate;

	private Asset asset;
	private BinaryVersion binaryVersion;

	public ScreenShots()
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

	@Column
	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	@Column
	public String getMediaType()
	{
		return mediaType;
	}

	public void setMediaType(String mediaType)
	{
		this.mediaType = mediaType;
	}

	@Column
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSET_ID", nullable = false)
	public Asset getAsset()
	{
		return this.asset;
	}

	public void setAsset(Asset asset)
	{
		this.asset = asset;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BINARYVERSION_ID")
	public BinaryVersion getBinaryVersion()
	{
		return binaryVersion;
	}

	public void setBinaryVersion(BinaryVersion binaryVersion)
	{
		this.binaryVersion = binaryVersion;
	}
}
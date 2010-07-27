package model.annotation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class TagRelation implements Serializable
{
	private static final long serialVersionUID = -8973263778215169453L;

	private Long id;
	private Tag tag;
	private Asset asset;
	private BinaryVersion binaryVersion;

	public TagRelation()
	{
	}

	public TagRelation(Tag tag, Asset asset)
	{
		this.tag = tag;
		this.asset = asset;
	}

	public TagRelation(Long id, Tag tag, Asset asset)
	{
		this.id = id;
		this.tag = tag;
		this.asset = asset;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TAG_ID", nullable = false)
	public Tag getTag()
	{
		return this.tag;
	}

	public void setTag(Tag tag)
	{
		this.tag = tag;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BINARY_VERSION_ID")
	public BinaryVersion getBinaryVersion()
	{
		return binaryVersion;
	}

	public void setBinaryVersion(BinaryVersion binaryVersion)
	{
		this.binaryVersion = binaryVersion;
	}
}
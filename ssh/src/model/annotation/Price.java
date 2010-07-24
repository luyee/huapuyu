package model.annotation;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
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
public class Price implements Serializable
{
	private static final long serialVersionUID = 5659487085319966897L;

	private Long id;
	private BigDecimal amount;
	private Country country;
	private Asset asset;
	private BinaryVersion binaryVersion;

	public Price()
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
	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COUNTRY_ID", nullable = false)
	public Country getCountry()
	{
		return country;
	}

	public void setCountry(Country country)
	{
		this.country = country;
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
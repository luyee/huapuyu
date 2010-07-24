package model.annotation;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class PurchaseHistory implements Serializable
{
	private static final long serialVersionUID = 952227978930863790L;

	private Long id;
	private BigDecimal paidPrice;
	private Asset asset;
	private Date purchaseDate = new Date();
	private Country country;

	public PurchaseHistory()
	{
	}

	public PurchaseHistory(Long id)
	{
		super();
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
	public Date getPurchaseDate()
	{
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate)
	{
		this.purchaseDate = purchaseDate;
	}

	@Column
	public BigDecimal getPaidPrice()
	{
		return paidPrice;
	}

	public void setPaidPrice(BigDecimal paidPrice)
	{
		this.paidPrice = paidPrice;
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
}
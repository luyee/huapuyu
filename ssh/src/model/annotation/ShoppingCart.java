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
public class ShoppingCart implements Serializable
{
	private static final long serialVersionUID = 952227978930863790L;

	private Long id;
	private BigDecimal itemPrice;
	private Asset asset;
	private Date createDate = new Date();
	private Country country;

	public ShoppingCart()
	{
	}

	public ShoppingCart(Long id)
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

	@Column
	public BigDecimal getItemPrice()
	{
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice)
	{
		this.itemPrice = itemPrice;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COUNTRY_ID")
	public Country getCountry()
	{
		return country;
	}

	public void setCountry(Country country)
	{
		this.country = country;
	}
}
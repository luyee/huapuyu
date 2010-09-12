package model.test;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * 该pojo类用于jpa测试
 * 
 * @author Anders
 * 
 */
@Entity
@SequenceGenerator(sequenceName = "seq_depart", name = "sequence")
public class Tb_depart implements Serializable
{
	private static final long serialVersionUID = 8479144368840747416L;

	private Integer id;
	private String name;
	private Integer enabled;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	@Column(length = 20, nullable = true)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column
	public Integer getEnabled()
	{
		return enabled;
	}

	public void setEnabled(Integer enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Tb_depart other = (Tb_depart) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}
}

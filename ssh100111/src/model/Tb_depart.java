package model;

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
@SuppressWarnings("serial")
@Entity
@SequenceGenerator(sequenceName = "seq_depart", name = "sequence")
public class Tb_depart implements Serializable
{
	private int id;
	private String name;
	private int enabled;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	public int getId()
	{
		return id;
	}

	public void setId(int id)
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
	public int getEnabled()
	{
		return enabled;
	}

	public void setEnabled(int enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Tb_depart other = (Tb_depart) obj;
		if (id != other.id)
			return false;
		return true;
	}
}

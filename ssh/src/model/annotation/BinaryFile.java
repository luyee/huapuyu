package model.annotation;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table
public class BinaryFile implements Serializable
{
	private static final long serialVersionUID = 486377427723311396L;

	private Long id;
	private String fileLocation;
	private byte[] fileBinary;

	public BinaryFile()
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

	@Column(nullable = false, unique = true)
	public String getFileLocation()
	{
		return fileLocation;
	}

	public void setFileLocation(String fileLocation)
	{
		this.fileLocation = fileLocation;
	}

	@Column(nullable = false)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public byte[] getFileBinary()
	{
		return fileBinary;
	}

	public void setFileBinary(byte[] fileBinary)
	{
		this.fileBinary = fileBinary;
	}
}
package model;

/**
 * 该pojo类必须包含一个无参数的构造函数，否则hibernate和ibatis会出问题
 * 
 * @author Anders
 * 
 */
public class Tb_user
{
	private int id;
	private String name;
	private String pwd;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	/**
	 * 通过重载toString方法，则System.out.println(tb_user)时，会直接调用tb_user对象的toString方法
	 */
	@Override
	public String toString()
	{
		return "id : " + this.id + "; name : " + this.name + "; pwd : " + this.pwd;
	}
}

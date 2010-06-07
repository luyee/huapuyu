package annotation;

import org.springframework.stereotype.Component;

@Component
public class GuoLili
{
	private String name;
	private String relation;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRelation()
	{
		return relation;
	}

	public void setRelation(String relation)
	{
		this.relation = relation;
	}

	public GuoLili()
	{
		this.name = "郭立立";
		this.relation = "媳妇";
	}

	@Override
	public String toString()
	{
		return "姓名：" + this.name + "；关系:" + this.relation;
	}
}

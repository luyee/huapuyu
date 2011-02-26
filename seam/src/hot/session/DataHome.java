package session;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import entity.Data;

@Name("dataHome")
public class DataHome extends EntityHome<Data>
{
	private static final long serialVersionUID = -4442948747250257446L;

	@RequestParameter
	Long dataId;

	@Override
	public Object getId()
	{
		if (dataId == null)
		{
			return super.getId();
		}
		else
		{
			return dataId;
		}
	}

	@Override
	@Begin
	public void create()
	{
		super.create();
	}
}

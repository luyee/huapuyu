package session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import entity.Data;

@Name("dataList")
public class DataList extends EntityQuery<Data>
{
	private static final long serialVersionUID = 5397948979020089643L;

	public DataList()
	{
		setEjbql("select data from Data data");
	}
}

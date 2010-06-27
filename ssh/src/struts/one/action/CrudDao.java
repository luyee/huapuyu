package struts.one.action;

import java.util.List;

public interface CrudDao
{
	// 初步定义为jdbc得到总数，之后可以定义为Hibernate的和iBATIS的。
	public List<Object> getListFromSql(String sql, Integer startIndex);

	public Pager getPage(int pageSize, int startIndex, String sql);
}

package struts.one.action;

import java.util.ArrayList;
import java.util.List;

public class CrudDaoImpl implements CrudDao
{

	private int pageSize;
	private List<Object> record = new ArrayList<Object>();

	@Override
	public Pager getPage(int pageSize, int startIndex, String sql)
	{
		this.pageSize = pageSize;
		String countSql = "select count(*) from " + sql.substring(sql.indexOf(" from ") + 6);
		System.out.println("已经连接数据库·······");
		int totalCount = Integer.parseInt(this.getListFromSql(countSql, null).get(0).toString());
		Pager pager = new Pager(pageSize, startIndex, totalCount);
		return pager;
	}

	@Override
	public List<Object> getListFromSql(String sql, Integer startIndex)
	{
		System.out.println("通过spring 的 ormaping Hibernate,使用sqlquery得到返回值");
		List<Object> returnList = new ArrayList<Object>();
		if (startIndex != null && record.isEmpty())

		{
			System.out.println("初始化数据······");
			for (int i = 0; i < 100; i++)
			{
				record.add("我是第" + i + "个");
			}
		}

		if (startIndex == null)
		{
			returnList.add(100);
		}
		else
		{
			// 模拟limit 1,10这种东西。
			for (int i = 0; i < pageSize; i++)
			{
				try
				{
					returnList.add(record.get(startIndex + i));
				} catch (Exception e)
				{
				}
			}
		}
		return returnList;
	}
}

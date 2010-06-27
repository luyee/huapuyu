package struts.one.action;

import java.util.List;

public class Pager
{

	private Integer pageSize;
	private Integer startIndex;// 所在页起始页的开始序号
	private Integer totalCount;

	private Integer preStartIndex;// 当前页的前一页的开始序号
	private Integer nextStartIndex;// 当前页的后一页的开始序号
	private Integer endStartIndex;// 最后一页的开始序号
	private Integer jumpStartIndex;// 要跳转页的的开始序号
	private Integer currentPage; // 当前页

	private List<Object> itemList; // 当页应有数据

	public Integer getPreStartIndex()
	{
		return preStartIndex;
	}

	public void setPreStartIndex()
	{
		this.preStartIndex = ((this.getStartIndex() - this.getPageSize()) > 0 ? (this.getStartIndex() - this.getPageSize()) : 0);
	}

	public Integer getNextStartIndex()
	{
		return nextStartIndex;
	}

	public void setNextStartIndex()
	{
		this.nextStartIndex = ((this.getStartIndex() + this.getPageSize()) > this.getEndStartIndex() ? this.getEndStartIndex() : (this.getStartIndex() + this.getPageSize()));
	}

	public Integer getEndStartIndex()
	{
		return endStartIndex;
	}

	public void setEndStartIndex()
	{
		int endIndex = 0;
		if ((this.getTotalCount() - (this.getTotalCount() / this.getPageSize()) * this.getPageSize()) == 0)
		{
			endIndex = (this.getTotalCount() / this.getPageSize() - 1) * this.getPageSize();
		}
		else
		{
			endIndex = (this.getTotalCount() / this.getPageSize()) * this.getPageSize() + 1;
		}
		this.endStartIndex = endIndex;
	}

	public Integer getJumpStartIndex()
	{
		return jumpStartIndex;
	}

	public void setJumpStartIndex(Integer jumpStartIndex)
	{
		this.jumpStartIndex = jumpStartIndex;
	}

	public List<Object> getItemList()
	{
		return itemList;
	}

	public void setItemList(List<Object> itemList)
	{
		this.itemList = itemList;
	}

	public Integer getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
	}

	public Integer getStartIndex()
	{
		return startIndex;
	}

	public void setStartIndex(Integer startIndex)
	{
		this.startIndex = startIndex;
	}

	public Integer getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(Integer totalCount)
	{
		this.totalCount = totalCount;
	}

	// 得到分页实例，每个action中这个实例只需要创建一次。
	public Pager(int pageSize, int startIndex, int totalCount)
	{
		this.setPageSize(pageSize);
		this.setStartIndex(startIndex);
		this.setTotalCount(totalCount);
	}

	public void init()
	{
		this.setEndStartIndex();
		this.setNextStartIndex();
		this.setPreStartIndex();
		this.setCurrentPage();
	}

	public Integer getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage()
	{
		this.currentPage = this.getStartIndex() / 10 + 1;
	}
}

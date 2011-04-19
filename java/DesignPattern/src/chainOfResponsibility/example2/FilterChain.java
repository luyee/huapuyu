package chainOfResponsibility.example2;

import java.util.ArrayList;
import java.util.List;

public class FilterChain
{
	private List<Filter> filters = new ArrayList<Filter>();

	public FilterChain addFilter(Filter filter)
	{
		this.filters.add(filter);
		return this;
	}

	public String doFilter(String msg)
	{
		for (Filter filter : filters)
			msg = filter.doFilter(msg);

		return msg;
	}
}

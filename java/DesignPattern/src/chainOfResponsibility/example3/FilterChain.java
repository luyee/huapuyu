package chainOfResponsibility.example3;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter
{
	private List<Filter> filters = new ArrayList<Filter>();

	public FilterChain addFilter(Filter filter)
	{
		this.filters.add(filter);
		return this;
	}

	@Override
	public String doFilter(String msg)
	{
		for (Filter filter : filters)
			msg = filter.doFilter(msg);

		return msg;
	}
}

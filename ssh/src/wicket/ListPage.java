package wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

import annotation.ZhuZhen;

public class ListPage extends WebPage
{
	@SpringBean
	private ZhuZhen zhuZhen;

	public ListPage()
	{
		add(new Label("countLabel", zhuZhen.getName()));
	}
}

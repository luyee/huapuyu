package com.anders.ssh.wicket;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.StringResourceStream;
import org.wicketstuff.annotation.mount.MountPath;

import com.anders.ssh.annotation.ZhuZhen;


@MountPath(path = "list")
public class ListPage extends WebPage
{
	@SpringBean
	private ZhuZhen zhuZhen;

	public ListPage()
	{
		add(new Label("countLabel", zhuZhen.getName()));
		add(new MyForm("form"));
	}

	public class MyForm extends Form<Void>
	{
		private static final long serialVersionUID = 7526894806464357894L;

		private Integer radioChoice;
		private Integer radioGroup;

		public MyForm(String id)
		{
			super(id);

			List<Integer> list = new ArrayList<Integer>();
			list.add(1);
			list.add(2);
			add(new RadioChoice<Integer>("radioChoice", new PropertyModel<Integer>(this, "radioChoice"), list));

			RadioGroup<Integer> radioGroup = new RadioGroup<Integer>("radioGroup", new PropertyModel<Integer>(this, "radioGroup"));
			radioGroup.add(new Radio<Integer>("radio1", new Model<Integer>(1)));
			radioGroup.add(new Radio<Integer>("radio2", new Model<Integer>(2)));
			add(radioGroup);
		}

		@Override
		protected void onSubmit()
		{
			System.out.println(radioGroup);
			CharSequence export = "zhuzhen,lusong," + radioGroup.toString();
			ResourceStreamRequestTarget target = new ResourceStreamRequestTarget(new StringResourceStream(export, "text/csv"));
			target.setFileName("discounts.csv");
			RequestCycle.get().setRequestTarget(target);
			// getRequestCycle().setRequestTarget(target);

			super.onSubmit();
		}

	}
}

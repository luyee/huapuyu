package composite;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Component
{
	private List<Component> componentList = new ArrayList<Component>();

	@Override
	public Component getComponent()
	{
		return this;
	}

	@Override
	public void sampleOperation(String msg)
	{
		System.out.println(msg + this.getClass().getName());

		for (Component component : componentList)
		{
			component.sampleOperation(msg + "--");
		}
	}

	@Override
	public void add(Component component)
	{
		componentList.add(component);
	}

	@Override
	public void remove(Component component)
	{
		component.remove(component);
	}

}

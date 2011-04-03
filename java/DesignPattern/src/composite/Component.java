package composite;

public interface Component
{
	public Component getComponent();

	public void sampleOperation(String msg);

	public void add(Component component);

	public void remove(Component component);
}

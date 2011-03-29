package 合成模式;

public interface Component
{
	public Component getComponent();

	public void sampleOperation(String msg);

	public void add(Component component);

	public void remove(Component component);
}

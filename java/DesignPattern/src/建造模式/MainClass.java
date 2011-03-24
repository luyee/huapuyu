package 建造模式;

public class MainClass
{
	public static void main(String[] args)
	{
		new Director(new ConcreteBuilder1()).construct("zhuzhen", "guolili");
		new Director(new ConcreteBuilder2()).construct("zhuzhen", "guolili");
	}
}

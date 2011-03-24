package 抽象工厂模式;

public class MainTest
{
	public static void main(String[] args)
	{
		I省份 prov = new 江苏();
		I水果 fruit = prov.factoryFruit();
		I蔬菜 vege = prov.factoryVegetable();
		fruit.grow();
		fruit.harvest();
		fruit.plant();
		vege.grow();
		vege.harvest();
		vege.plant();

		System.out.println("-------------------------------");

		prov = new 山东();
		fruit = prov.factoryFruit();
		vege = prov.factoryVegetable();
		fruit.grow();
		fruit.harvest();
		fruit.plant();
		vege.grow();
		vege.harvest();
		vege.plant();

		// BeanFactory是抽象工厂模式，但不是太典型，需要找一个典型的抽象工厂模式
		// XmlBeanFactory
	}
}

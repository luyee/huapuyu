package 简单工厂模式;

public class 普通工厂类
{
	public static ICar factory(汽车品牌 carBrand) throws CreateCarException
	{
		switch (carBrand)
		{
		case 奔驰:
			return new 奔驰();
		case 奥迪:
			return new 奥迪();
		case 宝马:
			return new 宝马();
		default:
			throw new CreateCarException("创建汽车异常");
		}
	}
}
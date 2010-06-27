package jsp2;

/**
 * 自定义EL表达式，也有个相应的标签配置配置文件
 * 
 * @author Administrator
 * 
 */
public class ElFunction
{

	public static String reverse(String str)
	{

		return new StringBuffer(str).reverse().toString();

	}
}

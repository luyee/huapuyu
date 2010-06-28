package common;

import org.apache.commons.lang.StringUtils;

/**
 * 放置公共使用方法
 * 
 * @author Administrator
 * 
 */
public class Tools
{

	/**
	 * 判断输入字符串是不是空的null，""都算是空
	 * 
	 * @param str
	 * @return 空返回true
	 */
	public static boolean isBlank(String str)
	{

		if (StringUtils.isBlank(str))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}

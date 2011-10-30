package bubble;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class Tester
{
	/**
	 * 按照从小到大排序
	 */
	@Test
	public void test()
	{
		List<Integer> list = new ArrayList<Integer>(10);
		Random random = new Random();
		for (int i = 0; i < 10; i++)
			list.add(random.nextInt(100));

		Integer[] array = list.toArray(new Integer[10]);

		array = new Integer[] { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };

		System.out.println("排序前数据：");
		for (Integer integer : array)
			System.out.print(integer + ", ");
		System.out.println("");

		for (int i = 0; i < array.length - 1; i++)
		{
			for (int j = 0; j < array.length - 1 - i; j++)
			{
				if (array[j] > array[j + 1])
				{
					Integer temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}

			System.out.println("第" + (i + 1) + "排序数据：");
			for (Integer integer : array)
				System.out.print(integer + ", ");
			System.out.println("");
		}

		System.out.println("排序后数据：");
		for (Integer integer : array)
			System.out.print(integer + ", ");
		System.out.println("");
	}
}

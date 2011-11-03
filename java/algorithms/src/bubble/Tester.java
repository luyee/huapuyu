package bubble;

import org.junit.Test;

public class Tester
{
	/**
	 * 按照从小到大排序
	 */
	@Test
	public void test()
	{
		// List<Integer> list = new ArrayList<Integer>(10);
		// Random random = new Random();
		// for (int i = 0; i < 10; i++)
		// list.add(random.nextInt(100));
		// Integer[] array = list.toArray(new Integer[10]);

		Integer[] array = new Integer[] { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
		// Integer[] array = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		System.out.println("****************");
		System.out.println("排序前数据");
		System.out.println("****************");
		for (Integer integer : array)
			System.out.print(integer + ", ");
		System.out.println("");

		for (int i = array.length - 1; i > 0; i--)
		// for (int i = 0; i < array.length - 1; i++)
		{
			boolean breakFlag = true;

			for (int j = 0; j < i; j++)
			// for (int j = 0; j < array.length - 1 - i; j++)
			{
				if (array[j] > array[j + 1])
				{
					Integer temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
					breakFlag = false;
				}
			}

			System.out.println("第" + (array.length - i) + "次排序：");
			// System.out.println("第" + (i + 1) + "次排序：");
			for (Integer integer : array)
				System.out.print(integer + ", ");
			System.out.println("");

			if (breakFlag)
				break;
		}

		System.out.println("****************");
		System.out.println("排序后数据");
		System.out.println("****************");
		for (Integer integer : array)
			System.out.print(integer + ", ");
		System.out.println("");
	}
}

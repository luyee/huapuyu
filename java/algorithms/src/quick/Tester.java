package quick;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tester
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
		int[] array = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
		// int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		System.out.println("****************");
		System.out.println("排序前数据");
		System.out.println("****************");
		for (int integer : array)
		{
			System.out.print(integer + ", ");
		}
		System.out.println("");

		quicksort(array, 0, array.length - 1);

		System.out.println("****************");
		System.out.println("排序后数据");
		System.out.println("****************");
		for (int integer : array)
		{
			System.out.print(integer + ", ");
		}
		System.out.println("");
	}

	private void quicksort(int[] array, int left, int right)
	{
		if (left < right)
		{
			int key = array[left];
			int low = left;
			int high = right;
			while (low < high)
			{
				while (low < high && array[high] > key)
				{
					high--;
				}
				array[low] = array[high];
				while (low < high && array[low] < key)
				{
					low++;
				}
				array[high] = array[low];
			}
			array[low] = key;
			quicksort(array, left, low - 1);
			quicksort(array, low + 1, right);
		}

		for (int integer : array)
		{
			System.out.print(integer + ", ");
		}
		System.out.println("");
	}
}
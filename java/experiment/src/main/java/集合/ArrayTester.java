package 集合;

import java.util.Arrays;

public class ArrayTester {

	public static void main(String[] args) {
		// 数组的拷贝：浅拷贝
		int[] nums1 = new int[] { 12, 13, 14 };
		int[] nums2 = nums1;
		System.out.println(nums1 == nums2);

		// 数组的拷贝：深拷贝（1.6版本之前用此方法）
		int[] nums3 = new int[3];
		System.arraycopy(nums1, 0, nums3, 0, 3);
		System.out.println(nums1 == nums3);

		// 数组的拷贝：深拷贝（1.6版本之后用此方法）
		int[] nums4 = Arrays.copyOf(nums1, nums1.length);
		System.out.println(nums1 == nums4);
	}
}

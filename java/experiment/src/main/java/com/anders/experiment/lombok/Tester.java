package com.anders.experiment.lombok;

public class Tester {
	public static void main(String[] args) {
		Demo demo = new Demo();
		demo.setId(1L);
		demo.setName("zhuzhen");
		System.out.println(demo);

		Demo demo1 = new Demo(2L, "guolili");
		System.out.println(demo1);
	}
}

package com.momolela.assertd;

/**
 * @author sunzj
 */
public class AssertDemo {

	/**
	 * 一定要添加 -ea 的 vm options 不然断言不生效
	 * @param args
	 */
	public static void main(String[] args) {
		test1(-5);
		test2(-3);
	}
	
	private static void test1(int a){
		assert a > 0;
		System.out.println(a);
	}
	private static void test2(int a){
		assert a > 0 : "something goes wrong here, a cannot be less than 0";
		System.out.println(a);
	}
}
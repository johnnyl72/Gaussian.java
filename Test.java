package cs3010;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[] apples = {1,2,3,4,5};
		System.out.println(Arrays.toString(Return1(apples)));
		System.out.println(Arrays.toString(Return2(apples)));
	}
	public static int[] Return1(int[] a1) {
		int[] a = a1;
		a[0] = 122;
		return a;
	}
	public static int[] Return2(int[] a2) {
		int[] a = a2;
		a2[0] = 12;
		return a;
	}
}
